package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;

public class StatusDisplay extends JPanel {
    private JLabel coins;
    private JLabel keys;
    private HealthBar healthBar;
    public StatusDisplay() {
        super();
        setBounds(10, 10, 160, 120);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setBackground(Color.GRAY);
        setVisible(false);
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/coins.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds(10, 40, 32, 32);
            add(labelTitleText);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/health.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds(10, 0, 32, 32);
            add(labelTitleText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/keys.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds(10, 80, 32, 32);
            add(labelTitleText);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        healthBar = new HealthBar(100);
        healthBar.setBounds(50, 6, 100, 20);
        add(healthBar);
        
        coins = new JLabel();
        coins.setBounds(50, 40, 100, 32);
        coins.setForeground(Color.white);
        add(coins);
        
        keys = new JLabel();
        keys.setBounds(50, 80, 100, 32);
        keys.setForeground(Color.white);
        add(keys);
        repaint();
    }
    
    public void repaint() {
        if (healthBar != null) {
            healthBar.setValue(Main.player.getHealth());
            healthBar.setString(Integer.toString(Main.player.getHealth()));
        }
        if (coins != null) coins.setText(Integer.toString(Main.player.coins));
        if (keys != null) keys.setText(Integer.toString(Main.player.keys));
        super.repaint();
    }
}
