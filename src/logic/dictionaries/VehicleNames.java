

package logic.dictionaries;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author FINDarkside
 */
public class VehicleNames {
    private final static Dictionary fileToName = new Dictionary(new File(Paths.get("").toAbsolutePath().toString()+"\\Files\\Dictionary\\Vehicles"),false);
    private final static Dictionary nameToFile = new Dictionary(new File(Paths.get("").toAbsolutePath().toString()+"\\Files\\Dictionary\\Vehicles"),true);
    
    public static String getName(String s){
        return fileToName.get(s);
    }
    
    public static String getFile(String s){
        return nameToFile.get(s);
    }
}
