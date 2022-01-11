package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.jdabtieu.DungeonEscape.core.Fonts;
/**
 * Specifies the style for a menu button, used in the main menu and death screens.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class MenuButton extends JButton {
    /**
     * Creates a menu button with the specified text
     * @param name  text to be displayed on the button
     */
    public MenuButton(final String name) {
        super(name);
        
        // override all the default styles
        setForeground(Color.WHITE);
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setFont(Fonts.TITLE);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // use our custom press/hover animation
        if (getModel().isPressed() || getModel().isRollover()) {
            g.setColor(Color.GRAY.brighter());
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}