package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import logic.Settings;

/**
 *
 * @author Jesse
 */
public class Tooltips {

    private static Dictionary dictionary;

    static {
        try {
            dictionary = new Dictionary(new File(Settings.currentPath + "\\Files\\Dictionary\\Tooltips.txt"), false);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load " + Settings.currentPath + "\\Files\\Dictionary\\Tooltips.txt");
        }
    }

    public static String get(String s) {
        return dictionary.get(s);
    }
}
