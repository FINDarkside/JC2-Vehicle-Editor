package logic;

import java.io.File;
import java.net.URISyntaxException;

/**
 *
 * @author FINDarkside
 */
public class Settings {

    public final static String currentPath = getPath();
    public static final String version = "";

    public static File blender = null;
    public static boolean closeProjectsOnExit = true;
    public static boolean saveXmlInEez = true;
    public static boolean saveOnExit = false;

    public static void setBlender(File f) {
        blender = f;
    }

    private static String getPath() {
        String path;
        try {
            path = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException ex) {
            StackTracePrinter.handle(ex);
            return System.getProperty("user.dir");
        }
        if (path.endsWith("\\build")) {
            return path.substring(0, path.indexOf("\\build"));
        } else {
            return path;
        }
    }

    public static void save() {

    }

}
