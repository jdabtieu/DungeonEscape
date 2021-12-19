package com.jdabtieu.DungeonEscape.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.jdabtieu.DungeonEscape.MapComponent.*;

public class Stage1 extends Stage {
    private boolean bossInit;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage1() {
        super();
        bossInit = false;
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
        generateImg("stage1");
        finishConstructor();
    }
    
    private void initBoss() {
        if (bossInit) return;
        bossInit = true;
        remove(stage[3][125]);
        stage[3][125] = new Wall();
        remove(stage[4][125]);
        stage[4][125] = new Wall();
        remove(stage[5][125]);
        stage[5][125] = new Wall();
    }
}
