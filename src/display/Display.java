package display;

import game.Config;
import game.InputHandler;
import game.KeyGetter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Display extends Canvas {

    public boolean newGame;
    private String title;
    private int width, height;
    private JFrame frame;
    private Canvas canvas;

    public Display(String displayFrame, String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        if (displayFrame.equals("GameState")) {
            this.createGameFrame();
        } else if (displayFrame.equals("MenuState")) {
            this.createMenuFrame();
        }
    }

    private void createGameFrame() {
        Dimension dimensions = new Dimension(this.width, this.height);

        this.frame = new JFrame(this.title);
        this.frame.setSize(this.width, this.height);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(dimensions);
        this.canvas.setMaximumSize(dimensions);
        this.canvas.setMinimumSize(dimensions);

        KeyGetter.loadKeys();
        try {
            Config.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenuBar bar = new JMenuBar();
        bar.setBounds(0, 0, this.width, 25);
        JMenu menu = new JMenu("Menu");
        menu.setBounds(0, 0, 45, 24);
        bar.add(menu);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            InputHandler.pause = true;
            JFrame areUSure = new JFrame("Are you sure?");
            areUSure.setSize(200, 140);
            areUSure.setLayout(null);
            areUSure.setLocationRelativeTo(null);
            areUSure.setResizable(false);
            areUSure.setVisible(true);
            areUSure.setResizable(false);
            JLabel label = new JLabel("Start new game?");
            label.setBounds(50, 0, 200, 50);
            JButton okayButton = new JButton("Ok");
            okayButton.setBounds(30, 60, 50, 30);
            okayButton.addActionListener(e1 -> {
                this.newGame = true;
                InputHandler.pause = false;
                areUSure.dispose();
            });
            JButton noButton = new JButton("No");
            noButton.setBounds(110, 60, 50, 30);
            noButton.addActionListener(e1 -> {
                areUSure.dispose();
                InputHandler.pause = false;
            });

            areUSure.add(label);
            areUSure.add(okayButton);
            areUSure.add(noButton);
        });

        JMenuItem highScore = new JMenuItem("Highscore");
        highScore.addActionListener((e -> {
            InputHandler.pause = true;
            //TODO : logic to display highscore;
            int testHighScore = 0;
            JFrame alert = new JFrame("High Score");
            alert.setSize(200, 150);
            alert.setLayout(null);
            alert.setLocationRelativeTo(null);
            alert.setResizable(false);
            alert.setVisible(true);
            alert.setResizable(false);

            JLabel score = new JLabel("The highscore is: " + testHighScore);
            score.setBounds(0, 0, 200, 50);

            JButton okayButton = new JButton("Okay");
            okayButton.setBounds(50, 80, 100, 30);
            okayButton.addActionListener(e1 ->{
                InputHandler.pause = false;
                alert.dispose();
            });
            alert.add(score);
            alert.add(okayButton);
        }));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e -> {
            InputHandler.pause = true;
            System.exit(0);
        }));

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(e -> {
            InputHandler.pause = true;
            Config.openConfig(frame);

        });

        menu.add(newGame);
        menu.add(highScore);
        menu.add(options);
        menu.add(exit);
        this.frame.add(bar);
        this.frame.add(this.canvas);
        this.frame.pack();
    }


    private void createMenuFrame() {
        Dimension dimensions = new Dimension(this.width, this.height);

        this.frame = new JFrame(this.title);
        this.frame.setSize(this.width, this.height);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(dimensions);
        this.canvas.setMaximumSize(dimensions);
        this.canvas.setMinimumSize(dimensions);

        // this.frame.add(bNewGame);
        this.frame.add(this.canvas);


        this.frame.pack();

    }

    public Canvas getCanvas() {
        return canvas;
    }
}