package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.ComboLock;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.component.Weapon;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Music;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Coins;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.GroundWeapon;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Wall;

public class Stage1 extends Stage {
    private boolean bossInit;
    private boolean comboLockEnabled;
    private boolean bossDone;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage1() {
        super();
        Music.initAudio("mellohi.wav", true);
        bossInit = false;
        comboLockEnabled = false;
        Main.getPlayer().setPosition(1200, 500);
        fillStage("stage1");
        texts.add(new Text(">>> Where am I?", 880, 450));
        texts.add(new Text(">>> ...", 880, 470));
        texts.add(new Text(">>> Maybe I should open my eyes", 880, 490));
        texts.add(new Text(">>> This looks like my basement. But something's off.", 880, 510));
        texts.add(new Text(">>> Where am I?", 880, 530));
        texts.add(new Text("Tip: Sensors can trigger different actions", 1350, 550));
        texts.add(new Text("Tip: Watch out for spikes! They deal a ton of damage", 1260, 700));
        texts.add(new Text("Tip: Look around each room for room numbers and clue", 1040, 50));
        texts.add(new Text("1__34_", 870, 320));
        texts.add(new Text("_42__2", 710, 320));
        texts.add(new Text("Ooh, shiny! It's a weapon! Wonder what it is...", 1900, 96));
        texts.add(new Text("Tip: You can hold up to 3 weapons at a time. Any extras will be discarded.", 1900, 270));
        stage[3][126] = new Sensor(() -> initBoss());
        stage[4][126] = new Sensor(() -> initBoss());
        stage[5][126] = new Sensor(() -> initBoss());
        stage[29][74] = new Sensor(() -> changeTile(30, 76, Wall.class));
        stage[31][74] = new Sensor(() -> changeTile(30, 76, Ground.class));
        
        stage[3][14] = new HiddenSensor(() -> {
            if (comboLockEnabled) {
                comboLockEnabled = false;
                if (new ComboLock("142342").run()) {
                    changeTile(2, 14, Ground.class);
                    changeTile(3, 14, Ground.class);
                    redraw();
                }
            }
        });
        
        for (Point e : new Point[] {new Point(3, 12), new Point(4, 12), new Point(5, 12),
                                    new Point(5, 13), new Point(5, 14), new Point(5, 15),
                                    new Point(5, 16), new Point(4, 16), new Point(3, 16)}) {
            stage[e.x][e.y] = new HiddenSensor(() -> comboLockEnabled = true);
        }
        
        stage[3][73] = new HiddenSensor(() -> {
            changeTile(3, 73, Ground.class);
            new BasicPopup("You found a key!", Color.BLACK);
            redraw();
            Main.getPlayer().addKey();
        }, Color.YELLOW);
        
        stage[6][100] = new GroundWeapon(new Weapon("Wooden Axe", 3, 30, "wood_axe.png"));

        finishConstructor();
    }
    
    private void initBoss() {
        if (bossInit || Main.getPlayer().getWeapons().isEmpty()) return;
        bossInit = true;
        bossDone = false;
        changeTile(3, 124, Wall.class);
        changeTile(4, 124, Wall.class);
        changeTile(5, 124, Wall.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.getPlayer().setHealth(100);
        Main.getPlayer().setPosition(2760, 184);
        Main.getPlayer().pauseMovement();
        redraw();
        Banner bb = new Banner("BOSS FIGHT!");
        Main.getContentPane().add(bb, Layer.ENEMY, 0);
        bb.animate();
        Main.getContentPane().remove(bb);
        
        JLabel boss;
        try {
            boss = new JLabel(new ImageIcon(ImageIO.read(new File("assets/boss1.png"))));
        } catch (IOException e) {
            boss = new JLabel();
        }
        boss.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 40, 80, 80);
        Main.getContentPane().add(boss, Layer.ENEMY, 0);
        
        HealthBar healthBar = new HealthBar(30);
        healthBar.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 65, 80, 20);
        Main.getContentPane().add(healthBar, Layer.ENEMY, 0);
        
        Main.getPlayer().weaponSelect();
        
        fight(30, healthBar, () -> (int) (Math.random() + 0.3) * (int) (Math.random() * 5 + 1));
        Main.getPlayer().unpauseMovement();
        new BasicPopup("You defeated the boss!", Color.BLACK);
        healthBar.setVisible(false);
        Main.getContentPane().remove(healthBar);
        boss.setVisible(false);
        Main.getContentPane().remove(boss);
        changeTile(7, 149, Coins.class, 1000);
        changeTile(7, 150, Coins.class, 1000);
        changeTile(8, 149, Coins.class, 1000);
        changeTile(9, 152, GroundWeapon.class, new Weapon("Cubic Scales", 5, 30, "cubic_scales.png"));
        
        for (int i = 164; i < 192; i++) {
            changeTile(4, i, Ground.class);
            changeTile(5, i, Ground.class);
        }
        changeTile(4, 192, Sensor.class, new Runnable() {
            public void run() {
                if (bossDone) return;
                bossDone = true;
                finish();
                synchronized(Main.mon) {
                    Main.mon.notify();
                }
            }
        });
        changeTile(5, 192, Sensor.class, new Runnable() {
            public void run() {
                if (bossDone) return;
                bossDone = true;
                finish();
                synchronized(Main.mon) {
                    Main.mon.notify();
                }
            }
        });
        redraw();
    }
}
