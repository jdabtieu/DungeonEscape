package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.QuizShow;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Music;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
import com.jdabtieu.DungeonEscape.tile.Wall;
import com.jdabtieu.DungeonEscape.vfx.ScreenFlicker;
/**
 * Code for stage 3 part 2
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Stage3Part2 extends Stage {
    /**
     * Whether the boss fight has been initiated
     */
    private boolean bossInit;
    
    /**
     * Whether the game show is active
     */
    private boolean activeGameShow;
    
    /**
     * Create the stage 
     */
    public Stage3Part2() {
        super("stage3_2");
        Main.getPlayer().setPosition(360, 2240);
        bossInit = false;
        activeGameShow = false;
        texts.add(new Text(">>> You completed the maze!", 40, 2240));
        texts.add(new Text(">>> Unfortunately, the hallway collapsed, and you fell into this room", 40, 2260));
        texts.add(new Text("Game show this way → →", 1480, 2090));
        {
            final Text txt = new Text("", 520, 2070);
            txt.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/fountain.png")));
            txt.setBounds(520, 2070, 200, 180);
            texts.add(txt);
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
    
    /**
     * Code for the game show
     */
    private void initGameShow() {
        if (!activeGameShow) return;
        activeGameShow = false;
        if (Main.getPlayer().getKeys() < 1) {
            new BasicDialog("Sorry, but admission requires one key.").selection();
            return;
        } else if (!new BasicConfirm("<html>Do you want to use one key<br>to enter the game show?</html>").selection()) {
            return;
        }
        Main.getPlayer().useKey();
        Main.getPlayer().pauseMovement();
        if (new QuizShow("Guess the Phrase (80s Song Lyrics)", "never gonna give you up").selection()) {
            new BasicDialog("That's correct! You won 1200 coins.").selection();
            Main.getPlayer().addCoins(1200);
        } else {
            new BasicDialog("Sorry, that's not the answer.").selection();
        }
        
        if (new QuizShow("Finish the word (clues were in stage 2)", "vector").selection()) {
            new BasicDialog("That's correct! You won 1200 coins.").selection();
            Main.getPlayer().addCoins(1200);
        } else {
            new BasicDialog("Sorry, that's not the answer.").selection();
        }

        new BasicDialog("Thanks for playing!");
        Main.getPlayer().unpauseMovement();
    }
    
    /**
     * Code for the boss fight
     */
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        changeTile(96, 62, Wall.class);
        changeTile(96, 63, Wall.class);
        changeTile(96, 64, Wall.class);
        
        bossFight("assets/boss3.png", 825, 1260, 1840, 200, 130, () -> (int) (Math.random() + 1.7));
        
        new BasicDialog("5000 coins acquired!").selection();
        Main.getPlayer().addCoins(5000);
        endScene();
    }
    
    /**
     * The ending scene after beating the boss
     */
    private void endScene() {
        final Text[] texts = new Text[6];
        final JPanel creditsbg = new JPanel() {
            private int alpha = 0;
            
            @Override
            public void setBackground(Color c) {
                alpha = c.getAlpha();
            }
            
            @Override
            public void paintComponent(Graphics g) {
                // Change transparency of this component
                // https://stackoverflow.com/questions/4982960/java-swing-translucent-components
                g.setColor(new Color(0, 0, 0, alpha));
                Insets insets = getInsets();
                g.fillRect(insets.left, insets.top, 
                        getWidth() - insets.left - insets.right, 
                        getHeight() - insets.top - insets.bottom);
                super.paintComponent(g);
            }
        };
        
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
        ScreenFlicker.stopAnimation();
        redraw();
        Music.stopAudio();
        Music.initAudio("win.wav", false);
        while (Main.getPlayer().yPos() > 360) {
            if (Main.getPlayer().yPos() % 5 == 0) ScreenFlicker.decreaseAlpha();
            Main.getPlayer().movePlayer(0, -1);
            redraw();
            Main.safeSleep(10);
        }
        Main.getPlayer().setSDVisible(false);
        Main.getPlayer().setInventoryVisible(false);
        texts[0] = new Text("Wow, this is really bright!", 100, -25);
        texts[1] = new Text("Is this the outside world?", 100, -225);
        texts[2] = new Text("I can't believe I'm finally out!", 100, -425);
        texts[3] = new Text("I wonder how long I've been stuck here...", 100, -625);
        if (Main.getPlayer().interviewComplete()){
                texts[4] = new Text("5 years, I think?", 100, -825);
                texts[5] = new Text("Well, time to try out the sequel I made...", 100, -1025);
        } else {
            texts[4] = new Text("3 years, I think?", 100, -825);
            texts[5] = new Text("Well, time to catch up on what I missed...", 100, -1025);
        }
        for (final Text t : texts) Main.getContentPane().add(t, Layer.STAGE3_END, 0);
        for (int i = 0; i < 1850; i++) {
            Main.safeSleep(10);
            for (final Text t : texts) {
                t.setLocation(100, t.getY() + 1);
                t.repaint();
            }
        }
        
        creditsbg.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        creditsbg.setOpaque(false);
        Main.getContentPane().add(creditsbg, Layer.VFX_0, 0);
        for (int i = 0; i < 256; i++) {
            Main.safeSleep(16);
            creditsbg.setBackground(new Color(0, 0, 0, i));
            creditsbg.repaint();
        }
        finish();
        Main.getPlayer().setVisible(false);
        for (final Text t : texts) Main.getContentPane().remove(t);
        Main.getContentPane().remove(creditsbg);
        synchronized(Main.mon) {
            Main.mon.notify();
        }
    }
}
