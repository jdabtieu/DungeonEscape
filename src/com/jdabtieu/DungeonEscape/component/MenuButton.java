package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MenuButton extends JButton {

    public MenuButton(String name) {
        super(name);
        setForeground(new Color(255, 255, 255));
        setBackground(new Color(128, 128, 128));
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setFont(new Font("Sitka Text", Font.BOLD, 16));
        setFocusPainted(false);
    }
}