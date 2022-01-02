package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
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
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.jdabtieu.DungeonEscape.Main;
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
        setBounds(Window.WIDTH, 0, Window.WIDTH, Window.HEIGHT);
        setBackground(Color.black);
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
        
        fillStage(fname);
        registerKbd();
        movement = new Thread(() -> {
			long time = System.currentTimeMillis();
			long delayTime;
            while (true) {
                delayTime = KBD_POLL_DELAY + time - System.currentTimeMillis();
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
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
        redraw();
        texts.stream().forEach(e -> add(e));
        Arrays.stream(stage).forEach(a -> Arrays.stream(a).forEach(e -> add(e)));
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
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
     * @param enemyHealth       the amount of health the enemy has
     * @param enemyHealthBar    the enemy's health bar
     * @param ap                the enemy's attack pattern
     */
    protected void fight(int enemyHealth, final HealthBar enemyHealthBar, final AttackPattern ap) {
        while (enemyHealth > 0 && Main.getPlayer().getHealth() > 0) { // simulate attacks
            enemyHealth -= Main.getPlayer().getActiveWeapon().attack(critIndicator);
            Main.getPlayer().changeHealth(-ap.attack());
            enemyHealthBar.setHealth(enemyHealth);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {}
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
        try {
            rm = new String(Files.readAllBytes(Paths.get("assets/stage/" + fname + ".txt")), StandardCharsets.UTF_8).split("\n");
        } catch (IOException e) {
            System.err.println("FATAL: Stage failed to load.");
            System.exit(-1);
            return;
        }
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
                } else {
                    System.err.printf("WARN: Unrecognized token at %d:%d, assuming ground\n", i, j);
                    stage[i][j] = new Ground();
                }
            }
        }
    }
    
    /**
     * Redraws the stage so that the player is perpetually centered.
     * This is different from repaint(), which will only repaint the panel.
     */
    protected void redraw() {
        SwingUtilities.invokeLater(() -> {
            setBounds(Window.WIDTH, 0, Window.WIDTH, Window.HEIGHT);
            for (int i = 0; i < stage.length; i++) {
                for (int j = 0; j < stage[i].length; j++) {
                    stage[i][j].setBounds((j * 20) - Main.getPlayer().xPos() + (Window.WIDTH - 20) / 2,
                                          (i * 20) - Main.getPlayer().yPos() + (Window.HEIGHT - 20) / 2,
                                          20,
                                          20);
                }
            }
            for (final Text lab : texts) {
                lab.setBounds(lab.getXFixed() - Main.getPlayer().xPos() + (Window.WIDTH - 20) / 2,
                              lab.getYFixed() - Main.getPlayer().yPos() + (Window.HEIGHT - 20) / 2,
                              lab.getWidth(),
                              lab.getHeight());
            }

            setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
            Main.getPlayer().getSD().repaint();
        });
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
        int i1 = p.xPos(), i2 = i1;
        int j1 = p.yPos(), j2 = j1;
        if (ox < 0) {
            i1 += ox;
            i2 += ox;
            j2 += 19;
        } else if (ox > 0) {
            i1 += 19 + ox;
            i2 += 19 + ox;
            j2 += 19;
        }
        if (oy < 0) {
            j1 += oy;
            j2 += oy;
            i2 += 19;
        } else if (oy > 0) {
            j1 += 19 + oy;
            j2 += 19 + oy;
            i2 += 19;
        }
        if (stage[j1 / 20][i1 / 20] instanceof Triggerable) {
            ((Triggerable) stage[j1 / 20][i1 / 20]).trigger();
        } else if (stage[j2 / 20][i2 / 20] instanceof Triggerable) {
            ((Triggerable) stage[j2 / 20][i2 / 20]).trigger();
        }
        int dy = 0, dx = 0;
        if (stage[j1 / 20][i1 / 20] instanceof Wall || stage[j2 / 20][i2 / 20] instanceof Wall) {
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
        p.movePlayer(ox + dx, oy + dy);
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
        int wx = 0;
        int wy = 0;
        if (keysPressed.contains('w')) wy--;
        if (keysPressed.contains('a')) wx--;
        if (keysPressed.contains('s')) wy++;
        if (keysPressed.contains('d')) wx++;
        if (wx == 0 && wy == 0) return;
        wx *= 4;
        wy *= 4;
        testCollision(wx, 0);
        testCollision(0, wy);
        redraw();
        System.out.println(Main.getPlayer().xPos() + " " + Main.getPlayer().yPos());
        
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
        remove(stage[x][y]);
        try {
            for (final Constructor<?> cons : newTile.getConstructors()) {
                if (cons.getParameterCount() == args.length) {
                    stage[x][y] = (Tile) cons.newInstance(args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Reset original tile if the new tile cannot be added
        }
        add(stage[x][y]);
    }
}
