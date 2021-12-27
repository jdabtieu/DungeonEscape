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
        final String[] creds = {
                "Congratulations! You beat the game!",
                "",
                String.format("Final Score: %d", Main.getPlayer().score()),
                "",
                "===================\n"
                + "|                 |\n"
                + "|     CREDITS     |\n"
                + "|                 |\n"
                + "===================",
                "Created By:",
                "\tJonathan Wu",
                "",
                "Music and Sound:",
                "",
                "Beta Testers:",
                "",
                "More projects:",
                "\thttps://github.com/jdabtieu",
                "",
                "Thanks for playing!"
        };
        
        final Text[] lines = new Text[creds.length];
        for (int i = 0; i < creds.length; i++) {
            lines[i] = new Text(creds[i], 100, Window.HEIGHT + 50 * i);
            lines[i].setForeground(Color.white);
            add(lines[i]);
        }
        
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
    }
}