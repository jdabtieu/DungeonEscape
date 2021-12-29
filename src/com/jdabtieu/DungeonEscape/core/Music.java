package com.jdabtieu.DungeonEscape.core;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * This class is responsible for playing music throughout the game.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Music {
    /**
     * These objects control the background music used in initAudio() and
     * stopAudio()
     */
    private static Clip clip;
    private static final LineListener audioListener = e -> {
        if (e.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0);
            clip.start();
        }
    };
    
    /**
     * Prevent instantiation of this class
     */
    private Music() {}
    
    /**
     * Attempts to start an audio track. A warning is printed if audio cannot be started.
     * @param relPath   a relative path to the file to be played
     * @param repeat    whether the music should loop forever
     */
    public static void initAudio(final String relPath, final boolean repeat) {
        URL is;
        AudioInputStream ais;
        try {
            // Loading the audio file from the JAR
            is = new URL("file:assets/music/" + relPath);
            ais = AudioSystem.getAudioInputStream(is);
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, ais.getFormat()));
            if (repeat) {
                clip.addLineListener(audioListener);
            }
            clip.open(ais);
            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15.0f);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            clip = null;
            System.err.println("WARN: Audio failed to start.");
        }
    }
    
    /**
     * Stops the current playing track, if anything is playing
     */
    public static void stopAudio() {
        if (clip == null) { // Nothing is playing; that's fine
            return;
        }
        clip.removeLineListener(audioListener);
        clip.stop();
        clip.close();
        clip = null; // Prevent access to the clip after closing
    }
}
