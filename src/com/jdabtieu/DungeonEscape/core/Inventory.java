package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Inventory extends JPanel {

    /**
     * Create the panel.
     */
    public Inventory() {
        setBounds(Window.WIDTH / 2 - 60, Window.HEIGHT - 100, 120, 40);
        setLayout(null);
        setOpaque(false);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(0, 26, 40, 14);
        add(lblNewLabel);
        repaint();
    }
    
    public void repaint() {
        removeAll();
        super.repaint();
        if (Main.player == null) return;
        for (int i = 0; i < 3; i++) {
            JLabel wp;
            if (Main.player.weapons.size() <= i) {
                wp = new JLabel();
            } else {
                wp = Main.player.weapons.get(i);
                JLabel durability = new JLabel(Integer.toString(Main.player.weapons.get(i).getDurability()));
                durability.setFont(new Font("Tahoma", Font.PLAIN, 11));
                durability.setForeground(Color.WHITE);
                durability.setHorizontalAlignment(SwingConstants.RIGHT);
                durability.setBounds(0, 26, 40, 14);
                add(durability);
                
                JLabel dmg = new JLabel(Integer.toString(Main.player.weapons.get(i).getDamage()));
                dmg.setFont(new Font("Tahoma", Font.PLAIN, 11));
                dmg.setForeground(Color.WHITE);
                dmg.setHorizontalAlignment(SwingConstants.RIGHT);
                dmg.setBounds(0, 0, 40, 14);
                add(dmg);
            }
            wp.setBounds(i*40, 0, 40, 40);
            wp.setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            wp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            add(wp);
        }
    }
}