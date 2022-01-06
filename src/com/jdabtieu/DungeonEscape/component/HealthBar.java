package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.plaf.metal.MetalProgressBarUI;
/**
 * Creates a healthbar with variable health. Used for player and boss health.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class HealthBar extends JProgressBar {
    /**
     * The maximum health this healthbar can store
     */
    private final int maxHealth;
    
    /**
     * Creates a healthbar with the specified max health.
     * @param maxHealth the maximum possible health
     */
    public HealthBar(final int maxHealth) {
        super(0, maxHealth);
        this.maxHealth = maxHealth;
        
        setBackground(Color.gray);
        setForeground(Color.red);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setValue(maxHealth);
        setStringPainted(true);
        setUI(new MetalProgressBarUI() {
            @Override
            protected Color getSelectionForeground() {
                return Color.white;
            }
            @Override
            protected Color getSelectionBackground() {
                return Color.white;
            }
        });
        setString(Integer.toString(maxHealth));
    }
    
    /**
     * Sets the health to the new health
     * @param newHealth new health value
     */
    public void setHealth(int newHealth) {
        newHealth = Math.max(newHealth, 0);
        setValue(newHealth);
        setString(Integer.toString(newHealth));
    }
    
    @Override
    public void setValue(final int val) {
        super.setValue(val);
        setString(Integer.toString(val));
    }
    
    /**
     * Returns the max health of this healthbar
     * @return the max health of this healthbar
     */
    public int getValue() {
        return maxHealth;
    }
}
