package com.jdabtieu.DungeonEscape.core;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    private static Clip clip;
    private static final LineListener audioListener = e -> {
        if (e.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0);
            clip.start();
        }
    };
    
    private Music() {}
    
    public static void initAudio(final String relPath, final boolean repeat) {
        URL is;
        AudioInputStream ais;
        try {
            // Loading the audio file from the JAR
            is = new URL("file:assets/" + relPath);
            ais = AudioSystem.getAudioInputStream(is);
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, ais.getFormat()));
            if (repeat) {
                clip.addLineListener(audioListener);
            }
            clip.open(ais);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            clip = null;
            System.err.println("[WARNING] Audio failed to start.");
        }
    }
    
    public static void stopAudio() {
        if (clip == null) { // Audio failed to start; that's fine
            return;
        }
        clip.removeLineListener(audioListener);
        clip.stop();
        clip.close();
        clip = null; // Prevent access to the clip after closing
    }
}
