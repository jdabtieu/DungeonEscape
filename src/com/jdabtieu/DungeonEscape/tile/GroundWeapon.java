package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GroundWeapon extends Sensor {

    public GroundWeapon(Runnable action) {
        super(action);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Ground.COLOR);
        try {
            JLabel icon = new JLabel(new ImageIcon(ImageIO.read(new File("assets/weapon.png"))));
            icon.setBounds(0, 0, 20, 20);
            add(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
