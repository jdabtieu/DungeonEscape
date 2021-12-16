package com.jdabtieu.DungeonEscape.MapComponent;

public class HiddenSensor extends Sensor implements Triggerable {
    public HiddenSensor(Runnable action) {
        super(action);
        setBackground(Ground.color);
    }
}
