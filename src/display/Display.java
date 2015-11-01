package display;

import game.Config;
import game.KeyGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Display extends Canvas{
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
        JMenu file = new JMenu("File");
        file.setBounds(0, 0, 45, 24);
        bar.add(file);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener((e -> {
            System.out.println("Starting new game..");
            //TODO : logic to start new game;
        }));

        JMenuItem highScore = new JMenuItem("Highscore");
        highScore.addActionListener((e -> {
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
            score.setBounds(0,0,200,50);

            JButton okayButton = new JButton("Okay");
            okayButton.setBounds(50,80,100,30);
            okayButton.addActionListener(e1 -> alert.dispose());
            alert.add(score);
            alert.add(okayButton);
        }));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e -> System.exit(0)));

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(e -> Config.openConfig(frame));

        file.add(newGame);
        file.add(highScore);
        file.add(options);
        file.add(exit);
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