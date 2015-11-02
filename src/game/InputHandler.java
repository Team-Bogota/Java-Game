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
    //all checks are whit string compare - name of configured keys and name of used keys
    public void keyPressed(KeyEvent e) {
       /* if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
            left = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
            right = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
            down = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
            rotate = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
            pause = true;
        }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
            left = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
            right = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
            down = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
            rotate = true;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
            pause = true;
        }
    }
}