package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import logic.Settings;

/**
 *
 * @author FINDarkside
 */
public class VehicleNames {

    private static Dictionary fileToName;
    private static Dictionary nameToFile;

    static {
        try {
            fileToName = new Dictionary(new File(Settings.currentPath + "\\Files\\Dictionary\\Vehicles.txt"), false);
            nameToFile = new Dictionary(new File(Settings.currentPath + "\\Files\\Dictionary\\Vehicles.txt"), true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getName(String file) {
        return fileToName.get(file);
    }

    public static String getFile(String name) {
        return nameToFile.get(name);
    }
}
