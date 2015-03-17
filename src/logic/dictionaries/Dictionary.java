package logic.dictionaries;

import jtools.FileTools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author FINDarkside
 */
public class Dictionary {

    private HashMap<String, String> dictionary = new HashMap<>();

    public Dictionary(File file, boolean inverted) throws IOException {

        List<String> text = Files.readAllLines(file.toPath());

        for (String s : text) {
            String key, value;
            key = s.substring(0, s.indexOf(':'));
            value = s.substring(s.indexOf(':') + 1, s.length());
            if (inverted) {
                dictionary.put(value, key);
            } else {
                dictionary.put(key, value);
            }
        }

    }

    public String get(String key) {
        return dictionary.get(key);
    }

}
