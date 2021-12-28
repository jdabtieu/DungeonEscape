package com.jdabtieu.DungeonEscape.tile;

import javax.swing.JPanel;
/**
 * A tile. All game tiles that make up the map, such as walls and sensors are subclasses of this class.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public abstract class Tile extends JPanel {
    /**
     * Creates and makes a tile visible
     */
    public Tile() {
        super();
        setVisible(true);
    }

}
