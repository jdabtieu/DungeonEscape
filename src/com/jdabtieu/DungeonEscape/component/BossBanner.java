package com.jdabtieu.DungeonEscape.component;

import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Window;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
/**
 * Boss alert banner to be displayed when entering a boss battle room.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class BossBanner extends JPanel {
    /**
     * The text to be animated.
     */
    private JLabel lblFight;
    
    /**
     * Create the banner.
     */
    public BossBanner() {
        setBackground(Color.RED);
        setBounds(0, 80, Window.WIDTH, 60);
        setLayout(null);
        
        lblFight = new JLabel("BOSS FIGHT!");
        lblFight.setFont(new Font("Sitka Text", Font.PLAIN, 32));
        lblFight.setForeground(Color.WHITE);
        lblFight.setBounds(-200, 0, Window.WIDTH, 60);
        add(lblFight);
    }
    
    /**
     * Calls the animation. This renders the banner, makes the text slide across the screen,
     * and then hides the banner.
     */
    public void animate() {
        setVisible(true);
        Main.safeSleep(300);
        
        for (int i = -200; i <= Window.WIDTH; i++) {
            lblFight.setBounds(i, 0, Window.WIDTH, 60);
            repaint();
            Main.safeSleep(4000 / (Window.WIDTH + 200));
        }
        
        Main.safeSleep(300);
        setVisible(false);
    }

}
