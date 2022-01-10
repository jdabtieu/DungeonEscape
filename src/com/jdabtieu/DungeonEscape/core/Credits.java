package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;

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
                "Congratulations! You escaped the Dungeon!",
                "",
                "Final Score: " + Main.getPlayer().score(),
                "High Score: " + Player.getHighScore(),
                "",
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
                "",
                "More projects:",
                "        https://github.com/jdabtieu",
                "",
                "Thanks for playing!"
        };
        final Text[] lines = new Text[creds.length];
        final int totalTime = 38000; // 38s animation time
        final int lineTime = totalTime / (Window.HEIGHT + 50 * creds.length); // time per line
        
        for (int i = 0; i < creds.length; i++) {
            // convert each line of text into GUI component
            lines[i] = new Text(creds[i], 100, Window.HEIGHT + 50 * i);
            lines[i].setForeground(Color.white);
            lines[i].setFont(Fonts.TITLE);
            add(lines[i]);
        }
        
        // these two lines should be in large font
        for (int e : new int[] {0, 7}) {
            lines[e].setFont(Fonts.LARGE);
            lines[e].setBounds(100, Window.HEIGHT + 50 * e, 1000, 32);
        }
        
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
        }
        Main.safeSleep(1000);
    }
}
