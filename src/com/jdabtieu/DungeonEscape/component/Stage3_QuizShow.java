package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Text;

public class Stage3_QuizShow extends JPanel {
    private String ans;
    private ArrayList<JTextField> letters;
    public Stage3_QuizShow(String title, String ans) {
        setBounds(Window.WIDTH / 4, Window.HEIGHT / 2 - 100, Window.WIDTH / 2, 200);
        setLayout(null);
        setBackground(Color.GRAY);
        Text txt = new Text(title, 0, 10);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 10, getWidth(), 40);
        txt.setFont(new Font("Sitka Text", Font.BOLD, 16));
        add(txt);
        
        letters = new ArrayList<>();
        for (int i = 0; i < ans.length(); i++) {
            if (ans.charAt(i) == ' ') continue;
            JTextField f = new JTextField();
            f.setBounds((10 + 40 * i) % getWidth(), 60 * ((10 + 40 * i) / getWidth() + 1), 30, 30);
            letters.add(f);
            add(f);
        }
        
        this.ans = ans.replace(" ", "").toLowerCase();
        
        JButton submit = new JButton("Submit");
        submit.setBounds(getWidth() / 2 - 50, 160, 100, 30);
        add(submit);
        submit.addActionListener(e -> {
            synchronized(this) {
                notify();
            }
        });
        Main.getContentPane().add(this, 5, 0);
    }
    
    public boolean selection() {
        synchronized(this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Main.getContentPane().remove(this);
        for (int i = 0; i < ans.length(); i++) {
            if (letters.get(i).getText().toLowerCase().charAt(0) != ans.charAt(i)) return false;
        }
        return true;
    }
}
