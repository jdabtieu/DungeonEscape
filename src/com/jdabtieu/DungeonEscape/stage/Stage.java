package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Point;
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
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.core.DEBUG_FLAGS;
import com.jdabtieu.DungeonEscape.core.EnemyAttackPattern;
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

public class Stage extends JPanel {
    public Object mon;
    protected static Tile[][] stage;
    protected static ArrayList<Text> texts;
    private HashSet<Character> keysPressed;
    private static final int KBD_POLL_RATE = 50;
    private Thread movement;

    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage() {
        super();
        setBounds(Window.WIDTH, 0, Window.WIDTH, Window.HEIGHT);
        setBackground(Color.black);
        setLayout(null);
        mon = new Object();
        stage = new Tile[0][0];
        texts = new ArrayList<>();
        keysPressed = new HashSet<>();
        registerKbd();
        movement = new Thread(() -> {
			long time = System.currentTimeMillis();
			final int delay = 1000 / KBD_POLL_RATE;
            while (true) {
                long delayTime = delay + time - System.currentTimeMillis();
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
        });
        movement.start();
    }
    
    protected void finishConstructor() {
        redraw();
        texts.stream().forEach(e -> add(e));
        Arrays.stream(stage).forEach(a -> Arrays.stream(a).forEach(e -> add(e)));
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
    }
    
    public void finish() {
        movement.interrupt();
    }
    
    protected void pause() {
        synchronized(mon) {
            try {
                mon.wait();
            } catch (InterruptedException e) {}
        }
    }
    
    protected void fight(int enemyHealth, HealthBar enemyHealthBar, EnemyAttackPattern ap) {
        while (enemyHealth > 0 && Main.getPlayer().getHealth() > 0) { // simulate attacks
            enemyHealth -= Main.getPlayer().getActiveWeapon().attack();
            Main.getPlayer().changeHealth(-ap.attack());
            enemyHealthBar.setHealth(enemyHealth);
            Main.getSD().repaint();
            Main.getPlayer().repaintInventory();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {}
        }
    }
    
    protected void fillStage(String fname) {
        String[] rm;
        try {
            rm = new String(Files.readAllBytes(Paths.get("assets/" + fname + ".txt")), StandardCharsets.UTF_8).split("\n");
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
                    stage[i][j] = new Coins();
                } else if ("H".contains(rm[i].substring(j, j+1))) {
                    stage[i][j] = new HealthPot();
                } else {
                    System.err.printf("WARN: Unrecognized token at %d:%d, assuming ground\n", i, j);
                    stage[i][j] = new Ground();
                }
            }
        }
    }
    
    public void redraw() {
        Main.drawSafe(() -> {
            setBounds(Window.WIDTH, 0, Window.WIDTH, Window.HEIGHT);
            for (int i = 0; i < stage.length; i++) {
                for (int j = 0; j < stage[i].length; j++) {
                    stage[i][j].setBounds((j * 20) - Main.getPlayer().x + (Window.WIDTH - 20) / 2,
                                          (i * 20) - Main.getPlayer().y + (Window.HEIGHT - 20) / 2,
                                          20,
                                          20);
                }
            }
            for (Text lab : texts) {
                lab.setBounds(lab.getXFixed() - Main.getPlayer().x + (Window.WIDTH - 20) / 2,
                              lab.getYFixed() - Main.getPlayer().y + (Window.HEIGHT - 20) / 2,
                              lab.getWidth(),
                              lab.getHeight());
            }

            setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
            Main.getSD().repaint();
        });
    }
    
    public Point testCollision(int Ox, int Oy) {
        int i1 = Main.getPlayer().x, i2 = i1;
        int j1 = Main.getPlayer().y, j2 = j1;
        if (Ox < 0) {
            i1 += Ox;
            i2 += Ox;
            j2 += 19;
        } else if (Ox > 0) {
            i1 += 19 + Ox;
            i2 += 19 + Ox;
            j2 += 19;
        }
        if (Oy < 0) {
            j1 += Oy;
            j2 += Oy;
            i2 += 19;
        } else if (Oy > 0) {
            j1 += 19 + Oy;
            j2 += 19 + Oy;
            i2 += 19;
        }
        if (stage[j1 / 20][i1 / 20] instanceof Triggerable) {
            ((Triggerable) stage[j1 / 20][i1 / 20]).trigger();
        } else if (stage[j2 / 20][i2 / 20] instanceof Triggerable) {
            ((Triggerable) stage[j2 / 20][i2 / 20]).trigger();
        }
        int dy = 0, dx = 0;
        if (stage[j1 / 20][i1 / 20] instanceof Wall || stage[j2 / 20][i2 / 20] instanceof Wall) {
            if (Oy < 0) {
                dy = Main.getPlayer().y % 20;
                if (dy == 0) dy = -Oy;
            }
            if (Ox < 0) {
                dx = Main.getPlayer().x % 20;
                if (dx == 0) dx = -Ox;
            }
            if (Oy > 0) {
                dy = (Main.getPlayer().y % 20) - 20;
                if (dy == -20) dy = -Oy;
            }
            if (Ox > 0) {
                dx = (Main.getPlayer().x % 20) - 20;
                if (dx == -20) dx = -Ox;
            }
        }
        return new Point(dx, dy);
    }
    
    private void registerKbd() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap om = getActionMap();
        im.put(KeyStroke.getKeyStroke("W"), "w");
        im.put(KeyStroke.getKeyStroke("A"), "a");
        im.put(KeyStroke.getKeyStroke("S"), "s");
        im.put(KeyStroke.getKeyStroke("D"), "d");
        im.put(KeyStroke.getKeyStroke("released W"), "wr");
        im.put(KeyStroke.getKeyStroke("released A"), "ar");
        im.put(KeyStroke.getKeyStroke("released S"), "sr");
        im.put(KeyStroke.getKeyStroke("released D"), "dr");
        om.put("w", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.add('w');
            }
        });
        om.put("a", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.add('a');
            }
        });
        om.put("s", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.add('s');
            }
        });
        om.put("d", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.add('d');
            }
        });
        om.put("wr", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.remove('w');
            }
        });
        om.put("ar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.remove('a');
            }
        });
        om.put("sr", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.remove('s');
            }
        });
        om.put("dr", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                keysPressed.remove('d');
            }
        });
    }
    
    private void threadTgt() {
        if (Main.getPlayer().movementPaused()) return;
        int wx = 0;
        int wy = 0;
        if (keysPressed.contains('w')) wy--;
        if (keysPressed.contains('a')) wx--;
        if (keysPressed.contains('s')) wy++;
        if (keysPressed.contains('d')) wx++;
        if (wx == 0 && wy == 0) return;
        movePlayer(wx, wy);
    }
    
    public void movePlayer(int wx, int wy) {
        wx *= 4;
        wy *= 4;
        Point offsetX = testCollision(wx, 0);
        Main.getPlayer().x += wx + offsetX.x;
        Point offsetY = testCollision(0, wy);
        Main.getPlayer().y += wy + offsetY.y;
        redraw();
    }
    
    protected void changeTile(int x, int y, Class<?> newTile, Object... args) {
        remove(stage[x][y]);
        try {
            for (Constructor<?> cons : newTile.getConstructors()) {
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
