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
        setValue(maxHealth);
        
        // override all the default styles
        setBackground(Color.GRAY);
        setForeground(Color.RED);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setStringPainted(true);
        setUI(new MetalProgressBarUI() {
            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE;
            }
            @Override
            protected Color getSelectionBackground() {
                return Color.WHITE;
            }
        });
    }
    
    /**
     * Sets the health to the new health
     * @param newHealth new health value
     */
    @Override
    public void setValue(int newHealth) {
        newHealth = Math.max(newHealth, 0);
        super.setValue(newHealth);
        setString(Integer.toString(newHealth));
    }
    
    /**
     * Returns the max health of this healthbar
     * @return the max health of this healthbar
     */
    public int maxHealth() {
        return maxHealth;
    }
}
