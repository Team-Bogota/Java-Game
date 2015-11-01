package game;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyGetter {

    public static HashMap<String, Integer> keys;
    public static ArrayList<String> keyNames;

   //take all keys names and their integer values and save in hashmap, and additional save names in list
    public static void loadKeys() {
        keys = new HashMap<>();
        keyNames = new ArrayList<>();
        Field[] fields = KeyEvent.class.getFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                if (f.getName().startsWith("VK")) {
                    try {
                        int num = f.getInt(null);
                        String name = KeyEvent.getKeyText(num);
                        keys.put(name, num);
                        keyNames.add(name);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
