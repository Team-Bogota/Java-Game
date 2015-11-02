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

    public static String rotate = "Up", left = "Left", right = "Right", down = "Down", pause = "P", instantDown = "Space";
    public static ArrayList<Choice> choices;

    //create options frame, and add event to save changes after done button is cklicked
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
        Choice instantDown = addChoice("InstantDown", options, 150, 130);
        instantDown.select(Config.instantDown);

        JButton done = new JButton("Done");
        done.setBounds(150, 220, 100, 30);
        done.addActionListener(e -> {
            options.dispose();
            saveChanges();
        });
        options.add(done);
    }

    //take player choice and save
    public static void saveChanges() {
        Choice left = choices.get(0);
        Choice right = choices.get(1);
        Choice rotate = choices.get(2);
        Choice down = choices.get(3);
        Choice pause = choices.get(4);
        Choice instantDown = choices.get(5);

        Config.left = left.getSelectedItem();
        Config.right = right.getSelectedItem();
        Config.down = down.getSelectedItem();
        Config.rotate = rotate.getSelectedItem();
        Config.pause = pause.getSelectedItem();
        Config.instantDown = instantDown.getSelectedItem();
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
            //F24 is magic number :), just keys and avents after isnt needed
            if (s.equalsIgnoreCase("F24")) {
                break;

            }
        }
        return result;
    }

    //create text file to hold player config if dont exist or load saved keys if exist
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
        //read, split and put in hash map saved config
        while (s.hasNextLine()) {
            String[] entry = s.nextLine().split(":");
            String key = entry[0];
            String value = entry[1];
            values.put(key, value);
        }

        //check readed info, we need 5 keys, if dont have load default config
        if (values.size() != 6) {
            saveConfig();
            return;
        }

        //chek readed info, we need this values, if dont have load default config
        if (!values.containsKey("left")
                || !values.containsKey("right")
                || !values.containsKey("down")
                || !values.containsKey("rotate")
                || !values.containsKey("pause")
                || !values.containsKey("instantDown")) {
            saveConfig();
            return;
        }

        String left = values.get("left");
        String right = values.get("right");
        String down = values.get("down");
        String rotate = values.get("rotate");
        String pause = values.get("pause");
        String instantDown = values.get("instantDown");

        //last check if hash map whit all possible keys hold loaded key names
        if (!getKeyNames().contains(left)
                && !getKeyNames().contains(right)
                && !getKeyNames().contains(down)
                && !getKeyNames().contains(rotate)
                && !getKeyNames().contains(pause)
                && !getKeyNames().contains(instantDown)) {
            return;
        }

        //set game keys to saved
        Config.left = left;
        Config.right = right;
        Config.down = down;
        Config.rotate = rotate;
        Config.pause = pause;
        Config.instantDown = instantDown;
    }

    //create file if dont exist and write current key config
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
        pw.println("instantDown:" + instantDown);
        pw.close();
    }

    //take path to directory used for load and save for Windows, mac or other OS platform inipendent
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
