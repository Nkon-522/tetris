package org.nkon.tetris.Managers;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    private static Clip backgroundClip;
    private static Clip  musicClip;
    private static final URL[] url = new URL[10];

    static {
        url[0] = SoundManager.class.getResource("/Sounds/white-labyrinth-active.wav");
        url[1] = SoundManager.class.getResource("/Sounds/delete-line.wav");
        url[2] = SoundManager.class.getResource("/Sounds/gameover.wav");
        url[3] = SoundManager.class.getResource("/Sounds/rotation.wav");
        url[4] = SoundManager.class.getResource("/Sounds/touch-floor.wav");
    }

    public static void play(int i, boolean music) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music && i != 0) {
                musicClip = clip;
            } else if (music) {
                backgroundClip = clip;
            }

            clip.open(audioInputStream);
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            audioInputStream.close();
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void playBackground() {
        play(0, true);
        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopBackground() {
        backgroundClip.stop();
        backgroundClip.close();
    }

    public static void handleBackground() {
        if (backgroundClip != null) {
            if(backgroundClip.isOpen()) {
                stopBackground();
            }else{
                playBackground();
            }
        }
    }

    public static void stop() {
        musicClip.stop();
        musicClip.close();
    }
}
