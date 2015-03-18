package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import logic.Settings;

/**
 *
 * @author Jesse
 */
public class IDs {

    private static Dictionary dictionary;

    static {
        try {
            dictionary = new Dictionary(new File(Settings.currentPath + "\\Files\\Dictionary\\IDs.txt"), false);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load " + Settings.currentPath + "\\Files\\Dictionary\\IDs.txt");
        }
    }

    public static String get(String s) {
        return dictionary.get(s);
    }
}
