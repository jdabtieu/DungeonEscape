package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.util.ArrayList;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.map.Tile;

public class Player extends Tile {
    public int coins;
    private int health;
    public int x;
    public int y;
    public int keys;
    private Inventory inv;
    public final ArrayList<Weapon> weapons;
    private Weapon activeWeapon;
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
        setVisible(false);
        Main.me.getContentPane().add(inv, 5, 0);
    }
    
    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public void setActiveWeapon(Weapon activeWeapon) {
        this.activeWeapon = activeWeapon;
    }

    public int score() {
        return coins + 100 * keys + weapons.stream()
                                           .filter(e -> e != null)
                                           .mapToInt(e -> e.score()).sum();
    }
    
    public void changeHealth(int dh) {
        if (health + dh > 0) {
            health += dh;
            return;
        }
        health = 0;
        Main.triggerGameOver();
    }
    
    public void addWeapon(Weapon wp) {
        if (weapons.size() >= 3) {
            // TODO discard extra
            return;
        } else {
            new BasicPopup("<html>You found a new weapon!<br>" + wp + "</html>", Color.BLACK);
        }
        weapons.add(wp);
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
}