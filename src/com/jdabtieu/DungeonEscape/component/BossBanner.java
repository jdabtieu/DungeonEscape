package com.jdabtieu.DungeonEscape.component;

import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.core.Window;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class BossBanner extends JPanel {
    private JLabel lblFight;
    /**
     * Create the panel.
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
    
    public void animate() {
        setVisible(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = -200; i <= Window.WIDTH; i++) {
            lblFight.setBounds(i, 0, Window.WIDTH, 60);
            repaint();
            try {
                Thread.sleep(4000 / (Window.WIDTH + 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
    }

}
