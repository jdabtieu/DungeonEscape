package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
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
/**
 * Code for stage 2
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Stage2 extends Stage {
    /**
     * Whether the boss fight has been initiated
     */
    private boolean bossInit;
    
    /**
     * WHether the interview has been initiated
     */
    private boolean interviewInit;
    
    /**
     * Whether the ambush has been initiated
     */
    private boolean ambushInit;
    
    /**
     * Whether the boss fight is done
     */
    private boolean bossDone;
    
    /**
     * Whether the vending machine is active
     */
    private boolean activeVending;
    
    /**
     * Create the stage
     */
    public Stage2() {
        super("stage2");
        Main.getPlayer().setPosition(360, 1750);
        bossInit = false;
        interviewInit = false;
        ambushInit = false;
        activeVending = true;
        
        // add texts
        texts.add(new Text(">>> That was the longest flight of stairs ever", 40, 1600));
        texts.add(new Text(">>> ...", 40, 1620));
        texts.add(new Text(">>> I did go up though...does that mean I'm closer to the surface?", 40, 1640));
        texts.add(new Text("Tip: Coins can be used at the vending machine, but the more coins you have at the end of the game, the higher your score.", 30, 1860));
        texts.add(new Text("Tip: Weapons cannot be switched out mid-fight, so make sure the one you", 264, 324));
        texts.add(new Text("choose can kill the boss!", 284, 344));
        texts.add(new Text("V", 740, 1580));
        texts.add(new Text("E", 24, 1164));
        texts.add(new Text("C", 740, 1400));
        texts.add(new Text("T", 740, 900));
        texts.add(new Text("O", 740, 530));
        texts.add(new Text("R", 1580, 620));
        {
            // create interview room: title, table, interviewer
            final Text txt = new Text("Interview Room", 500, 1180);
            final Text interviewRoom = new Text("", 470, 1264);
            final Text interviewer = new Text("", 642, 1288);
            final BufferedImage i;
            final Graphics2D g;
            
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            txt.setBounds(500, 1180, 148, 40);
            txt.setFont(Fonts.TITLE);
            texts.add(txt);
            
            interviewRoom.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/interviewRoom.png")));
            interviewRoom.setBounds(470, 1264, 220, 80);
            texts.add(interviewRoom);
            
            // draw the interviewer as a 20x20 red square
            i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) i.getGraphics();
            g.setColor(Color.RED);
            g.fillRect(0, 0, 20, 20);
            g.dispose();
            interviewer.setIcon(new ImageIcon(i));
            interviewer.setBounds(642, 1288, 20, 20);
            texts.add(interviewer);
        }
        
        // add dynamic tiles
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
    
    /**
     * Code for the vending machine
     */
    private void vendingMachine() {
        if (!activeVending) return; // can only use vending machine once
        activeVending = false;
        Main.getPlayer().pauseMovement();
        if (new BasicConfirm("<html>For 3000 coins, Vending Machine offers:<br>" + 
                             new Weapon("One Hit Blade", 2000, 1, "ohb.png") +
                             "<br>Would you like to purchase it?</html>").selection()) {
            try {
                Main.getPlayer().useCoins(3000);
                Main.getPlayer().addWeapon(new Weapon("One Hit Blade", 2000, 1, "ohb.png"));
                
                // delete the sensor and groundweapon tiles that are part of the vending machine
                changeTile(20, 10, Ground.class);
                changeTile(17, 10, Ground.class);
                repaint();
            } catch (IllegalArgumentException e) {
                new BasicDialog("You don't have enough coins!", Color.RED).selection();
            }
        }
        Main.getPlayer().unpauseMovement();
    }
    
    private void initAmbush() {
        if (ambushInit) return; // ambush only happens once
        final JLabel enemy = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/ambush.png")));
        final Point[] offsets = {new Point(115, 4), new Point(38, 60), new Point(40, 190),
                new Point(172, 161), new Point(260, 63), new Point(304, 184),
                new Point(375, 16), new Point(520, 72), new Point(483, 177)};
        final HealthBar[] healthBars = new HealthBar[offsets.length];

        ambushInit = true;
        setPlayerPosition(336, 1000);
        Main.getPlayer().pauseMovement();
        
        // remove ambush sensors
        changeTile(53, 16, Ground.class);
        changeTile(53, 17, Ground.class);
        changeTile(53, 18, Ground.class);
        repaint();
        
        Main.safeSleep(200);
        new Banner("AMBUSH!").animate();
        
        // draw enemies and health bars
        enemy.setBounds(156, 90, 740, 320);
        Main.getContentPane().add(enemy, Layer.ENEMY, 0);
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i] = new HealthBar(8);
            healthBars[i].setBounds(enemy.getX() + offsets[i].x, enemy.getY() + offsets[i].y, 80, 20);
            Main.getContentPane().add(healthBars[i], Layer.ENEMY, 0);
        }
        
        // fight
        Main.getPlayer().weaponSelect();
        for (final HealthBar bar : healthBars) {
            fight(bar, () -> (int) (Math.random() + 0.4) * (int) (Math.random() * 5 + 1));
        }
        Main.getPlayer().unpauseMovement();
        new BasicDialog("You defeated the enemies!", Color.BLACK).selection();
        
        // clean up
        for (final HealthBar bar : healthBars) {
            bar.setVisible(false);
            Main.getContentPane().remove(bar);
        }
        enemy.setVisible(false);
        Main.getContentPane().remove(enemy);
    }
    
    /**
     * Code for the interview
     */
    private void initInterview() {
        if (interviewInit) return; // only one chance at interview
        final BasicQuiz[] quiz = {
            new BasicQuiz("For a large and complex collaborative project, how should it be shared?", 2,
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
        if (!new BasicConfirm("<html>The Dungeon Mester would like to<br>invite you to create new levels.<br>Accept the offer?</html>").selection()) {
            setPlayerPosition(712, 1408);
            return;
        }
        interviewInit = true;
        Main.getPlayer().pauseMovement();
        setPlayerPosition(496, 1288);
        
        // start the interview
        new BasicDialog("<html>So, you want to help add more levels, eh?<br>You're going to have to pass the test first.</html>").selection();
        
        // run through each question, and make sure at least 4 are correct
        if (Arrays.stream(quiz).mapToInt(q -> q.selection() ? 1 : 0).sum() < 4) {
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
    
    /**
     * Code for the boss fight
     */
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        bossDone = false;
        
        // block off entrance
        changeTile(18, 40, Wall.class);
        changeTile(19, 40, Wall.class);
        changeTile(20, 40, Wall.class);
        
        bossFight("assets/boss2.png", 100, 930, 500, Window.WIDTH * 7 / 10, Window.HEIGHT / 2 - 40,
                  () -> (int) (Math.random() + 0.4) * (int) (Math.random() * 8 + 1));

        // create tunnel to next stage
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
        repaint();
    }
}
