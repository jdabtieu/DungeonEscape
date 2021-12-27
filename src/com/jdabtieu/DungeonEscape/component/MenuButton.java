package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

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
    public MenuButton(String name) {
        super(name);
        setForeground(Color.WHITE);
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setFont(Fonts.TITLE);
        setFocusPainted(false);
    }
}