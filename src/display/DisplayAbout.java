package display;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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


        JTextPane aboutText = new JTextPane();
        JScrollPane jsp = new JScrollPane(aboutText);

        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
                new FileReader("resources/txt/about.txt")
        )) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        aboutText.setText(sb.toString());
        frame.add(jsp);


    }
}
