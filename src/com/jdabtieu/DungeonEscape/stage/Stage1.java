package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.io.IOException;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.map.Ground;
import com.jdabtieu.DungeonEscape.map.HiddenSensor;
import com.jdabtieu.DungeonEscape.map.Sensor;
import com.jdabtieu.DungeonEscape.map.Stage1_ComboLock;
import com.jdabtieu.DungeonEscape.map.Stage1_KeyPopup;
import com.jdabtieu.DungeonEscape.map.Text;
import com.jdabtieu.DungeonEscape.map.Wall;

public class Stage1 extends Stage {
    private boolean bossInit;
    private boolean comboLockEnabled;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage1() {
        super();
        bossInit = false;
        comboLockEnabled = false;
        fillStage("stage1");
        texts.add(new Text("Tip: Look around each room for room numbers and clues", 1000, 470));
        texts.add(new Text("Tip: You can return to previous rooms at any time", 1040, 50));
        texts.add(new Text("1__34_", 870, 320));
        texts.add(new Text("_42__2", 710, 320));
        texts.add(new Text("Tip: You can hold up to 3 weapons at a time. Any extras will be discarded.", 1900, 270));
        texts.add(new Text("Tip: Sensors can trigger different actions", 1350, 550));
        stage[3][127] = new Sensor(() -> initBoss());
        stage[4][127] = new Sensor(() -> initBoss());
        stage[5][127] = new Sensor(() -> initBoss());
        stage[29][74] = new Sensor(() -> {
            remove(stage[31][74]);
            stage[31][74] = new Wall();
            add(stage[31][74]);
        });
        stage[33][74] = new Sensor(() -> {
            remove(stage[31][74]);
            stage[31][74] = new Ground();
            add(stage[31][74]);
        });
        
        stage[3][14] = new HiddenSensor(() -> {
            if (comboLockEnabled) {
                comboLockEnabled = false;
                new Stage1_ComboLock();
            }
        });
        
        stage[3][12] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[4][12] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[5][12] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[5][13] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[5][14] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[5][15] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[5][16] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[4][16] = new HiddenSensor(() -> comboLockEnabled = true);
        stage[3][16] = new HiddenSensor(() -> comboLockEnabled = true);
        
        stage[3][73] = new HiddenSensor(() -> {
            remove(stage[3][73]);
            stage[3][73] = new Ground();
            add(stage[3][73]);
            new Stage1_KeyPopup();
            redraw();
            Main.player.keys++;
        }, Color.YELLOW);
        
        generateImg("stage1");
        finishConstructor();
    }
    
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        remove(stage[3][125]);
        stage[3][125] = new Wall();
        add(stage[3][125]);
        remove(stage[4][125]);
        stage[4][125] = new Wall();
        add(stage[4][125]);
        remove(stage[5][125]);
        stage[5][125] = new Wall();
        add(stage[5][125]);
    }
    
    public void correctCombo() {
        remove(stage[3][14]);
        stage[3][14] = new Ground();
        add(stage[3][14]);
        remove(stage[2][14]);
        stage[2][14] = new Ground();
        add(stage[2][14]);
        redraw();
    }
}
