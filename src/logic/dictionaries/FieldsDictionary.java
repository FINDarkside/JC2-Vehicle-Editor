package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import java.util.*;
import jtools.FileTools;
import logic.FieldList;
import logic.Settings;

/**
 *
 * @author FINDarkside
 */
public class FieldsDictionary {

    private static List<FieldList> car = new ArrayList<>();
    private static List<FieldList> bike = new ArrayList<>();
    private static List<FieldList> helicopter = new ArrayList<>();
    private static List<FieldList> boat = new ArrayList<>();
    private static List<FieldList> plane = new ArrayList<>();

    public static void init() throws IOException {
        load(car, new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Car.txt"));
        load(bike, new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Bike.txt"));
        load(helicopter, new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Helicopter.txt"));
        load(boat, new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Boat.txt"));
        load(plane, new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Plane.txt"));
    }

    public static void load(List<FieldList> fl, File f) throws IOException {
        List<String> file = FileTools.readFile(f);

        String currentModule = null;
        FieldList currentFieldList = null;
        for (String s : file) {
            if (s.isEmpty()) {
                continue;
            }
            if (s.charAt(0) == '[') {
                currentModule = s.substring(1, s.length() - 1);
                if (currentFieldList != null) {
                    fl.add(currentFieldList);
                }
                currentFieldList = new FieldList(currentModule);
                continue;
            }
            currentFieldList.add(s);
        }
        fl.add(currentFieldList);

    }

    public static List<FieldList> getCar() {
        return car;
    }

    public static List<FieldList> getBike() {
        return bike;
    }

    public static List<FieldList> getHelicopter() {
        return helicopter;
    }

    public static List<FieldList> getPlane() {
        return plane;
    }

    public static List<FieldList> getBoat() {
        return boat;
    }

}
