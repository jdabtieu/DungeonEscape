package com.jdabtieu.DungeonEscape.map;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.stage.Stage;

public class Stage1_KeyPopup extends JPanel {

    /**
     * Create the panel.
     */
    public Stage1_KeyPopup() {
        Stage.pauseMovement = true;
        setBounds(Window.WIDTH / 2 - 100, Window.HEIGHT / 2 - 50, 200, 100);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        Main.me.getContentPane().add(this, 5, 0);
        
        JButton btnClose = new JButton("X");
        btnClose.setBackground(Color.LIGHT_GRAY);
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false);
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnClose.setBounds(162, 7, 28, 23);
        add(btnClose);
        
        JLabel lblWrong = new JLabel("You found a key!");
        lblWrong.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblWrong.setHorizontalAlignment(SwingConstants.CENTER);
        lblWrong.setBounds(0, 49, 200, 14);
        add(lblWrong);
        btnClose.addActionListener(e -> {
            setVisible(false);
            Main.me.remove(this);
            Stage.pauseMovement = false;
        });
    }
}
