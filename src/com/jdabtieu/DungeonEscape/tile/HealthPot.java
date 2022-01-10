package com.jdabtieu.DungeonEscape.tile;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
/**
 * A health potion tile. It restores the player to 80 health.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class HealthPot extends Ground implements Triggerable {
    /**
     * Whether the health potion is still active
     */
    boolean active;
    
    /**
     * Creates a health potion
     */
    public HealthPot() {
        super();
        final JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/healthpot.png")));
        active = true;
        setLayout(null);
        setBackground(Ground.COLOR);
        icon.setBounds(0, 0, 20, 20);
        add(icon);
    }

    @Override
    public void trigger() {
        if (!active) return;
        active = false;
        Main.getPlayer().setHealth(Math.max(Main.getPlayer().getHealth(), 80));
        new BasicDialog("The fountain of youth has restored you to 80 health.").selection();
        removeAll();
    }

}
