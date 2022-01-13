package com.jdabtieu.DungeonEscape.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Player;
/**
 * The status bar for the player. This displays health, coins, and keys.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class StatusDisplay extends JPanel {
    /**
     * Label for the number of coins
     */
    private final JLabel coins;
    
    /**
     * Label for number of keys
     */
    private final JLabel keys;
    
    /**
     * Healthbar
     */
    private final HealthBar healthBar;
    
    /**
     * Creates the status display.
     */
    public StatusDisplay() {
        super();
        final JLabel imgCoins = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/coins.png")));
        final JLabel imgHealth = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/health.png")));
        final JLabel imgKeys = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/keys.png")));
        
        setBounds(10, 10, 160, 80);
        setLayout(null);
        setBackground(Color.GRAY);
        setVisible(false);
        setOpaque(false);
        
        // add icons
        imgCoins.setBounds(10, 44, 24, 24);
        add(imgCoins);
        imgHealth.setBounds(10, 4, 24, 24);
        add(imgHealth);
        imgKeys.setBounds(92, 44, 24, 24);
        add(imgKeys);
        
        // add counters and healthbar
        healthBar = new HealthBar(100);
        healthBar.setBounds(50, 6, 100, 20);
        add(healthBar);
        
        coins = new JLabel();
        coins.setBounds(44, 44, 38, 24);
        coins.setForeground(Color.WHITE);
        add(coins);
        
        keys = new JLabel();
        keys.setBounds(124, 44, 28, 24);
        keys.setForeground(Color.WHITE);
        add(keys);
    }
    
    @Override
    public void repaint() {
        final Player p = Main.getPlayer();
        // since statusdisplay is created in player's constructor,
        // player could still be null
        if (p != null) {
            if (healthBar != null) healthBar.setValue(p.getHealth());
            if (coins != null) coins.setText(Integer.toString(p.getCoins()));
            if (keys != null) keys.setText(Integer.toString(p.getKeys()));
        }
        super.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        gd.setColor(Color.BLACK);
        gd.setStroke(new BasicStroke(2));
        gd.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 10, 10));
    }
}
