package game;

import states.GameState;
import states.StateManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

// Removed the arguments from Game() and moved them directly into the GameState class, allowing change and manipulation only in the GameState class // AleksandarTanev
public class Game implements Runnable {

    private Thread thread;
    private boolean isRunning;

    public Game() {
        this.isRunning = false;
    }

    private void init() {

        // Setting the starting state to be the GameState. The starting state in the future will be changed to MenuState() // AleksandarTanev
        StateManager.setState(new GameState("Tetris", 456, 528));
    }

    private void tick() {


    }

    private void render() {

        // Running the tick() and render() of the current set state - at the moment we have only the GameState active // AleksandarTanev
        StateManager.getState().tick();
        StateManager.getState().render();

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