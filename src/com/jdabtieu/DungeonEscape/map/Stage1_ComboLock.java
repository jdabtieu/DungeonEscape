package com.jdabtieu.DungeonEscape.map;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.stage.Stage;
import com.jdabtieu.DungeonEscape.stage.Stage1;

public class Stage1_ComboLock extends JPanel {

    /**
     * Create the panel.
     */
    public Stage1_ComboLock() {
        Stage.pauseMovement = true;
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 100, 300, 200);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        Main.me.getContentPane().add(this, 5, 0);
        
        JTextField input = new JTextField();
        ((AbstractDocument) input.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offset, int len, String str, AttributeSet a) throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength()) + str;
                if (text.matches("^[0-9]{0,6}$")) {
                    super.replace(fb, offset, len, str, a);
                }
            }
            
            @SuppressWarnings("unused")
            public void insertString(FilterBypass fb, int offset, int len, String str, AttributeSet a) throws BadLocationException {
                replace(fb, offset, len, str, a);
            }
        });
        input.setFont(new Font("Consolas", Font.PLAIN, 14));
        input.setBounds(100, 85, 100, 30);
        add(input);
        
        JLabel title = new JLabel("ComboLock 8100");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Sitka Text", Font.BOLD, 12));
        title.setBounds(0, 0, 300, 40);
        add(title);
        
        JButton btnClose = new JButton("X");
        btnClose.setBackground(Color.LIGHT_GRAY);
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false);
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnClose.setBounds(262, 7, 28, 23);
        add(btnClose);
        
        JLabel lblWrong = new JLabel("That's the wrong combination...");
        lblWrong.setForeground(Color.RED);
        lblWrong.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblWrong.setHorizontalAlignment(SwingConstants.CENTER);
        lblWrong.setBounds(0, 149, 300, 14);
        lblWrong.setVisible(false);
        add(lblWrong);
        btnClose.addActionListener(e -> {
            setVisible(false);
            Main.me.remove(this);
            Stage.pauseMovement = false;
        });

        input.addActionListener(e -> {
            if (!input.getText().equals("142342")) {
                lblWrong.setVisible(true);
            } else {
                setVisible(false);
                Main.me.remove(this);
                Stage.pauseMovement = false;
                ((Stage1) Main.activeWindow).correctCombo();
            }
        });
    }
}
