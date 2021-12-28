package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
/**
 * A dark ground tile. It is similar to the Ground tile, but with a darker color.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class DarkGround extends Tile {
    /**
     * Creates a dark ground tile
     */
    public DarkGround() {
        super();
        setBackground(new Color(80, 80, 80));
    }
}
