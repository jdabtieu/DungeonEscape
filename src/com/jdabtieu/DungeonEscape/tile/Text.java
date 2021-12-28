package com.jdabtieu.DungeonEscape.tile;

import javax.swing.JLabel;
/**
 * Text to be displayed on the map. It can hold one line of text, at the specified map location.
 * When the player moves, the text's position will be automatically modified to keep it at the
 * same location relative to the map.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Text extends JLabel {
    /**
     * The text's x position
     */
    private int x;
    
    /**
     * The text's y position
     */
    private int y;
    
    /**
     * Creates a Text object at the specified location
     * @param text  text to be displayed
     * @param x     x-coordinate of the text
     * @param y     y-coordinate of the text
     */
    public Text(String text, int x, int y) {
        super(text);
        this.x = x;
        this.y = y;
        setBounds(x, y, 1000, 20);
    }
    
    /**
     * Returns the x-position of the text, relative to the map
     * @return  the x-coordinate
     */
    public int getXFixed() {
        return x;
    }
    
    /**
     * Returns the y-position of the text, relative to the map
     * @return  the y-coordinate
     */
    public int getYFixed() {
        return y;
    }
}
