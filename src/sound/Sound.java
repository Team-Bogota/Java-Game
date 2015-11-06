package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private AudioInputStream audioInputStream;
    private Clip clip;

    public Sound(String soundName) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
            clip.open(this.audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        this.clip.start();
    }

    public void loop() {
        this.clip.loop(1000);

    }

    public void stop() {
        this.clip.stop();
        this.clip.close();
    }
}