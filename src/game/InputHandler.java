package game;

import display.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public boolean left;
    public boolean right;
    public boolean rotate;
    public boolean down;
    public boolean pause;

    public InputHandler(Display display) {
        display.getCanvas().addKeyListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            this.left = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            this.right = true;
        } else if (keyCode == KeyEvent.VK_UP) {
            this.rotate = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            this.down = true;
        } else if (keyCode == KeyEvent.VK_P) {
            this.pause = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            this.left = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            this.right = false;
        } else if (keyCode == KeyEvent.VK_UP) {
            this.rotate = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            this.down = false;
        } else if (keyCode == KeyEvent.VK_P) {
            this.pause = false;
        }
    }
}