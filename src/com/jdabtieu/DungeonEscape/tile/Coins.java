package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;

import com.jdabtieu.DungeonEscape.Main;
/**
 * A coins tile. It rewards the player with some coins when walked over.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Coins extends Ground implements Triggerable {
    /**
     * The value of this tile
     */
    private int value;
    
    /**
     * Creates a coins tile with the given value
     * @param value the value of this tile
     */
    public Coins(int value) {
        super();
        this.value = value;
        setBackground(Color.YELLOW);
    }
    
    @Override
    public void trigger() {
        if (value == 0) return;
        Main.getPlayer().addCoins(value);
        value = 0;
        setBackground(Ground.COLOR);
        repaint();
    }
}
