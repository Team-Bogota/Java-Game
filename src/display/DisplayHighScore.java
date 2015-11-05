package display;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;


public class DisplayHighScore {

    public static void createHighScoreWindow() {
        JFrame frame;
        String title = "HighScore";
        int width = 300;
        int height = 200;


        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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

        StringBuilder textBuilder = new StringBuilder();
        List<Integer> scoresList = new ArrayList<>();
        scoresList.addAll(scores);

        System.out.println(scoresList.size());

        for (int i = scoresList.size() - 1, j = 1; (i >= 0) && (i >= scoresList.size() - 5) ; i--, j++) {

            textBuilder.append(j);
            textBuilder.append(". ");
            textBuilder.append(scoresList.get(i));
           textBuilder.append((System.getProperty("line.separator")));

        }

        JLabel labelScoreText = new JLabel("Score", SwingConstants.CENTER);
        labelScoreText.setText(textBuilder.toString());

        frame.add(labelScoreText);


    }

}
