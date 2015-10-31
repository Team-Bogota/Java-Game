package display;

import javax.swing.*;
import java.awt.*;

public class Display extends Canvas {
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

        this.frame.add(this.canvas);
        this.frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}