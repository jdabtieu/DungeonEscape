package com.jdabtieu.DungeonEscape.Component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.metal.MetalProgressBarUI;

import com.jdabtieu.DungeonEscape.Main;

public class StatusDisplay extends JPanel {
    private JLabel coins;
    private JProgressBar healthBar;
    public StatusDisplay() {
        super();
        setBounds(10, 10, 140, 100);
        setLayout(null);
        setOpaque(false);
        setVisible(false);
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/coins.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds(0, 40, 32, 32);
            add(labelTitleText);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/health.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds(0, 0, 32, 32);
            add(labelTitleText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        healthBar = new JProgressBar();
        healthBar.setBackground(Color.gray);
        healthBar.setForeground(Color.red);
        healthBar.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        healthBar.setValue(100);
        healthBar.setStringPainted(true);
        healthBar.setBounds(40, 6, 100, 20);
        healthBar.setUI(new MetalProgressBarUI() {
            @Override
            protected Color getSelectionForeground() {
                return Color.white;
            }
            @Override
            protected Color getSelectionBackground() {
                return Color.white;
            }
        });
        add(healthBar);
        
        coins = new JLabel();
        coins.setBounds(40, 40, 100, 32);
        coins.setForeground(Color.white);
        add(coins);
        repaint();
    }
    
    public void repaint() {
        if (healthBar != null) {
            healthBar.setValue(Main.player.getHealth());
            healthBar.setString(Integer.toString(Main.player.getHealth()));
        }
        if (coins != null) coins.setText(Integer.toString(Main.player.coins));
        super.repaint();
    }
}
