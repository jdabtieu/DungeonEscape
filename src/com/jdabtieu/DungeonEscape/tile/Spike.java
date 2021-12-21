package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;

import com.jdabtieu.DungeonEscape.Main;

public class Spike extends Wall implements Triggerable {
    public Spike() {
        super();
        setBackground(Color.red);
    }

    public void trigger() {
        Main.player.changeHealth(-2);
    }
}
