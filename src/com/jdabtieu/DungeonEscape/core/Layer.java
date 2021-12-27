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
    public static final int MAP = 1;
    public static final int PLAYER = 2;
    public static final int ENEMY = 3;
    public static final int PLAYER_INFO = 4;
    public static final int POPUP = 5;
    public static final int STAGE3_END = 7;
    private Layer() {}
}
