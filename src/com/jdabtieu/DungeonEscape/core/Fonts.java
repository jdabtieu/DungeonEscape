package com.jdabtieu.DungeonEscape.core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
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
    public static final Font LARGE = load("SitkaText-02.ttf", Font.PLAIN, 32);
    
    /**
     * Title font: Sitka Text BOLD 16pt
     */
    public static final Font TITLE = load("SitkaText-Bold-02.ttf", Font.BOLD, 16);
    
    /**
     * Weapon Selector font: Sitka Text 14pt
     */
    public static final Font WEAPON_SELECT = load("SitkaText-02.ttf", Font.PLAIN, 14);
    
    /**
     * Standard text font: Tahoma 12pt
     */
    public static final Font STD_PARA = load("tahoma.ttf", Font.PLAIN, 12);
    
    /**
     * Inventory numbers font: Tahoma 11pt
     */
    public static final Font INVENTORY = load("tahoma.ttf", Font.PLAIN, 11);
    
    /**
     * Close button font: Tahoma 10pt
     */
    public static final Font CLOSE_BTN = load("tahoma.ttf", Font.PLAIN, 10);
    
    /**
     * Monospace font: Consolas 14pt
     */
    public static final Font MONO = load("consola.ttf", Font.PLAIN, 14);
    
    /**
     * Prevent instantiation of the Fonts class
     */
    private Fonts() {}
    
    /**
     * Loads a font with the provided information. This acts like new Font(name, style, size)
     * but loads from a file instead of from the system.
     * 
     * @param fname the filename, without path, of the TrueType font
     * @param style the style of the font
     * @param size  the size of the font
     * @return  a reference to the font, or null if it is invalid
     */
    private static Font load(String fname, int style, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("assets/font/" + fname)).deriveFont(style, size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
