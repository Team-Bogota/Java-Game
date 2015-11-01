package game;

import display.Display;

public class Launcher {
    public static void main(String[] args) {

        // Removed the arguments from Game() and moved them directly into the GameState class, allowing change and manipulation only in the GameState class // AleksandarTanev
        Game game = new Game("Tetris", 456, 528);
        game.start();
    }
}