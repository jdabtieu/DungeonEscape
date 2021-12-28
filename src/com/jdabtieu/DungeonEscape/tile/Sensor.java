package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
/**
 * A sensor tile. It performs an action when the user runs over it.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Sensor extends Ground implements Triggerable {
    /**
     * The action to be performed
     */
    private Runnable action;
    
    /**
     * Creates a sensor tile with the given action
     * @param action    the action to be performed
     */
    public Sensor(Runnable action) {
        super();
        setBackground(new Color(0, 160, 255));
        this.action = action;
    }
    
    @Override
    public void trigger() {
        action.run();
    }
}
