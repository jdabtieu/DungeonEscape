package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.BasicQuiz;
import com.jdabtieu.DungeonEscape.component.BossBanner;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.core.Weapon;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Coins;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.GroundWeapon;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Wall;

public class Stage2 extends Stage {
    private boolean bossInit;
    private boolean interviewInit;
    private boolean ambushInit;
    private boolean bossDone;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage2() {
        super();
        Main.getPlayer().setLocation(360, 1750);
        bossInit = false;
        interviewInit = false;
        ambushInit = false;
        fillStage("stage2");
        texts.add(new Text(">>> That was the longest flight of stairs ever", 40, 1600));
        texts.add(new Text(">>> ...", 40, 1620));
        texts.add(new Text(">>> It's brighter now...does that mean I'm closer to the surface?", 40, 1640));
        texts.add(new Text("Tip: Coins can be used at the vending machine, but the more coins you have at the end of the game, the higher your score.", 30, 1860));
        {
            Text txt = new Text("Interview Room", 500, 1180);
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            txt.setBounds(500, 1180, 148, 40);
            txt.setFont(new Font("Sitka Text", Font.BOLD, 16));
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
        stage[18][42] = new Sensor(() -> initBoss());
        stage[19][42] = new Sensor(() -> initBoss());
        stage[20][42] = new Sensor(() -> initBoss());
        
        generateImg("stage2");
        finishConstructor();
    }
    
    private void initInterview() {
        Thread t = new Thread(() -> interview());
        t.start();
    }
    
    private void interview() {
        if (interviewInit) return;
        interviewInit = true;
        boolean res = new BasicConfirm("<html>The Dungeon Mester would like to<br>invite you to create new levels.<br>Accept the offer?</html>").selection();
        if (!res) {
            interviewInit = false;
            Main.getPlayer().setLocation(712, 1408);
            return;
        }
        Main.getPlayer().pauseMovement();
        Main.getPlayer().setLocation(496, 1288);
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
            Main.getPlayer().coins += 1100;
            Main.getPlayer().setInterviewComplete();
            Main.getSD().repaint();
            new BasicDialog("<html>You were paid 1100 coins in exchange.</html>").selection();
        }
        Main.getPlayer().unpauseMovement();
    }
    
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        bossDone = false;
        // TODO
        changeTile(3, 124, Wall.class);
        changeTile(4, 124, Wall.class);
        changeTile(5, 124, Wall.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.getPlayer().setHealth(100);
        Main.getPlayer().x = 2760;
        Main.getPlayer().y = 184;
        Main.getPlayer().pauseMovement();
        redraw();
        BossBanner bb = new BossBanner();
        add(bb);
        bb.animate();
        remove(bb);
        
        JLabel boss;
        try {
            boss = new JLabel(new ImageIcon(ImageIO.read(new File("assets/boss1.png"))));
        } catch (IOException e) {
            boss = new JLabel();
        }
        boss.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 40, 80, 80);
        Main.getContentPane().add(boss, 3, 0);
        
        HealthBar healthBar = new HealthBar(30);
        healthBar.setBounds(Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 65, 80, 20);
        Main.getContentPane().add(healthBar, 3, 0);
        
        Main.getPlayer().weaponSelect(mon);
        pause();
        
        fight(30, healthBar, () -> (int) (Math.random() + 0.3) * (int) (Math.random() * 5 + 1));
        new BasicPopup("You defeated the boss!", Color.BLACK);
        healthBar.setVisible(false);
        Main.getContentPane().remove(healthBar);
        boss.setVisible(false);
        Main.getContentPane().remove(boss);
        Main.getPlayer().unpauseMovement();
        changeTile(7, 149, Coins.class, 1000);
        changeTile(7, 150, Coins.class, 1000);
        changeTile(8, 149, Coins.class, 1000);
        changeTile(9, 152, GroundWeapon.class, new Runnable() {
            public void run() {
                Main.getPlayer().addWeapon(new Weapon("Cubic Scales", 5, 30, "cubic_scales.png"));
                changeTile(9, 152, Ground.class);
                redraw();
            }
        });
        
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
