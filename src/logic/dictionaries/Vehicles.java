package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import logic.Settings;

/**
 *
 * @author FINDarkside
 */
public class Vehicles {

    private static Dictionary fileToName;

    static {
        try {
            fileToName = new Dictionary(new File(Settings.currentPath + "\\Files\\Dictionary\\Vehicles.txt"), false);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getName(String file) {
        return fileToName.get(file);
    }

    public static VehicleType getVehicleType(File f) {
        String name = f.getName();
        if (name.startsWith("seve")) {
            return VehicleType.BOAT;
        }
        if (name.startsWith("arve")) {
            return name.contains("plane") || name.contains("jet") ? VehicleType.PLANE : VehicleType.HELICOPTER;
        }

        int vehNum = Integer.parseInt(name.substring(6, 9));
        return vehNum == 6 || vehNum == 7 || vehNum == 52 || vehNum == 55 ? VehicleType.BIKE : VehicleType.CAR;
    }
}
