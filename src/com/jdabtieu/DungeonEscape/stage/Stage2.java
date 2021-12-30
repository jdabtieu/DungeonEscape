package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.BasicQuiz;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.component.Weapon;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Coins;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.GroundWeapon;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Wall;

public class Stage2 extends Stage {
    private boolean bossInit;
    private boolean interviewInit;
    private boolean ambushInit;
    private boolean bossDone;
    private boolean activeVending;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage2() {
        super();
        Main.getPlayer().setPosition(360, 1750);
        bossInit = false;
        interviewInit = false;
        ambushInit = false;
        activeVending = true;
        fillStage("stage2");
        texts.add(new Text(">>> That was the longest flight of stairs ever", 40, 1600));
        texts.add(new Text(">>> ...", 40, 1620));
        texts.add(new Text(">>> It's brighter now...does that mean I'm closer to the surface?", 40, 1640));
        texts.add(new Text("Tip: Coins can be used at the vending machine, but the more coins you have at the end of the game, the higher your score.", 30, 1860));
        texts.add(new Text("V", 740, 1580));
        texts.add(new Text("C", 740, 1400));
        texts.add(new Text("T", 740, 900));
        texts.add(new Text("O", 740, 530));
        {
            Text txt = new Text("Interview Room", 500, 1180);
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            txt.setBounds(500, 1180, 148, 40);
            txt.setFont(Fonts.TITLE);
            texts.add(txt);
        }
        try {
            Text txt = new Text("", 470, 1264);
            txt.setIcon(new ImageIcon(ImageIO.read(new File("assets/interviewRoom.png"))));
            txt.setBounds(470, 1264, 220, 80);
            texts.add(txt);
            
            Text interviewer = new Text("", 642, 1288);
            BufferedImage i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) i.getGraphics();
            g.setColor(Color.RED);
            g.fillRect(0, 0, 20, 20);
            interviewer.setIcon(new ImageIcon(i));
            interviewer.setBounds(642, 1288, 20, 20);
            texts.add(interviewer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage[69][36] = new Sensor(() -> initInterview());
        stage[69][35] = new Sensor(() -> initInterview());
        stage[53][16] = new HiddenSensor(() -> initAmbush());
        stage[53][17] = new HiddenSensor(() -> initAmbush());
        stage[53][18] = new HiddenSensor(() -> initAmbush());
        stage[17][10] = new GroundWeapon(null);
        stage[20][10] = new Sensor(() -> vendingMachine());
        stage[21][8] = new HiddenSensor(() -> activeVending = true);
        stage[22][9] = new HiddenSensor(() -> activeVending = true);
        stage[22][10] = new HiddenSensor(() -> activeVending = true);
        stage[22][11] = new HiddenSensor(() -> activeVending = true);
        stage[21][12] = new HiddenSensor(() -> activeVending = true);
        stage[18][42] = new Sensor(() -> initBoss());
        stage[19][42] = new Sensor(() -> initBoss());
        stage[20][42] = new Sensor(() -> initBoss());
        finishConstructor();
    }
    
    private void vendingMachine() {
        if (!activeVending) return;
        activeVending = false;
        Main.getPlayer().pauseMovement();
        Weapon wp = new Weapon("One Hit Blade", 2000, 1, "ohb.png");
        if (new BasicConfirm("<html>For 3300 coins, Vending Machine offers:<br>" + wp + "<br>Would you like to purchase it?</html>").selection()) {
            try {
                Main.getPlayer().useCoins(3300);
                Main.getPlayer().addWeapon(wp);
                changeTile(20, 10, Ground.class);
                changeTile(17, 10, Ground.class);
                redraw();
            } catch (IllegalArgumentException e) {
                new BasicPopup("You don't have enough coins!", Color.RED);
            }
        }
        Main.getPlayer().unpauseMovement();
    }
    
    private void initAmbush() {
        if (ambushInit) return;
        ambushInit = true;
        Main.getPlayer().setPosition(336, 1000);
        Main.getPlayer().pauseMovement();
        changeTile(53, 16, Ground.class);
        changeTile(53, 17, Ground.class);
        changeTile(53, 18, Ground.class);
        redraw();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Banner bb = new Banner("AMBUSH!");
        Main.getContentPane().add(bb, Layer.ENEMY, 0);
        bb.animate();
        Main.getContentPane().remove(bb);
        JLabel enemy;
        try {
            enemy = new JLabel(new ImageIcon(ImageIO.read(new File("assets/ambush.png"))));
        } catch (IOException e) {
            enemy = new JLabel();
        }
        enemy.setBounds(156, 90, 740, 320);
        Main.getContentPane().add(enemy, Layer.ENEMY, 0);
        Point[] offsets = {new Point(115, 4), new Point(38, 60), new Point(40, 190),
                           new Point(172, 161), new Point(260, 63), new Point(304, 184),
                           new Point(375, 16), new Point(520, 72), new Point(483, 177)};
        HealthBar[] healthBars = new HealthBar[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i] = new HealthBar(8);
            healthBars[i].setBounds(enemy.getX() + offsets[i].x, enemy.getY() + offsets[i].y, 80, 20);
            Main.getContentPane().add(healthBars[i], Layer.ENEMY, 0);
        }
        Main.getPlayer().weaponSelect();
        
        for (int i = 0; i < offsets.length; i++) {
            fight(8, healthBars[i], () -> (int) (Math.random() + 0.4) * (int) (Math.random() * 5 + 1));
        }
        Main.getPlayer().unpauseMovement();
        new BasicPopup("You defeated the enemies!", Color.BLACK);
        
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i].setVisible(false);
            Main.getContentPane().remove(healthBars[i]);
        }
        enemy.setVisible(false);
        Main.getContentPane().remove(enemy);
        redraw();
    }
    
    private void initInterview() {
        if (interviewInit) return;
        interviewInit = true;
        if (!new BasicConfirm("<html>The Dungeon Mester would like to<br>invite you to create new levels.<br>Accept the offer?</html>").selection()) {
            interviewInit = false;
            Main.getPlayer().setPosition(712, 1408);
            return;
        }
        Main.getPlayer().pauseMovement();
        Main.getPlayer().setPosition(496, 1288);
        redraw();
        new BasicDialog("<html>So, you want to help add more levels, eh?<br>You're going to have to pass the test first.</html>").selection();
        BasicQuiz[] quiz = {
            new BasicQuiz("For a complex project with over 35 classes, how will you use to manage them?", 2,
                          "a) Online collaborative IDE, such as Replit",
                          "b) Local IDE, such as Eclipse",
                          "c) Local IDE + Git, such as Eclipse"),
            new BasicQuiz("Which of the following would give an experienced programmer an aneurism?", 0,
                          "a) if (!someBool == true)",
                          "b) if (someBool)",
                          "c) if (someBool && someNumber > 3)"),
            new BasicQuiz("Which thread should do the bulk of rendering jobs?", 1,
                          "a) Main thread",
                          "b) AWT event thread",
                          "c) A seperate thread",
                          "d) Doesn't matter"),
            new BasicQuiz("If an object s is a subclass of Tile and implements Interactive, which would be true?", 2,
                          "a) s instanceof Tile",
                          "b) s instanceof Interactive",
                          "c) All of the above",
                          "d) None of the above"),
            new BasicQuiz("What is the best way to wait for a GUI event to happen in the near future?", 2,
                    "a) while true, check condition",
                    "b) while !condition, sleep",
                    "c) wait + notify",
                    "d) pause + resume"),
        };
        int score = 0;
        for (BasicQuiz q : quiz) {
            if (q.selection()) score++;
        }
        if (score < 4) {
            new BasicDialog("<html>You didn't pass the interview.</html>").selection();
        } else {
            new BasicDialog("<html>Congratulations, you got the job!</html>").selection();
            new BasicDialog("<html>After working for two years, you did the unthinkable. "
                    + "Converting from a text-only game to GUI was hard enough, but you "
                    + "somehow created a VR sequel. Amazing!</html>").selection();
            Main.getPlayer().addCoins(1100);
            Main.getPlayer().setInterviewComplete();
            new BasicDialog("<html>You were paid 1100 coins in exchange.</html>").selection();
        }
        Main.getPlayer().unpauseMovement();
    }
    
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        bossDone = false;
        changeTile(18, 40, Wall.class);
        changeTile(19, 40, Wall.class);
        changeTile(20, 40, Wall.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.getPlayer().setPosition(930, 500);
        Main.getPlayer().pauseMovement();
        redraw();
        Banner bb = new Banner("BOSS FIGHT!");
        Main.getContentPane().add(bb, Layer.ENEMY, 0);
        bb.animate();
        Main.getContentPane().remove(bb);
        
        JLabel boss;
        try {
            boss = new JLabel(new ImageIcon(ImageIO.read(new File("assets/boss2.png"))));
        } catch (IOException e) {
            boss = new JLabel();
        }
        boss.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 40, 80, 80);
        Main.getContentPane().add(boss, Layer.ENEMY, 0);
        
        HealthBar healthBar = new HealthBar(100);
        healthBar.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 65, 80, 20);
        Main.getContentPane().add(healthBar, Layer.ENEMY, 0);
        
        Main.getPlayer().weaponSelect();
        
        fight(100, healthBar, () -> (int) (Math.random() + 0.4) * (int) (Math.random() * 8 + 1));
        new BasicPopup("You defeated the boss!", Color.BLACK);
        healthBar.setVisible(false);
        Main.getContentPane().remove(healthBar);
        boss.setVisible(false);
        Main.getContentPane().remove(boss);
        Main.getPlayer().unpauseMovement();
        changeTile(22, 65, Coins.class, 1000);
        changeTile(24, 67, Coins.class, 1000);
        changeTile(22, 63, Coins.class, 1000);
        changeTile(23, 64, Coins.class, 1000);
        changeTile(21, 65, Coins.class, 1000);
        changeTile(24, 63, GroundWeapon.class, new Weapon("Shiny Axe", 20, 40, "shiny_axe.png"));
        
        for (int i = 2; i < 16; i++) {
            changeTile(i, 62, Ground.class);
            changeTile(i, 63, Ground.class);
        }
        changeTile(1, 62, Sensor.class, new Runnable() {
            public void run() {
                if (bossDone) return;
                bossDone = true;
                finish();
                synchronized(Main.mon) {
                    Main.mon.notify();
                }
            }
        });
        changeTile(1, 63, Sensor.class, new Runnable() {
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
