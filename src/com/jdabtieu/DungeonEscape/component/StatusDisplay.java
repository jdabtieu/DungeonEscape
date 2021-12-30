package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
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
    private JLabel coins;
    
    /**
     * Label for number of keys
     */
    private JLabel keys;
    
    /**
     * Healthbar
     */
    private HealthBar healthBar;
    
    /**
     * Creates the status display.
     */
    public StatusDisplay() {
        super();
        setBounds(10, 10, 160, 120);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setBackground(Color.GRAY);
        setVisible(false);
        
        JLabel imgCoins = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/coins.png")));
        imgCoins.setBounds(10, 40, 32, 32);
        add(imgCoins);
        
        JLabel imgHealth = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/health.png")));
        imgHealth.setBounds(10, 0, 32, 32);
        add(imgHealth);

        JLabel imgKeys = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/keys.png")));
        imgKeys.setBounds(10, 80, 32, 32);
        add(imgKeys);
        
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
    
    @Override
    public void repaint() {
        Player p = Main.getPlayer();
        if (p != null) {
            if (healthBar != null) healthBar.setValue(p.getHealth());
            if (coins != null) coins.setText(Integer.toString(p.getCoins()));
            if (keys != null) keys.setText(Integer.toString(p.getKeys()));
        }
        super.repaint();
    }
}
