package com.jdabtieu.DungeonEscape.core;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.MenuButton;
/**
 * This class is responsible for rendering the Main Menu screen.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class MainMenu extends JPanel {
    /**
     * Create the screen
     */
    public MainMenu() {
        super();
        final MenuButton btnStart = new MenuButton("Start Game");
        final MenuButton btnQuit = new MenuButton("Quit");
        final MenuButton btnControls = new MenuButton("Controls");
        final JLabel lblTitleTxt = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/titleText.png")));
        final JLabel lblBackgroundImg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/background.png")));
        
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setLayout(null);
        
        btnStart.setBounds((Window.WIDTH - 400) / 2, 340, 400, 40);
        add(btnStart);
        
        btnQuit.setBounds((Window.WIDTH - 400) / 2, 400, 400, 40);
        add(btnQuit);
        
        btnControls.setBounds((Window.WIDTH - 400) / 2, 460, 400, 40);
        add(btnControls);
        
        lblTitleTxt.setBounds((Window.WIDTH - 834) / 2, 140, 834, 65);
        add(lblTitleTxt);
        
        lblBackgroundImg.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        add(lblBackgroundImg);
        
        btnStart.addActionListener(e -> {
            synchronized(Main.mon) {
                Main.mon.notify();
            }
        });
        btnQuit.addActionListener(e -> System.exit(0));
        btnControls.addActionListener(e -> {
            new Thread(() -> new BasicDialog("<html>Controls:<br>WASD to move</html>").selection()).start();
        });
    }
}
