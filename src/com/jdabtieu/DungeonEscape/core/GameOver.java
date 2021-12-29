package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.MenuButton;
/**
 * This class is responsible for rendering the Game Over screen.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class GameOver extends JPanel {
    /**
     * Create the screen
     */
    public GameOver() {
        super();
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setBackground(Color.BLACK);
        setLayout(null);
        
        MenuButton btnStart = new MenuButton("Back to Main Menu");
        btnStart.setBounds((Window.WIDTH - 400) / 2, 450, 400, 40);
        add(btnStart);
        
        JLabel title = new JLabel("Game Over!");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Fonts.LARGE);
        title.setForeground(Color.RED);
        title.setBounds(0, 144, 960, 56);
        add(title);
        
        JLabel lblDead = new JLabel("You died!");
        lblDead.setHorizontalAlignment(SwingConstants.CENTER);
        lblDead.setFont(Fonts.TITLE);
        lblDead.setForeground(Color.RED);
        lblDead.setBounds(0, 240, Window.WIDTH, 20);
        add(lblDead);
        
        JLabel lblScore = new JLabel("Final Score: " + Main.getPlayer().score());
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setFont(Fonts.TITLE);
        lblScore.setForeground(Color.RED);
        lblScore.setBounds(0, 300, Window.WIDTH, 20);
        add(lblScore);
        
        btnStart.addActionListener(e -> {
            Main.gameOver = true;
            Main.getMainThread().interrupt();
        });
    }
}
