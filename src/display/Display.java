package display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends Canvas{
    private String title;
    private int width, height;

    private JFrame frame;

    private Canvas canvas;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.createFrame();
    }

    private void createFrame() {
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

        JMenuBar bar = new JMenuBar();
        bar.setBounds(0, 0, this.width, 25);
        JMenu file = new JMenu("File");
        file.setBounds(0,0,45,24);
        bar.add(file);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("New game starting...");
                //TODO : logic to start new game;
            }
        }));

        JMenuItem highScore = new JMenuItem("Highscore");
        highScore.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //TODO : this highscore must be changed whit real one
                int highScore = 0;
                JFrame alert = new JFrame("High Score");
                alert.setSize(200, 150);
                alert.setLayout(null);
                alert.setLocationRelativeTo(null);
                JLabel score = new JLabel("The highscore is: " + highScore);
                score.setBounds(0,0,200,50);
                JButton okayButton = new JButton("Okay");
                okayButton.setBounds(50,80,100,30);
                alert.setResizable(false);
                alert.setVisible(true);
                okayButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        alert.dispose();
                    }
                });
                alert.add(score);
                alert.add(okayButton);
            }
        }));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        }));

        file.add(newGame);
        file.add(highScore);
        file.add(exit);
        this.frame.add(bar);
        this.frame.add(this.canvas);
        this.frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}