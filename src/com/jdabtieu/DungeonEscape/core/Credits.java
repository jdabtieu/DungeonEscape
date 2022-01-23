package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.tile.Text;

public class Credits extends JPanel {
    /**
     * Create the screen
     */
    public Credits() {
        super();
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setLayout(null);
        setBackground(Color.BLACK);
    }
    
    /**
     * Start animating the credits
     */
    public void animate() {
        long currTime;
        
        // must update high score before computing credits text
        Main.getPlayer().updateHighScore();
        final String[] creds = {
                "",
                "",
                "",
                "",
                "",
                "Final Score: " + Main.getPlayer().score(),
                "High Score: " + Player.getHighScore(),
                "",
                "",
                "Credits",
                "",
                "Created By:",
                "        Jonathan Wu",
                "",
                "Music and Sound:",
                "        Mellohi - C418",
                "        Qumu - Doodle Champion Island Games: Ending",
                "",
                "Beta Testers:",
                "        Akshaya Varakunan",
                "        Ray Hang",
                "",
                "More projects:",
                "        https://github.com/jdabtieu",
                "",
                "Thanks for playing!",
        };
        final Text[] lines = new Text[creds.length];
        final int totalTime = 38000; // 38s animation time
        final int lineTime = totalTime / (Window.HEIGHT + 50 * creds.length); // time per line
        final JLabel logo = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/titleTextInv.png")));
        logo.setBounds((Window.WIDTH - 834) / 2, Window.HEIGHT, 834, 65);
        add(logo, 10000, 0);
        
        for (int i = 0; i < creds.length; i++) {
            // convert each line of text into GUI component
            lines[i] = new Text(creds[i], 100, Window.HEIGHT + 50 * i);
            lines[i].setForeground(Color.WHITE);
            lines[i].setFont(Fonts.TITLE);
            add(lines[i]);
        }
        
        // these credits text should be in large font
        lines[9].setFont(Fonts.LARGE);
        lines[9].setBounds(100, Window.HEIGHT + 50 * 9, 1000, 32);
        
        // start the audio
        Music.initAudio("win.wav", false);
        currTime = System.currentTimeMillis();
        for (int i = 0; i < totalTime; i += lineTime) {
            // start animation
            try {
                Main.safeSleep((int) (lineTime - System.currentTimeMillis() + currTime));
            } catch (IllegalArgumentException e) {} // lag, that's fine
            currTime = System.currentTimeMillis();
            
            // move each item down the screen
            for (Text t : lines) {
                t.setLocation(100, t.getY() - 1);
            }
            logo.setLocation(logo.getX(), logo.getY() - 1);
        }
        Main.safeSleep(1000);
    }
}
