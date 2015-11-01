package states;

import display.Display;
import gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class MenuState extends State {

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;

    public MenuState() {
        this.display = new Display("MenuState", "Tetris", 456, 527);
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

        graphics.drawImage(ImageLoader.loadImage("/images/mainmenu.png"), 0, 0, null);


        this.bs.show();
        graphics.dispose();
    }
}
