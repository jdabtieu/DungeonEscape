package com.jdabtieu.DungeonEscape.tile;

import java.awt.Color;
/**
 * A wall tile. The player cannot walk through a wall tile. Any tiles that cannot be walked
 * through must extend this class.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Wall extends Tile {
    /**
     * Creates a wall tile
     */
    public Wall() {
        super();
        setBackground(Color.BLACK);
    }
}
