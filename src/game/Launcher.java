package game;

import display.Display;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Tetris", 456, 548);
        game.start();
    }
}