

package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import logic.Settings;

/**
 *
 * @author FINDarkside
 */
public class VehicleNames {
    private static Dictionary fileToName;
    private static Dictionary nameToFile;
    
    public static void init() throws IOException{
        fileToName = new Dictionary(new File(Settings.currentPath+"\\Files\\Dictionary\\Vehicles.txt"),false);
        nameToFile = new Dictionary(new File(Settings.currentPath+"\\Files\\Dictionary\\Vehicles.txt"),true);
    }
    
    public static String getName(String file){
        return fileToName.get(file);
    }
    
    public static String getFile(String name){
        return nameToFile.get(name);
    }
}
