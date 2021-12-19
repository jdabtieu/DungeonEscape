package com.jdabtieu.DungeonEscape.MapComponent;

import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Window;

public class Tile extends JPanel {
    public int x;
    public int y;
    public Tile() {
        super();
        setVisible(true);
        x = Window.width / 2 - 10;
        y = Window.height / 2 - 10;
        setBounds(x, y, 20, 20);
    }

}
