package com.jdabtieu.DungeonEscape.MapComponent;

import javax.swing.JLabel;

public class Text extends JLabel {
    private int x;
    private int y;
    public Text(String text, int x, int y) {
        super(text);
        this.x = x;
        this.y = y;
        setBounds(x, y, 1000, 20);
    }
    
    public int getXFixed() {
        return x;
    }
    
    public int getYFixed() {
        return y;
    }
}
