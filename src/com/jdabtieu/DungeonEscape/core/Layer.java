package com.jdabtieu.DungeonEscape.core;
/*
 * The following components should use the following layers, by using constants in this class:
 * 7: stage 3 ending credits scene
 * 5: popups / any other pop-up window that the player can interact with
 * 4: status display, inventory
 * 3: enemies, enemy health bars, enemy banners
 * 2: player
 * 1: background, map
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 *
 */
public class Layer {
    /**
     * The layer to be used for any backgrounds or maps
     */
    public static final int MAP = 1;
    
    /**
     * The layer to be used for the player
     */
    public static final int PLAYER = 2;
    
    /**
     * The layer to be used for anything related to enemies
     */
    public static final int ENEMY = 3;
    
    /**
     * The layer to be used for anything related to the player
     */
    public static final int PLAYER_INFO = 4;
    
    /**
     * The layer to be used for all popups
     */
    public static final int POPUP = 5;
    
    /**
     * The layer to be used specially for the stage 3 ending
     */
    public static final int STAGE3_END = 7;
    
    /**
     * The bottom layer to be used for visual effects 
     */
    public static final int VFX_0 = 1000;
    
    /**
     * Prevent instantiation of this class
     */
    private Layer() {}
}
