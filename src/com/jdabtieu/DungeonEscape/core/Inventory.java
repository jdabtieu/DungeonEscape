package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;

public class Inventory extends JPanel {

    /**
     * Create the panel.
     */
    public Inventory() {
        setBounds(Window.WIDTH / 2 - 60, Window.HEIGHT - 100, 120, 40);
        setLayout(null);
        setOpaque(false);
        repaint();
    }
    
    public void repaint() {
        removeAll();
        super.repaint();
        if (Main.getPlayer() == null) return;
        for (int i = 0; i < 3; i++) {
            JLabel wp;
            if (Main.getPlayer().getWeapons().size() <= i) {
                wp = new JLabel();
            } else {
                wp = Main.getPlayer().getWeapons().get(i);
                JLabel durability = new JLabel(Integer.toString(Main.getPlayer().getWeapons().get(i).getDurability()));
                durability.setFont(new Font("Tahoma", Font.PLAIN, 11));
                durability.setForeground(Color.WHITE);
                durability.setHorizontalAlignment(SwingConstants.RIGHT);
                durability.setBounds(i*40, 26, 40, 14);
                add(durability);
                
                JLabel dmg = new JLabel(Integer.toString(Main.getPlayer().getWeapons().get(i).getDamage()));
                dmg.setFont(new Font("Tahoma", Font.PLAIN, 11));
                dmg.setForeground(Color.WHITE);
                dmg.setHorizontalAlignment(SwingConstants.RIGHT);
                dmg.setBounds(i*40, 0, 40, 14);
                add(dmg);
            }
            wp.setBounds(i*40, 0, 40, 40);
            wp.setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            wp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            add(wp);
        }
    }
}