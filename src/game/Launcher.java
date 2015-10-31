package game;

import display.Display;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Tetris", 380, 440);
        game.start();
    }
}