

package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author FINDarkside
 */
public class VehicleNames {
    private static Dictionary fileToName;
    private static Dictionary nameToFile;
    
    public static void init() throws IOException{
        fileToName = new Dictionary(new File(Paths.get("").toAbsolutePath().toString()+"\\Files\\Dictionary\\Vehicles"),false);
        nameToFile = new Dictionary(new File(Paths.get("").toAbsolutePath().toString()+"\\Files\\Dictionary\\Vehicles"),true);
    }
    
    public static String getName(String s){
        return fileToName.get(s);
    }
    
    public static String getFile(String s){
        return nameToFile.get(s);
    }
}
