package com.jdabtieu.DungeonEscape.core;

import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Window extends JFrame {
    public static final int WIDTH = 960, HEIGHT = 720;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Window() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("assets/icon.png"));
        setResizable(false);
        setTitle("DungeonGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setContentPane(new JLayeredPane());
        getContentPane().setLayout(null);
    }
}
