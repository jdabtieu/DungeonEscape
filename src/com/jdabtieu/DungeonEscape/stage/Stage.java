package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.component.Weapon;
import com.jdabtieu.DungeonEscape.core.AttackPattern;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Player;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Coins;
import com.jdabtieu.DungeonEscape.tile.DarkGround;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.HealthPot;
import com.jdabtieu.DungeonEscape.tile.Powerup;
import com.jdabtieu.DungeonEscape.tile.Spike;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Tile;
import com.jdabtieu.DungeonEscape.tile.Triggerable;
import com.jdabtieu.DungeonEscape.tile.Wall;

public abstract class Stage extends JPanel {
    /**
     * A 2-D array storing the map
     */
    protected Tile[][] stage;
    
    /**
     * This stores all the text to be displayed on the map
     */
    protected final ArrayList<Text> texts;
    
    /**
     * A Set containing the keys the player is currently pressing
     */
    private final HashSet<Character> keysPressed;
    
    /**
     * A critical hit indicator
     */
    private final JLabel critIndicator;
    
    /**
     * The keyboard poll rate. Movement will be polled this many times per second.
     */
    private static final int KBD_POLL_RATE = 50;
    
    /**
     * The delay for polling the keyboard at the specified poll rate
     * The game updates at this rate, making it the effective frame rate
     */
    private static final int KBD_POLL_DELAY = 1000 / KBD_POLL_RATE;
    
    /**
     * A reference to the thread controlling player movement
     */
    private final Thread movement;

