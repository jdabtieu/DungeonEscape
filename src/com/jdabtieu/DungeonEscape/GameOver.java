package com.jdabtieu.DungeonEscape;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Component.MenuButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GameOver extends JPanel {
    private static final long serialVersionUID = 5237335232850181080L;

    /**
     * Create the frame.
     * @throws IOException 
     */
    public GameOver() {
        super();
        setBounds(0, 0, Window.width, Window.height);
        setLayout(null);
        Main.me.getContentPane().add(this);
        
        MenuButton btnStart = new MenuButton("Back to Main Menu");
        btnStart.setBounds((Window.width - 400) / 2, 450, 400, 40);
        add(btnStart);
        
        JLabel lblDead = new JLabel("You died!");
        lblDead.setHorizontalAlignment(SwingConstants.CENTER);
        lblDead.setFont(new Font("Sitka Text", Font.BOLD, 16));
        lblDead.setForeground(Color.RED);
        lblDead.setBounds(0, 240, Window.width, 20);
        add(lblDead);
        
        JLabel lblScore = new JLabel("Final Score: " + Main.player.coins);
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setFont(new Font("Sitka Text", Font.BOLD, 16));
        lblScore.setForeground(Color.RED);
        lblScore.setBounds(0, 300, Window.width, 20);
        add(lblScore);
        
        try {
            BufferedImage backgroundImg = ImageIO.read(new File("assets/background.png"));
            JLabel labelBackgroundImg = new JLabel(new ImageIcon(backgroundImg));
            labelBackgroundImg.setBounds(0, 0, Window.width, Window.height);
            add(labelBackgroundImg);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        btnStart.addActionListener(e -> {
            Main.gameOver = true;
            Main.mainThread.interrupt();
        });
    }
}
