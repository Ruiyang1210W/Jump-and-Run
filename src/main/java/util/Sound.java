package util;

import javax.sound.sampled.*;
import java.io.InputStream;

public class Sound {
    private static Clip clip;

    /** Plays a sound once (like jump or goal). */
    public static void play(String path) {
        try (InputStream in = Sound.class.getResourceAsStream(path);
             AudioInputStream audio = AudioSystem.getAudioInputStream(in)) {
            Clip c = AudioSystem.getClip();
            c.open(audio);
            c.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Plays looping background music (BGM). */
    public static void playLoop(String path) {
        stop(); // Stop any current BGM
        try (InputStream in = Sound.class.getResourceAsStream(path);
             AudioInputStream audio = AudioSystem.getAudioInputStream(in)) {
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Stop any currently playing sound. */
    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /** (Optional) Change BGM volume. 0.0 = mute, 1.0 = full */
    public static void setVolume(float volume) {
        if (clip != null) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gain.getMaximum() - gain.getMinimum();
            float gainValue = gain.getMinimum() + range * volume;
            gain.setValue(gainValue);
        }
    }
}

