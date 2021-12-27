package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
/**
 * Main window for the game. The content pane is layered, and the following components should use the following layers:
 * 7: stage 3 ending credits scene
 * 5: popups / any other pop-up window that the player can interact with
 * 3: status display, inventory, enemies, enemy health bars
 * 2: player, boss banner
 * 1: background, map
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 *
 */
public class Window extends JFrame {
    public static final int WIDTH = 960, HEIGHT = 720;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Window() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("assets/icon.png"));
        setResizable(false);
        setTitle("DungeonEscape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setContentPane(new JLayeredPane());
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);
    }
}
