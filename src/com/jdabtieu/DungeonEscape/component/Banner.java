package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * Boss alert banner to be displayed when entering a boss battle room.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Banner extends JPanel {
    /**
     * The text to be animated.
     */
    private final JLabel lblFight;
    
    /**
     * Create the banner.
     */
    public Banner(final String text) {
        super();
        setBackground(Color.RED);
        setBounds(0, 80, Window.WIDTH, 60);
        setLayout(null);
        
        // add the text
        lblFight = new JLabel(text);
        lblFight.setFont(Fonts.LARGE);
        lblFight.setForeground(Color.WHITE);
        lblFight.setBounds(-200, 0, Window.WIDTH, 60);
        add(lblFight);
    }
    
    /**
     * Calls the animation. This renders the banner, makes the text slide across the screen,
     * and then hides the banner.
     */
    public void animate() {
        Main.getContentPane().add(this, Layer.ENEMY, 0);
        Main.safeSleep(300);
        
        // loop text from 200 pixels off screen on the left to off screen on the right
        for (int i = -200; i <= Window.WIDTH; i += 2) {
            lblFight.setBounds(i, 0, Window.WIDTH, 60);
            repaint();
            // target 8s animation
            Main.safeSleep(8000 / (Window.WIDTH + 200));
        }
        
        Main.safeSleep(300);
        setVisible(false);
        Main.getContentPane().remove(this);
    }

}
