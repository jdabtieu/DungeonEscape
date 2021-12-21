package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;

public class HiddenSensor extends Sensor implements Triggerable {
    public HiddenSensor(Runnable action) {
        this(action, Ground.COLOR);
    }
    
    public HiddenSensor(Runnable action, Color color) {
        super(action);
        setBackground(color);
    }
}
