
package logic;

import gui.MainForm;
import logic.dictionaries.Dictionary;
import jtools.FileTools;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jtools.XmlTools;
import logic.dictionaries.VehicleNames;


/**
 *
 * @author FINDarkside
 */
public class Logic {
    private MainForm form;

    private final String currentPath = Paths.get("").toAbsolutePath().toString();

    private File defaultSavePath = new File(currentPath + "\\Dropzone");
    private File customSavePath = null;
    private File savePath = defaultSavePath;
    
    private File currentFile;
    private Project currentProject;
    private Map projects = new HashMap<String,Project>();

    public void setForm(MainForm form){
        this.form = form;
    }
    
    public void test() {
        File test = new File(currentPath + "\\Files\\Vehicles\\Cars\\lave.v035_sport_custom.eez");
        loadFile(test);

        

    }

    public void loadFile(File f) {
        File unpacked = FileTools.smallUnpack(f);

        if (f == null) {
            return;
        }
        
        unpacked = FileTools.moveFile(unpacked, savePath);
        this.currentFile = unpacked;
        
        Project test = new Project(unpacked);
        form.setEditPanel(test.getPanel("test"));
        //test.update();
    }

    public void saveFile() {
        File packed = FileTools.smallPack(currentFile);      
    }

    public void editModel() {
        throw new UnsupportedOperationException();
    }
}
