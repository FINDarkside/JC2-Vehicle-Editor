

package logic;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author FINDarkside
 */
public class Settings {
    
    private final static String currentPath = Paths.get("").toAbsolutePath().toString();
    
    public static File blender = null;
    
    public static void setBlender(File f){
        blender = f;
    }
    
    public static void loadSettings(){
        
    }
    
}
