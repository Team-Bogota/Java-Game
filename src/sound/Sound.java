package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    private String soundName;

    public Sound(String soundName) {
        this.soundName = soundName;
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(this.soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}