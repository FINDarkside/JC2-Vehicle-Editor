package logic;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 *
 * @author FINDarkside
 */
public class Settings {

    public final static String currentPath = getPath();

    public static File blender = null;

    public static void setBlender(File f) {
        blender = f;
    }

    public static void init() {
        getPath();
    }

    private static String getPath() {
        if (currentPath != null) {
            return currentPath;
        }
        String tmp;
        try {
            tmp = new File(Logic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException ex) {
            StackTracePrinter.handle(ex);
            return currentPath;
        }
        if (tmp.equals("C:\\Users\\Jesse\\Java\\Omat\\JC2 Vehicle Editor\\build")) {
            System.out.println("Jekku");
            return currentPath;
        } else {
            return currentPath;
        }
    }

}
