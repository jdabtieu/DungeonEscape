package com.jdabtieu.DungeonEscape.core;

import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.MenuButton;

public class MainMenu extends JPanel {

    /**
     * Create the frame.
     * @throws IOException 
     */
    public MainMenu() {
        super();
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setLayout(null);
        
        MenuButton btnStart = new MenuButton("Start Game");
        btnStart.setBounds((Window.WIDTH - 400) / 2, 350, 400, 40);
        add(btnStart);
        
        MenuButton btnQuit = new MenuButton("Quit");
        btnQuit.setBounds((Window.WIDTH - 400) / 2, 410, 400, 40);
        add(btnQuit);
        
        JLabel labelTitleText = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/titleText.png")));
        labelTitleText.setBounds((Window.WIDTH - 834) / 2, 140, 834, 65);
        add(labelTitleText);
        
        JLabel labelBackgroundImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/background.png")));
        labelBackgroundImg.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        add(labelBackgroundImg);
        
        btnStart.addActionListener(e -> {
            synchronized(Main.mon) {
                Main.mon.notify();
            }
        });
        
        btnQuit.addActionListener(e -> {
            System.exit(0);
        });
    }
}
