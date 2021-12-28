package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;

import com.jdabtieu.DungeonEscape.Main;
/**
 * A spike tile. It deals 2 damage when the player runs into it.
 * The player is not damaged by standing next to a spike.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Spike extends Wall implements Triggerable {
    /**
     * Creates a spike tile
     */
    public Spike() {
        super();
        setBackground(Color.red);
    }

    @Override
    public void trigger() {
        Main.getPlayer().changeHealth(-2);
    }
}
