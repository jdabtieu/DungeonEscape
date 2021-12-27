package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.Inventory;
import com.jdabtieu.DungeonEscape.component.Weapon;
import com.jdabtieu.DungeonEscape.tile.Tile;

public class Player extends Tile {
    public int coins;
    private int health;
    public int x;
    public int y;
    public int keys;
    private Inventory inv;
    private boolean pauseMovement;
    private final ArrayList<Weapon> weapons;
    private Weapon activeWeapon;
    private boolean interviewComplete;
    
    public Player() {
        super();
        setBackground(Color.green);
        x = Window.WIDTH / 2 - 10;
        y = Window.HEIGHT / 2 - 10;
        setBounds(x, y, 20, 20);
        
        health = 100;
        coins = 0;
        keys = 0;
        weapons = new ArrayList<>();
        inv = new Inventory();
        pauseMovement = false;
        setVisible(false);
        Main.getContentPane().add(inv, 3, 0);
    }
    
    public void setInventoryVisible(boolean aFlag) {
        inv.setVisible(aFlag);
    }
    
    public boolean isInterviewComplete() {
        return interviewComplete;
    }

    public void setInterviewComplete() {
        interviewComplete = true;
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }
    
    public void pauseMovement() {
        pauseMovement = true;
    }
    
    public void unpauseMovement() {
        pauseMovement = false;
    }
    
    public boolean movementPaused() {
        return pauseMovement;
    }
    
    public void weaponSelect(Object monitor) {
        weaponSelect(monitor, "Choose a Weapon");
    }

    public void weaponSelect(Object monitor, String pmt) {
        boolean movementPaused = pauseMovement;
        pauseMovement = true;
        JPanel contentPane = new JPanel();
        contentPane.setBounds(Window.WIDTH / 2 - 90, Window.HEIGHT / 2 - 120, 180, 240);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GREEN);
        
        JLabel title = new JLabel(pmt);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Sitka Text", Font.PLAIN, 14));
        title.setBounds(0, 0, 180, 30);
        contentPane.add(title);
        
        for (int i = 0; i < weapons.size(); i++) {
            final int f = i;
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBounds(20, 60 * i + 30, 140, 60);
            container.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            Weapon wp = weapons.get(i).clone();
            wp.setBounds(0, 10, 40, 40);
            JLabel lab = new JLabel("<html>" + wp.toString() + "</html>");
            lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lab.setBounds(40, 0, 200, 60);
            container.add(wp);
            container.add(lab);
            container.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    activeWeapon = weapons.get(f);
                    pauseMovement = movementPaused;
                    contentPane.setVisible(false);
                    Main.getContentPane().remove(contentPane);
                    synchronized(monitor) {
                        monitor.notify();
                    }
                }
            });
            contentPane.add(container);
        }
        Main.getContentPane().add(contentPane, 5, 0);
    }

    public int score() {
        return coins + 100 * keys + getWeapons().stream()
                                           .filter(e -> e != null)
                                           .mapToInt(e -> e.score()).sum();
    }
    
    public void changeHealth(int dh) {
        if (health + dh > 0) {
            health += dh;
            return;
        }
        health = 0;
        Main.triggerLoss();
    }
    
    public void addWeapon(Weapon wp) {
        weapons.add(wp);
        if (weapons.size() > 3) {
            weaponSelect(this, "<html>You found a new weapon,<br>but you can only hold 3.<br>Choose one to discard.</html>");
            try {
                synchronized(this) {
                    wait();                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            weapons.remove(activeWeapon);
            activeWeapon = null;
        } else {
            new BasicPopup("<html>You found a new weapon!<br>" + wp + "</html>", Color.BLACK);
        }
        inv.repaint();
    }
    
    public int getHealth() {
        return health;
    }
    
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (inv != null) inv.setVisible(aFlag);
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void repaintInventory() {
        SwingUtilities.invokeLater(() -> inv.repaint());
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}