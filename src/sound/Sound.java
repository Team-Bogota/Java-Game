package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private AudioInputStream audioInputStream;
    private File file;
    private Clip clip;

    public Sound(String soundFileName) {
        this.file = new File(soundFileName);
    }

    public void loop(int loops) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(this.file);
            this.clip = AudioSystem.getClip();
            this.clip.open(this.audioInputStream);
            this.clip.loop(loops);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }
}