package states;

import display.Display;
import gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

import gfx.SpriteSheet;

// This is the main state // AleksandarTanev
public class GameState extends State {

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;
    private SpriteSheet spsh;

    private Font statsFont = new Font("Monospaced", Font.BOLD | Font.ITALIC, 22);


    public GameState(String title, int width, int height) {

        this.display = new Display(title, width, height);
        this.spsh = new SpriteSheet(ImageLoader.loadImage("/images/blocks.png"));
    }


    @Override
    public void tick() {

    }

    @Override
    public void render() {

        this.bs = this.display.getCanvas().getBufferStrategy();

        if (this.bs == null) {
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        graphics = this.bs.getDrawGraphics();

        graphics.drawImage(ImageLoader.loadImage("/images/background.png"), 0, 0, null);

        // THIS IS SOME RANDOM BLOCKS PRINTING JUST FOR TEST PURPOSE
        Random rnd = new Random();
        for (int row = 1; row <= 20; row++) {
            for (int col = 1; col <= 10; col++) {
                int color = rnd.nextInt(8);
                this.graphics.drawImage(this.spsh.crop(color * 24, 0, 24, 25), col * 24, 20 + row * 24, null);
            }
        }

        this.graphics.setFont(statsFont);
        //this.graphics.drawString("12", 270, 254);
        //this.graphics.drawString("3698", 270, 304);
        //this.graphics.drawString("987", 270, 354);

        // END OF TEST PRINTING

        this.bs.show();
        graphics.dispose();
    }
}