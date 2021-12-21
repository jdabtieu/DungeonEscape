package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.DEBUG_FLAGS;
import com.jdabtieu.DungeonEscape.core.GameOverException;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.map.Coins;
import com.jdabtieu.DungeonEscape.map.DarkGround;
import com.jdabtieu.DungeonEscape.map.Ground;
import com.jdabtieu.DungeonEscape.map.Spike;
import com.jdabtieu.DungeonEscape.map.Text;
import com.jdabtieu.DungeonEscape.map.Tile;
import com.jdabtieu.DungeonEscape.map.Triggerable;
import com.jdabtieu.DungeonEscape.map.Wall;

public class Stage extends JPanel {
    public Object mon;
    protected static Tile[][] stage;
    protected static ArrayList<Text> texts;
    private HashSet<Character> keysPressed;
    private static final int KBD_POLL_RATE = 50;
    private Thread movement;
    public static boolean pauseMovement = false;

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
    
    protected void generateImg(String fname) {
        BufferedImage map = new BufferedImage(Arrays.stream(stage).mapToInt(a -> a.length)
                                                    .reduce(Integer::max).getAsInt() * 20,
                                              stage.length * 20,
                                              BufferedImage.TYPE_INT_ARGB);
        Graphics2D mg = (Graphics2D) map.getGraphics();
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                mg.setColor(stage[i][j].getBackground());
                mg.fillRect(j * 20, i * 20, 20, 20);
            }
        }
        try {
            ImageIO.write(map, "png", new File(fname + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
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
                    stage[i][j].setBounds((j * 20) - Main.player.x + (Window.WIDTH - 20) / 2,
                                          (i * 20) - Main.player.y + (Window.HEIGHT - 20) / 2,
                                          20,
                                          20);
                }
            }
            for (Text lab : texts) {
                lab.setBounds(lab.getXFixed() - Main.player.x + (Window.WIDTH - 20) / 2,
                              lab.getYFixed() - Main.player.y + (Window.HEIGHT - 20) / 2,
                              1000,
                              20);
            }

            setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
            Main.sd.repaint();
        });
    }
    
    public Point testCollision(int Ox, int Oy) {
        int i1 = Main.player.x, i2 = i1;
        int j1 = Main.player.y, j2 = j1;
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
        }
        if (stage[j2 / 20][i2 / 20] instanceof Triggerable) {
            ((Triggerable) stage[j2 / 20][i2 / 20]).trigger();
        }
        int dy = 0, dx = 0;
        if (stage[j1 / 20][i1 / 20] instanceof Wall || stage[j2 / 20][i2 / 20] instanceof Wall) {
            if (Oy < 0) {
                dy = Main.player.y % 20;
                if (dy == 0) dy = -Oy;
            }
            if (Ox < 0) {
                dx = Main.player.x % 20;
                if (dx == 0) dx = -Ox;
            }
            if (Oy > 0) {
                dy = (Main.player.y % 20) - 20;
                if (dy == -20) dy = -Oy;
            }
            if (Ox > 0) {
                dx = (Main.player.x % 20) - 20;
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
        if (pauseMovement) return;
        int wx = 0;
        int wy = 0;
        if (keysPressed.contains('w')) wy--;
        if (keysPressed.contains('a')) wx--;
        if (keysPressed.contains('s')) wy++;
        if (keysPressed.contains('d')) wx++;
        if (wx == 0 && wy == 0) return;
        movePlayer(wx, wy);
        if (DEBUG_FLAGS.PRINT_LOCATION.get()) {
            System.out.println(Main.player.x + " " + Main.player.y);
        }
    }
    
    public void movePlayer(int wx, int wy) {
        wx *= 4;
        wy *= 4;
        Point offsetX = testCollision(wx, 0);
        Main.player.x += wx + offsetX.x;
        Point offsetY = testCollision(0, wy);
        Main.player.y += wy + offsetY.y;
        redraw();
    }
    
    protected void changeTile(int x, int y, Class<?> newTile, Object... args) {
        remove(stage[x][y]);
        try {
            stage[x][y] = (Tile) newTile.getConstructors()[0].newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            // Reset original tile if the new tile cannot be added
        }
        add(stage[x][y]);
    }
}
