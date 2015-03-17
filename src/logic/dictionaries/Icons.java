package logic.dictionaries;

import java.io.File;
import java.util.*;
import javax.swing.ImageIcon;
import logic.Settings;

/**
 *
 * @author Jesse
 */
public class Icons {

    private final static Map<String, ImageIcon> icons = new HashMap<>();

    static {
        File root = new File(Settings.currentPath + "\\Files\\Icons");
        for (File f : root.listFiles()) {
            String name = f.getName();
            if (name.endsWith(".png")) {
                icons.put(name.substring(0, name.lastIndexOf(".")), new ImageIcon(f.getAbsolutePath()));
            }
        }
    }

    public static ImageIcon get(String name) {
        return icons.get(name);
    }
}
