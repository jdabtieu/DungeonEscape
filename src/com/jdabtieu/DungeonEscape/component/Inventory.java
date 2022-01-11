package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * Creates the inventory displaying all weapons and their stats.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Inventory extends JPanel {
    /**
     * Create the container.
     */
    public Inventory() {
        super();
        setBounds(Window.WIDTH / 2 - 60, Window.HEIGHT - 100, 120, 40);
        setLayout(null);
        setOpaque(false);
        setVisible(false);
        repaint();
    }
    
    @Override
    public void repaint() {
        JLabel wp;
        JLabel durability;
        JLabel dmg;
        
        // clear the inventory
        removeAll();
        super.repaint();
        if (Main.getPlayer() == null) return;
        
        // loop through the player's weapons
        for (int i = 0; i < 3; i++) {
            if (Main.getPlayer().getWeapons().size() <= i) {
                // no weapon = empty slot
                wp = new JLabel();
            } else {
                // get the weapon, durability, and damage
                wp = Main.getPlayer().getWeapons().get(i);
                
                durability = new JLabel(Integer.toString(Main.getPlayer().getWeapons().get(i).getDurability()));
                durability.setFont(Fonts.INVENTORY);
                durability.setForeground(Color.WHITE);
                durability.setHorizontalAlignment(SwingConstants.RIGHT);
                durability.setBounds(i*40, 26, 40, 14);
                add(durability);
                
                dmg = new JLabel(Integer.toString(Main.getPlayer().getWeapons().get(i).getDamage()));
                dmg.setFont(Fonts.INVENTORY);
                dmg.setForeground(Color.WHITE);
                dmg.setHorizontalAlignment(SwingConstants.RIGHT);
                dmg.setBounds(i*40, 0, 40, 14);
                add(dmg);
            }
            // add the weapon or empty slot to the inventory
            wp.setBounds(i*40, 0, 40, 40);
            wp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            add(wp);
        }
    }
}