    /**
     * Create the stage and generate the level data from the provided file.
     * @param fname  the filename for the data of this stage, without the path or extension
     */
    public Stage(final String fname) {
        super();
        // draw the stage offscreen and do not automatically repaint, since
        // we repaint manually when it's updated
        setIgnoreRepaint(true);
        setBounds(Window.WIDTH, 0, Window.WIDTH, Window.HEIGHT);
        
        setBackground(Color.BLACK);
        setLayout(null);
        
        texts = new ArrayList<>();
        keysPressed = new HashSet<>();
        
        critIndicator = new JLabel("Critical Hit!");
        critIndicator.setFont(Fonts.TITLE);
        critIndicator.setForeground(Color.RED);
        critIndicator.setVisible(false);
        critIndicator.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        critIndicator.setBounds(Window.WIDTH / 2 - 120, Window.HEIGHT / 2 - 30, 240, 20);
        Main.getContentPane().add(critIndicator, Layer.POPUP, 0);
        
        // create tiles, start listening for keypresses
        fillStage(fname);
        registerKbd();
        movement = new Thread(() -> {
			long time = System.currentTimeMillis();
			long delayTime;
            while (true) {
                // time to the next frame, if it's negative, the game is lagging
                delayTime = KBD_POLL_DELAY + time - System.currentTimeMillis();
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
                    // interrupt happens if we want to stop listening for keypresses
                    return;
                } catch (IllegalArgumentException e) {
                    // uh oh, the game is lagging. that's fine though
                    System.err.printf("INFO: Game lagging. Skipped %d ms\n", -delayTime);
                }
                time = System.currentTimeMillis();
                threadTgt();
            }
        }, "Movement-Thread");
        movement.start();
    }
    
    /**
     * All subclasses must call this method as the last call of their constructor. This
     * makes all the tiles visible and re-renders the map.
     */
    protected void finishConstructor() {
        final int height = stage.length;
        final int width = Arrays.stream(stage).mapToInt(a -> a.length).max().getAsInt();
        // adds all texts
        texts.stream().forEach(e -> add(e));
        
        // adds all tiles
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                stage[i][j].setBounds(j * 20, i * 20, 20, 20);
                add(stage[i][j]);
            }
        }
        
        // moves the map to the right place, relative to the player
        // since the player doesn't move, the map moves opposite the direction of the player
        // mathematically, x-position is -(player's x pos - half the window's width + half a tile's width)
        // same for y-position
        setBounds(-Main.getPlayer().xPos() + Window.WIDTH / 2 - 10, -Main.getPlayer().yPos() + Window.HEIGHT / 2 - 10, width * 20, height * 20);
    }
    
    /**
     * Finish this stage. This call stops the movement listener thread.
     */
    public void finish() {
        movement.interrupt();
    }
    
    /**
     * Fight an enemy. The enemy is defeated if it falls to zero health. The player loses if they
     * fall to zero health.
     * @param enemyHealthBar    the enemy's health bar
     * @param ap                the enemy's attack pattern
     */
    protected void fight(final HealthBar enemyHealthBar, final AttackPattern ap) {
        int enemyHealth = enemyHealthBar.maxHealth();
        // keep attacking until someone is out of health
        while (enemyHealth > 0 && Main.getPlayer().getHealth() > 0) {
            enemyHealth -= Main.getPlayer().getActiveWeapon().attack(critIndicator);
            Main.getPlayer().changeHealth(-ap.attack());
            enemyHealthBar.setValue(enemyHealth);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                // rethrow interrupt
                Thread.currentThread().interrupt();
            }
            critIndicator.setVisible(false);
        }
    }
    
    /**
     * Utility method to be used in constructors. Given a filename, this method attempts to fill
     * the map using the tiles specified in the stage file. Dynamic tiles, such as sensors, cannot
     * be filled in automatically.
     * @param fname the filename of the stage, without the full path or extension
     */
    private void fillStage(final String fname) {
        final String[] rm;
        // read the entire stage file line-by-line into the rm array
        try {
            rm = new String(Files.readAllBytes(Paths.get("assets/stage/" + fname + ".txt")), StandardCharsets.UTF_8).split("\n");
        } catch (IOException e) {
            System.err.println("FATAL: Stage failed to load.");
            System.exit(-1);
            return;
        }
        
        // parse the rm array and turn characters into tiles
        stage = new Tile[rm.length][];
        for (int i = 0; i < rm.length; i++) {
            stage[i] = new Tile[rm[i].length()];
            for (int j = 0; j < rm[i].length(); j++) {
                if (" ".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new Ground();
                } else if ("|=\r".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new Wall();
                } else if ("D".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new DarkGround();
                } else if ("#".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new Spike();
                } else if ("C".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new Coins(100);
                } else if ("H".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new HealthPot();
                } else if ("P".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new Powerup();
                } else {
                    System.err.printf("WARN: Unrecognized token at %d:%d, assuming ground\n", i, j);
                    stage[i][j] = new Ground();
                }
            }
        }
    }
    
    /**
     * Tests if the player touches with any blocks of interest. These include
     * Triggerable tiles, and wall tiles. If no collision occur with walls,
     * move the player to the desired location
     * @param ox    how far along the x-direction the player wants to move
     * @param oy    how far along the y-direction the player wants to move
     */
    private void testCollision(final int ox, final int oy) {
        final Player p = Main.getPlayer();
        // when moving, the player can step on up to two new tiles at once
        // (i1, j1) and (i2, j2)
        int i1 = p.xPos(), i2 = i1;
        int j1 = p.yPos(), j2 = j1;
        if (ox < 0) {
            // to the left
            i1 += ox;
            i2 += ox;
            j2 += 19; // could overlap the tile directly above
        } else if (ox > 0) {
            // to the right
            i1 += 19 + ox;
            i2 += 19 + ox;
            j2 += 19; // could overlap the tile directly above
        }
        if (oy < 0) {
            // down
            j1 += oy;
            j2 += oy;
            i2 += 19; // could overlap the tile directly to the right
        } else if (oy > 0) {
            // up
            j1 += 19 + oy;
            j2 += 19 + oy;
            i2 += 19; // could overlap the tile directly to the right
        }
        // convert player x, y to tile x, y
        i1 /= 20;
        i2 /= 20;
        j1 /= 20;
        j2 /= 20;
        
        // trigger tile
        if (stage[j1][i1] instanceof Triggerable) {
            ((Triggerable) stage[j1][i1]).trigger();
        }
        
        // trigger second tile, but only if it's different from the first one
        if (!(j2 == j1 && i2 == i1) && stage[j2][i2] instanceof Triggerable) {
            ((Triggerable) stage[j2][i2]).trigger();
        }
        
        // prevent player running into the wall by adding adjustment factors
        int dy = 0, dx = 0;
        if (stage[j1][i1] instanceof Wall || stage[j2][i2] instanceof Wall) {
            if (oy < 0) {
                dy = p.yPos() % 20;
                if (dy == 0) dy = -oy;
            }
            if (ox < 0) {
                dx = p.xPos() % 20;
                if (dx == 0) dx = -ox;
            }
            if (oy > 0) {
                dy = (p.yPos() % 20) - 20;
                if (dy == -20) dy = -oy;
            }
            if (ox > 0) {
                dx = (p.xPos() % 20) - 20;
                if (dx == -20) dx = -ox;
            }
        }
        
        // move the player and the map
        p.movePlayer(ox + dx, oy + dy);
        setLocation(getX() - ox - dx, getY() - oy - dy);
    }
    
    /**
     * Sets the player's position to (x, y). This method should be used
     * over Player.setPosition(x, y) in any method that is not a Stage
     * constructor.
     * @param x new x-position of player
     * @param y new y-position of player
     */
    protected void setPlayerPosition(final int x, final int y) {
        final Player p = Main.getPlayer();
        setLocation(getX() + p.xPos() - x, getY() + p.yPos() - y);
        p.setPosition(x, y);
        repaint();
    }
    
    /**
     * Registers the keyboard listener. This allows it to listen for keypresses.
     */
    private void registerKbd() {
        final InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap om = getActionMap();
        for (char c = 'a'; c <= 'z'; c++) {
            final char f = c;
            im.put(KeyStroke.getKeyStroke(Character.toString(c).toUpperCase()), Character.toString(c));
            im.put(KeyStroke.getKeyStroke("released " + Character.toString(c).toUpperCase()), c + "r");
            om.put(Character.toString(c), new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    keysPressed.add(f);
                }
            });
            om.put(Character.toString(c) + "r", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    keysPressed.remove(f);
                }
            });
        }
    }
    
    /**
     * The target for the movement thread. This moves the player according to the
     * keys they are pressing.
     */
    private void threadTgt() {
        if (Main.getPlayer().movementPaused()) return;
        
        // grab the x- and y-velocities
        int wx = 0;
        int wy = 0;
        if (keysPressed.contains('w')) wy--;
        if (keysPressed.contains('a')) wx--;
        if (keysPressed.contains('s')) wy++;
        if (keysPressed.contains('d')) wx++;
        if (wx == 0 && wy == 0) return;
        
        // player moves 4 pixels per input
        wx *= 4;
        wy *= 4;
        if (wx != 0) testCollision(wx, 0);
        if (wy != 0) testCollision(0, wy);
        
        // developer easter egg weapon
        if (keysPressed.contains('j') && keysPressed.contains('w') && keysPressed.contains('p')) {
            Main.getPlayer().addWeapon(new Weapon("Developer Blade", 1000, 1000, "ohb.png"));
        }
    }
    
    /**
     * Changes the tile at the specified location to the new tile
     * @param x         the x-position of the tile
     * @param y         the y-position of the tile
     * @param newTile   the class of the new tile
     * @param args      any arguments required to initialize the new tile
     */
    protected void changeTile(final int x, final int y, final Class<?> newTile, final Object... args) {
        // remove the old tile
        remove(stage[x][y]);
        try {
            // find the constructor corresponding to the arguments and call it,
            // creating a new tile
            for (final Constructor<?> cons : newTile.getConstructors()) {
                if (cons.getParameterCount() == args.length) {
                    stage[x][y] = (Tile) cons.newInstance(args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // reset original tile if the new tile cannot be added
        }
        stage[x][y].setBounds(y * 20, x * 20, 20, 20);
        add(stage[x][y]);
    }
    
    /**
     * Performs a boss fight. The calling method is responsible for player rewards and changing tiles.
     * @param fname     filename for boss image
     * @param health    health of boss
     * @param playerX   x position of player
     * @param playerY   y position of player
     * @param bossX     x position of boss
     * @param bossY     x position of boss
     * @param ap        the attack pattern of the boss
     */
    protected void bossFight(String fname, int health, int playerX, int playerY, int bossX, int bossY, AttackPattern ap) {
        final JLabel boss = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(fname)));
        final HealthBar healthBar = new HealthBar(health);
        final int bossWidth = boss.getIcon().getIconWidth();
        final int bossHeight = boss.getIcon().getIconWidth();
        
        // pause the player and set them at the specified location
        Main.safeSleep(200);
        setPlayerPosition(playerX, playerY);
        Main.getPlayer().pauseMovement();
        
        new Banner("BOSS FIGHT!").animate();
        
        // create the boss & healthbar
        boss.setBounds(bossX, bossY, bossWidth, bossHeight);
        Main.getContentPane().add(boss, Layer.ENEMY, 0);
        healthBar.setBounds(bossX, bossY - 25, bossWidth, 20);
        Main.getContentPane().add(healthBar, Layer.ENEMY, 0);
        
        // make the player select a weapon and fight
        Main.getPlayer().weaponSelect();
        fight(healthBar, ap);
        
        // player beat the boss!
        Main.getPlayer().unpauseMovement();
        new BasicDialog("You defeated the boss!").selection();
        healthBar.setVisible(false);
        Main.getContentPane().remove(healthBar);
        boss.setVisible(false);
        Main.getContentPane().remove(boss);
    }
}
