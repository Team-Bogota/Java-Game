package display;

import javax.swing.*;


public class DisplayAbout {

    public static void createAboutWindow() {
        JFrame frame;
        String title = "About";
        int width = 300;
        int height = 200;


        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel labelAboutText = new JLabel("Test", SwingConstants.CENTER);
        labelAboutText.setText("Here we will have information about the game.");
        frame.add(labelAboutText);


    }
}
