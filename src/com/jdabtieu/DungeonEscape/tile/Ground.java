package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
/**
 * A ground tile. Its color is also used for other tiles, such as hidden sensors.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Ground extends Tile {
    /**
     * The base ground color
     */
    public final static Color COLOR = Color.GRAY;
    
    /**
     * Creates a ground tile
     */
    public Ground() {
        super();
        setBackground(COLOR);
    }
}
