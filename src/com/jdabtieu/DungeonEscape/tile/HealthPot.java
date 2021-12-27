package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicPopup;

public class HealthPot extends Ground implements Triggerable {
    private int uses;
    public HealthPot() {
        setLayout(null);
        setBackground(Ground.COLOR);
        uses = 1;
        try {
            JLabel icon = new JLabel(new ImageIcon(ImageIO.read(new File("assets/healthpot.png"))));
            icon.setBounds(0, 0, 20, 20);
            add(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trigger() {
        if (uses <= 0) return;
        uses--;
        Main.getPlayer().setHealth(Math.max(Main.getPlayer().getHealth(), 80));
        Main.getSD().repaint();
        new BasicPopup("The fountain of youth has restored you to 80 health.", Color.BLACK);
        if (uses == 0) {
            removeAll();
        }
    }

}
