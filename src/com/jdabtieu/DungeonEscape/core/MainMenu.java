package com.jdabtieu.DungeonEscape.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
        
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/titleText.png"));
            JLabel labelTitleText = new JLabel(new ImageIcon(titleText));
            labelTitleText.setBounds((Window.WIDTH - 834) / 2, 140, 834, 65);
            add(labelTitleText);  
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            BufferedImage backgroundImg = ImageIO.read(new File("assets/background.png"));
            JLabel labelBackgroundImg = new JLabel(new ImageIcon(backgroundImg));
            labelBackgroundImg.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
            add(labelBackgroundImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
