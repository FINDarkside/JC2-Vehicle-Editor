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
    public static boolean deleteUnpackedOnExit = true;
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
        if (path.equals("C:\\Users\\Jesse\\Java\\Omat\\JC2 Vehicle Editor\\build")) {
            return "C:\\Users\\Jesse\\Java\\Omat\\JC2 Vehicle Editor";
        } else {
            return path;
        }
    }
    
    public static void save(){
        
    }

}
