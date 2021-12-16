package com.jdabtieu.DungeonEscape;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public static final int width = 960, height = 720;
    private static final long serialVersionUID = 5237335232850181080L;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Window() throws IOException {
        setIconImage(Toolkit.getDefaultToolkit().getImage("assets/icon.png"));
        setResizable(false);
        setTitle("DungeonGame");
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
    }
}
