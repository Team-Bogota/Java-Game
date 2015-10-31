package game;

import display.Display;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game implements Runnable {
    private String title;
    private int width, height;

    private Thread thread;

    private boolean isRunning;

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;
    private SpriteSheet spsh;

    private Font statsFont = new Font("Monospaced", Font.BOLD | Font.ITALIC, 22);

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;
    }

    private void init() {
        this.display = new Display(title, width, height);
        this.spsh = new SpriteSheet(ImageLoader.loadImage("/images/blocks.png"));
    }

    private void tick() {

    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();

        if (this.bs == null) {
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }

        this.graphics = this.bs.getDrawGraphics();

        this.graphics.drawImage(ImageLoader.loadImage("/images/background.png"), 0, 20, null);

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
        this.graphics.dispose();
    }

    @Override
    public void run() {
        this.init();

        while (isRunning) {
            this.tick();
            this.render();
        }

        this.stop();
    }

    public synchronized void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public synchronized void stop() {
        if (this.isRunning) {
            try {
                this.isRunning = false;
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}