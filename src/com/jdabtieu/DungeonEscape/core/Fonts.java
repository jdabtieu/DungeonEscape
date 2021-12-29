package com.jdabtieu.DungeonEscape.core;

import java.awt.Font;
/**
 * Fonts stores a list of fonts to be used in the game. All fonts used must come from this class.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Fonts {
    /**
     * Large font: Sitka Text 32pt
     */
    public static final Font LARGE = new Font("Sitka Text", Font.PLAIN, 32);
    
    /**
     * Title font: Sitka Text BOLD 16pt
     */
    public static final Font TITLE = new Font("Sitka Text", Font.BOLD, 16);
    
    /**
     * Weapon Selector font: Sitka Text 14pt
     */
    public static final Font WEAPON_SELECT = new Font("Sitka Text", Font.PLAIN, 14);
    
    /**
     * Standard text font: Tahoma 12pt
     */
    public static final Font STD_PARA = new Font("Tahoma", Font.PLAIN, 12);
    
    /**
     * Inventory numbers font: Tahoma 11pt
     */
    public static final Font INVENTORY = new Font("Tahoma", Font.PLAIN, 11);
    
    /**
     * Close button font: Tahoma 10pt
     */
    public static final Font CLOSE_BTN = new Font("Tahoma", Font.PLAIN, 10);
    
    /**
     * Monospace font: Consolas 14pt
     */
    public static final Font MONO = new Font("Consolas", Font.PLAIN, 14);
    
    /**
     * Prevent instantiation of the Fonts class
     */
    private Fonts() {}
}
