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
    public static final String version = "";
    
    public static File blender = null;
    public static boolean closeProjectsOnExit = true;
    public static boolean saveXmlInEez = true;
    public static boolean saveOnExit = false;
    
    public static void setBlender(File f) {
        blender = f;
    }
    
    public static void init() {
        getPath();
    }
    
    private static String getPath() {
        String path;
        try {
            path = new File(Logic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException ex) {
            StackTracePrinter.handle(ex);
            return System.getProperty("user.dir");
        }
        if (path.contains("JC2 Vehicle Editor\\build")) {
            return path.substring(0, path.indexOf("\\build"));
        } else {
            return path;
        }
    }
    
    public static void save() {
        
    }
    
}
