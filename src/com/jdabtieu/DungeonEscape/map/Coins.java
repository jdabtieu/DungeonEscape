package com.jdabtieu.DungeonEscape.map;

import java.awt.Color;

import com.jdabtieu.DungeonEscape.Main;

public class Coins extends Ground implements Triggerable {
    private int value;
    public Coins() {
        this(100);
    }
    
    public Coins(int value) {
        this.value = value;
        setBackground(Color.yellow);
    }
    
    public void trigger() {
        if (value == 0) return;
        Main.player.coins += value;
        value = 0;
        setBackground(Ground.COLOR);
        repaint();
    }
}
