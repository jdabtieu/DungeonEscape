package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * QuizShow is used to display a fill-in-the-blank question for the stage 3 quiz show.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class QuizShow extends JPanel {
    /**
     * The correct answer, without spaces, all lowercase
     */
    private String ans;
    
    /**
     * A list of all the input fields, in order
     */
    private ArrayList<JTextField> letters;
    
    /**
     * Creates the question panel
     * @param question  the question to be asked
     * @param ans       the answer to the question
     */
    public QuizShow(String question, String ans) {
        setBounds(Window.WIDTH / 4, Window.HEIGHT / 2 - 100, Window.WIDTH / 2, 200);
        setLayout(null);
        setBackground(Color.GRAY);
        JLabel txt = new JLabel(question);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 10, getWidth(), 40);
        txt.setFont(Fonts.TITLE);
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
        Main.getContentPane().add(this, Layer.POPUP, 0);
    }
    
    /**
     * Waits for the user to submit an answer, and return whether it's correct
     * @return  whether the user's answer is correct
     */
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
