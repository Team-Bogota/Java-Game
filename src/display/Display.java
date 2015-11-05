package display;

import game.Config;
import game.InputHandler;
import game.KeyGetter;
import gfx.ImageLoader;
import states.GameState;
import states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.SortedSet;
import java.util.TreeSet;

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

            int testHighScore = readHighScore();
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
            okayButton.addActionListener(e1 -> {
                InputHandler.pause = false;
                alert.dispose();
            });
            alert.add(score);
            alert.add(okayButton);
        }));

        JMenuItem save = new JMenuItem("Save game");
        save.addActionListener(e -> {
            GameState.isSaved = true;
        });

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
        menu.add(save);
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



        // ---- Creating buttons for the main Menu

        // Button - New Game
        JButton bNewGame = createNewMainMenuButton("/images/MenuButton_NewGame.png", 79, 237, 305, 38);
        bNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideFrame();
                StateManager.setState(new GameState("Tetris", 456, 553));
            }
        });

        // Button - Load Saved Game
        JButton bLoadSavedGame = createNewMainMenuButton("/images/MenuButton_LoadSavedGame.png", 79, 275, 305, 38);
        bLoadSavedGame.addActionListener(e -> {
            hideFrame();
            StateManager.setState(new GameState("Tetris", 456, 553));
            GameState.isLoaded = true;
        });

        // Button - High Scores
        JButton bHighScores = createNewMainMenuButton("/images/MenuButton_HighScore.png", 79, 313, 305, 38);
        bHighScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { DisplayHighScore.createHighScoreWindow();}
        });

        // Button - About
        JButton bAbout = createNewMainMenuButton("/images/MenuButton_About.png", 79, 351, 305, 38);
        bAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayAbout.createAboutWindow();
            }
        });

        // Button - Exit
        JButton bExit = createNewMainMenuButton("/images/MenuButton_Exit.png", 79, 389, 305, 38);
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // ------------------------


        // ---- Adding buttons
        this.frame.add(bNewGame);
        this.frame.add(bLoadSavedGame);
        this.frame.add(bHighScores);
        this.frame.add(bAbout);
        this.frame.add(bExit);
        // --------------------

        this.frame.add(this.canvas);


        this.frame.pack();


    }

    public static JButton createNewMainMenuButton(String source, int positionX, int positionY, int sizeX, int sizeY) {
        JButton newButton = new JButton();
        newButton.setIcon(new javax.swing.ImageIcon(ImageLoader.class.getResource(source)));
        newButton.setBorderPainted(true);
        newButton.setBounds(positionX, positionY, sizeX, sizeY);
        newButton.setBorder(null);
        newButton.setOpaque(false);
        newButton.setContentAreaFilled(false);
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newButton.setBorder(BorderFactory.createBevelBorder(1));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                newButton.setBorder(null);
            }
        });

        return newButton;
    }

    private int readHighScore(){

        int highScore = 0;

        File source = new File("score.save");
        SortedSet<Integer> scores = new TreeSet<>();

        if (source.exists()){

            try(ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("score.save") )))) {

                scores = (SortedSet<Integer>) inputStream.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!scores.isEmpty()){
            highScore = scores.last();
        }

        return highScore;
    }

    public void hideFrame() {
        this.frame.setVisible(false);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}