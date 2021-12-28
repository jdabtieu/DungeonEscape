package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
/**
 * A hidden sensor tile. It is identical to a Sensor, except that its color matches the ground color.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class HiddenSensor extends Sensor implements Triggerable {
    /**
     * Creates a hidden sensor that perfoms the given action
     * @param action    the action to be performed
     */
    public HiddenSensor(Runnable action) {
        this(action, Ground.COLOR);
    }
    
    /**
     * Creates a hidden sensor that perfoms the given action, with the given color
     * @param action    the action to be performed
     * @param color     the color of this sensor
     */
    public HiddenSensor(Runnable action, Color color) {
        super(action);
        setBackground(color);
    }
}
