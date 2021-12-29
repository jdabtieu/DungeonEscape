package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.tile.Text;

public class Credits extends JPanel {

    /**
     * Create the frame.
     * @throws IOException 
     */
    public Credits() {
        super();
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setLayout(null);
        setBackground(Color.BLACK);
    }
    
    public void animate() {
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
                "",
                "More projects:",
                "        https://github.com/jdabtieu",
                "",
                "Thanks for playing!"
        };
        
        final Text[] lines = new Text[creds.length];
        for (int i = 0; i < creds.length; i++) {
            lines[i] = new Text(creds[i], 100, Window.HEIGHT + 50 * i);
            lines[i].setForeground(Color.white);
            lines[i].setFont(Fonts.TITLE);
            add(lines[i]);
        }
        lines[0].setFont(Fonts.LARGE);
        lines[0].setBounds(100, Window.HEIGHT, 1000, 32);
        lines[7].setFont(Fonts.LARGE);
        lines[7].setBounds(100, Window.HEIGHT + 300, 1000, 32);
        Music.initAudio("win.wav", false);
        final int totalTime = 38000;
        final int lineTime = totalTime / (Window.HEIGHT + 50 * creds.length);
        long currTime = System.currentTimeMillis();
        for (int i = 0; i < totalTime; i += lineTime) {
            try {
                Main.safeSleep((int) (lineTime - System.currentTimeMillis() + currTime));
            } catch (IllegalArgumentException e) {} // lag, that's fine
            currTime = System.currentTimeMillis();
            for (Text t : lines) {
                t.setLocation(100, t.getY() - 1);
            }
        }
        Main.safeSleep(1000);
    }
}
