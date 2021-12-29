package com.jdabtieu.DungeonEscape.stage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.component.QuizShow;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Music;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Wall;

public class Stage3Part2 extends Stage {
    private boolean bossInit;
    private boolean activeGameShow;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage3Part2() {
        super();
        Main.getPlayer().setLocation(360, 2240);
        bossInit = false;
        activeGameShow = false;
        fillStage("stage3_2");
        texts.add(new Text(">>> You completed the maze!", 40, 2240));
        texts.add(new Text(">>> Unfortunately, the hallway collapsed, and you fell into this room", 40, 2260));
        texts.add(new Text("Game show this way --->", 1480, 2090));
        try {
            Text txt = new Text("", 520, 2070);
            txt.setIcon(new ImageIcon(ImageIO.read(new File("assets/fountain.png"))));
            txt.setBounds(520, 2070, 200, 180);
            texts.add(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage[104][102] = new Sensor(() -> initGameShow());
        stage[105][102] = new Sensor(() -> initGameShow());
        stage[104][100] = new HiddenSensor(() -> activeGameShow = true);
        stage[105][100] = new HiddenSensor(() -> activeGameShow = true);
        stage[94][62] = new Sensor(() -> initBoss());
        stage[94][63] = new Sensor(() -> initBoss());
        stage[94][64] = new Sensor(() -> initBoss());
        
        finishConstructor();
    }
    
    private void initGameShow() {
        if (!activeGameShow) return;
        activeGameShow = false;
        if (Main.getPlayer().keys < 1) {
            new BasicDialog("Sorry, but admission requires one key.").selection();
            return;
        } else if (!new BasicConfirm("<html>Do you want to use one key<br>to enter the game show?</html>").selection()) {
            return;
        }
        Main.getPlayer().keys--;
        Main.getSD().repaint();
        Main.getPlayer().pauseMovement();
        if (new QuizShow("Guess the Phrase (80s Song Lyrics)", "never gonna give you up").selection()) {
            new BasicDialog("That's correct! You won 1200 coins.").selection();
            Main.getPlayer().coins += 1200;
            Main.getSD().repaint();
        } else {
            new BasicDialog("Sorry, that's not the answer.").selection();
        }
        
        if (new QuizShow("Finish the word (clues were in stage 2)", "vector").selection()) {
            new BasicDialog("That's correct! You won 1200 coins.").selection();
            Main.getPlayer().coins += 1200;
            Main.getSD().repaint();
        } else {
            new BasicConfirm("Sorry, that's not the answer.").selection();
        }

        new BasicDialog("Thanks for playing!");
        Main.getPlayer().unpauseMovement();
    }
    
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        Main.getPlayer().pauseMovement();
        changeTile(96, 62, Wall.class);
        changeTile(96, 63, Wall.class);
        changeTile(96, 64, Wall.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.getPlayer().setLocation(1260, 1840);
        redraw();
        Banner bb = new Banner("BOSS FIGHT!");
        Main.getContentPane().add(bb, Layer.ENEMY, 0);
        bb.animate();
        Main.getContentPane().remove(bb);
        
        JLabel boss;
        try {
            boss = new JLabel(new ImageIcon(ImageIO.read(new File("assets/boss3.png"))));
        } catch (IOException e) {
            boss = new JLabel();
        }
        boss.setBounds(200, 130, 160, 160);
        Main.getContentPane().add(boss, Layer.ENEMY, 0);
        
        HealthBar healthBar = new HealthBar(850);
        healthBar.setBounds(240, 100, 80, 20);
        Main.getContentPane().add(healthBar, 3, 0);
        
        Main.getPlayer().weaponSelect();
        
        fight(850, healthBar, () -> (int) (Math.random() + 1.7));
        new BasicDialog("You defeated the boss! 5000 coins acquired!").selection();
        Main.getPlayer().coins += 5000;
        Main.getSD().repaint();
        healthBar.setVisible(false);
        Main.getContentPane().remove(healthBar);
        boss.setVisible(false);
        Main.getContentPane().remove(boss);
        endScene();
    }
    
    private void endScene() {
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                stage[i][j].setBackground(Color.WHITE);
            }
        }
        for (int i = 33; i < 65; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                if (stage[i][j] instanceof Ground) {
                    int clr = 255 - (i - 33) * 4;
                    stage[i][j].setBackground(new Color(clr, clr, clr));
                } else {
                    int clr = Math.max(0, 255 - (i - 33) * 8);
                    stage[i][j].setBackground(new Color(clr, clr, clr));
                }
            }
        }
        redraw();
        Music.stopAudio();
        Music.initAudio("win.wav", false);
        while (Main.getPlayer().y > 360) {
            Main.getPlayer().y -= 1;
            redraw();
            Main.safeSleep(10);
        }
        Main.getSD().setVisible(false);
        Main.getPlayer().setInventoryVisible(false);
        final Text[] texts = new Text[6];
        texts[0] = new Text("Wow, this is really bright!", 100, -25);
        texts[1] = new Text("Is this the outside world?", 100, -225);
        texts[2] = new Text("I can't believe I'm finally out!", 100, -425);
        texts[3] = new Text("I wonder how long I've been stuck here...", 100, -625);
        if (Main.getPlayer().isInterviewComplete()){
                texts[4] = new Text("5 years, I think?", 100, -825);
                texts[5] = new Text("Well, time to try out the sequel I made...", 100, -1025);
        } else {
            texts[4] = new Text("3 years, I think?", 100, -825);
            texts[5] = new Text("Well, time to catch up on what I missed...", 100, -1025);
        }
        for (Text t : texts) Main.getContentPane().add(t, Layer.STAGE3_END, 0);
        for (int i = 0; i < 2000; i++) {
            Main.safeSleep(10);
            for (Text t : texts) {
                t.setLocation(100, t.getY() + 1);
                t.repaint();
            }
        }
        
        CreditsBG creditsbg = new CreditsBG();
        creditsbg.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        creditsbg.setBackground(Color.BLACK);
        Main.getContentPane().add(creditsbg, Layer.STAGE3_END, 0);
        for (int i = 0; i < 128; i++) {
            Main.safeSleep(50);
            creditsbg.step(i);
        }
        finish();
        Main.getPlayer().setVisible(false);
        for (Text t : texts) Main.getContentPane().remove(t);
        Main.getContentPane().remove(creditsbg);
        synchronized(Main.mon) {
            Main.mon.notify();
        }
    }
    
    private class CreditsBG extends JPanel {
        private float alpha = 0;
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
            super.paint(g2d);
            g2d.dispose();
        }
        
        public void step(int i) {
            alpha += 0.0005;
            repaint();
        }
    }
}
