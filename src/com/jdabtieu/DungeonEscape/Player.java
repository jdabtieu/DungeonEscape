package com.jdabtieu.DungeonEscape;

import java.awt.Color;

import com.jdabtieu.DungeonEscape.MapComponent.Tile;

public class Player extends Tile {
    public int coins;
    private int health;
    public int x;
    public int y;
    public Player() {
        super();
        setBackground(Color.green);
        x = 0;
        y = 0;
        setVisible(false);
        health = 100;
        coins = 0;
    }
    
    public void changeHealth(int dh) {
        if (health + dh > 0) {
            health += dh;
            return;
        }
        health = 0;
        System.out.println("You're dead!");
        Main.triggerGameOver();
    }
    
    public int getHealth() {
        return health;
    }
}