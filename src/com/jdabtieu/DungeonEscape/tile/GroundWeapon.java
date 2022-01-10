package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Weapon;
/**
 * A ground weapon. Upon running into this tile, the player will pick up the associated weapon.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class GroundWeapon extends Ground implements Triggerable {
    /**
     * The weapon associated with this tile
     */
    Weapon wp;
    
    /**
     * Creates a GroundWeapon tile
     * @param wp    the weapon to be picked up when the player runs into this tile
     */
    public GroundWeapon(Weapon wp) {
        super();
        final JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/weapon.png")));
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.wp = wp;
        icon.setBounds(0, 0, 20, 20);
        add(icon);
    }
    
    @Override
    public void trigger() {
        if (wp == null) return;
        // transform this tile visually into a ground tile
        removeAll();
        setBorder(null);
        
        Main.getPlayer().addWeapon(wp);
        wp = null;
    }
}
