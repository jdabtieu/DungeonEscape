package com.jdabtieu.DungeonEscape.tile;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
/**
 * A powerup. Upon running into this tile, the player will be able to add 1 damage to any weapon.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Powerup extends Ground implements Triggerable {
    /**
     * Whether this powerup has been consumed
     */
    private boolean used;
    
    /**
     * Creates a Powerup tile
     */
    public Powerup() {
        super();
        final JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/powerup.png")));
        setLayout(null);
        icon.setBounds(0, 0, 20, 20);
        add(icon);
        used = false;
    }
    
    @Override
    public void trigger() {
        if (used) return;
        used = true;
        removeAll();
        Main.getPlayer().weaponSelect("<html>Choose a weapon to<br>apply +1 damage</html>");
        Main.getPlayer().getActiveWeapon().powerup();
        Main.getPlayer().repaintInventory();
    }
}
