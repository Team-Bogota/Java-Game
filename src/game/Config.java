package game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

    public static String rotate = "Up", left = "Left", right = "Right", down = "Down", pause = "P";
    public static ArrayList<Choice> choices;

    public static void openConfig(JFrame frame) {
        choices = new ArrayList<>();
        JFrame options = new JFrame("Options");
        options.setSize(400, 300);
        options.setResizable(false);
        options.setLocationRelativeTo(frame);
        options.setLayout(null);
        options.setVisible(true);
        Choice left = addChoice("Left", options, 30, 30);
        left.select(Config.left);
        Choice right = addChoice("Right", options, 150, 30);
        right.select(Config.right);
        Choice rotate = addChoice("Rotate", options, 150, 80);
        rotate.select(Config.rotate);
        Choice down = addChoice("Down", options, 30, 80);
        down.select(Config.down);
        Choice pause = addChoice("Pause", options, 30, 130);
        pause.select(Config.pause);

        JButton done = new JButton("Done");
        done.setBounds(150, 220, 100, 30);
        done.addActionListener(e -> {
            options.dispose();
            saveChanges();
        });
        options.add(done);
    }

    public static void saveChanges() {
        Choice left = choices.get(0);
        Choice right = choices.get(1);
        Choice down = choices.get(2);
        Choice rotate = choices.get(3);
        Choice pause = choices.get(4);

        Config.left = left.getSelectedItem();
        Config.right = right.getSelectedItem();
        Config.down = down.getSelectedItem();
        Config.rotate = rotate.getSelectedItem();
        Config.pause = pause.getSelectedItem();
        try {
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Choice addChoice(String name, JFrame options, int x, int y) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y - 20, 100, 20);
        Choice key = new Choice();
        for (String s : getKeyNames()) {
            key.add(s);
        }
        key.setBounds(x, y, 100, 20);
        options.add(key);
        options.add(label);
        choices.add(key);
        return key;
    }

    public static ArrayList<String> getKeyNames() {
        ArrayList<String> result = new ArrayList<>();
        for (String s : KeyGetter.keyNames) {
            result.add(s);
            if (s.equalsIgnoreCase("F24")) {
                break;

            }
        }
        return result;
    }

    public static void loadConfig() throws IOException {
        File directory = new File(getDefaultDirectory(), "/Tetris");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File config = new File(directory, "/config.txt");
        if (!config.exists()) {
            config.createNewFile();
            saveConfig();
            return;
        }
        Scanner s = new Scanner(config);
        HashMap<String, String> values = new HashMap<>();
        while (s.hasNextLine()) {
            String[] entry = s.nextLine().split(":");
            String key = entry[0];
            String value = entry[1];
            values.put(key, value);
        }

        if (values.size() != 5) {
            saveConfig();
            return;
        }

        if (!values.containsKey("left")
                || !values.containsKey("right")
                || !values.containsKey("down")
                || !values.containsKey("rotate")
                || !values.containsKey("pause")) {
            saveConfig();
            return;
        }

        String left = values.get("left");
        String right = values.get("right");
        String down = values.get("down");
        String rotate = values.get("rotate");
        String pause = values.get("pause");

        if (!getKeyNames().contains(left)
                && !getKeyNames().contains(right)
                && !getKeyNames().contains(down)
                && !getKeyNames().contains(rotate)
                && !getKeyNames().contains(pause)) {
            return;
        }

        Config.left = left;
        Config.right = right;
        Config.down = down;
        Config.rotate = rotate;
        Config.pause = pause;
    }

    public static void saveConfig() throws IOException {
        File directory = new File(getDefaultDirectory(), "/Tetris");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File config = new File(directory, "/config.txt");
        PrintWriter pw = new PrintWriter(config);
        pw.println("right:" + right);
        pw.println("left:" + left);
        pw.println("down:" + down);
        pw.println("rotate:" + rotate);
        pw.println("pause:" + pause);
        pw.close();
    }

    public static String getDefaultDirectory() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            return System.getenv("APPDATA");
        }
        if (OS.contains("MAC")) {
            return System.getProperty("user.home") + "Library/Application Support";
        }
        return System.getProperty("user.home");
    }
}
