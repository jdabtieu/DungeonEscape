package com.jdabtieu.DungeonEscape.core;

import java.util.Arrays;
import java.awt.Color;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.map.Tile;

public class Player extends Tile {
    public int coins;
    private int health;
    public int x;
    public int y;
    public int keys;
    public final Weapon[] weapons;
    public Player() {
        super();
        setBackground(Color.green);
        x = Window.WIDTH / 2 - 10;
        y = Window.HEIGHT / 2 - 10;
        setBounds(x, y, 20, 20);
        setVisible(false);
        
        health = 100;
        coins = 0;
        keys = 0;
        weapons = new Weapon[3];
    }
    
    public int score() {
        return coins + 100 * keys + Arrays.stream(weapons)
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
    
    public int getHealth() {
        return health;
    }
}