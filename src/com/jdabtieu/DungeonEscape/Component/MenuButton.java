package com.jdabtieu.DungeonEscape.Component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MenuButton extends JButton {
    private static final long serialVersionUID = 7523096660885892240L;

    public MenuButton(String name) {
        super(name);
        setForeground(new Color(255, 255, 255));
        setBackground(new Color(128, 128, 128));
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setFont(new Font("Sitka Text", Font.BOLD, 16));
        setFocusPainted(false);
    }
}