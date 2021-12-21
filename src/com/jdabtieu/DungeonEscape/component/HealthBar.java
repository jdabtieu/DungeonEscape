package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.plaf.metal.MetalProgressBarUI;

public class HealthBar extends JProgressBar {
    public HealthBar(int maxHealth) {
        super(0, maxHealth);
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
    
    public void setHealth(int newHealth) {
        newHealth = Math.max(newHealth, 0);
        setValue(newHealth);
        setString(Integer.toString(newHealth));
    }
}
