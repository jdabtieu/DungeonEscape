package com.jdabtieu.DungeonEscape.map;

import java.awt.Color;

public class Sensor extends Ground implements Triggerable {
    private Runnable action;
    public Sensor(Runnable action) {
        super();
        setBackground(new Color(0, 160, 255));
        this.action = action;
    }
    
    public void trigger() {
        action.run();
    }
}
