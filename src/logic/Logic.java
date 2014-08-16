package logic;

import gui.MainForm;
import jtools.FileTools;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
    private Map projects = new HashMap<String, Project>();

    public void setForm(MainForm form) {
        this.form = form;
    }

    public Logic() {
        try {
            VehicleNames.init();
        } catch (IOException e) {
            StackTracePrinter.handle(e);
        }
    }

    public void test() {

        File test = new File(currentPath + "\\Files\\Vehicles\\Cars\\lave.v035_sport_custom.eez");
        loadFile(test);


    }

    public boolean loadFile(File f) {
        File unpacked = null;
        Project test;
        try {
            unpacked = FileTools.smallUnpack(f);
            unpacked = FileTools.moveFile(unpacked, savePath);
            test = new Project(unpacked);
        } catch (IOException | InterruptedException e) {
            if(unpacked != null){
                FileTools.deleteFolder(unpacked);
            }
            StackTracePrinter.handle(e);
            return false;
        }

        this.currentFile = unpacked;
        form.setEditPanel(test.getPanel("test"));
        //test.update();
        return true;
    }

    public void saveFile() {
        try {
            File packed = FileTools.smallPack(currentFile);
        } catch (IOException | InterruptedException e) {
            StackTracePrinter.handle(e);
        }
    }

    public void editModel() {
        throw new UnsupportedOperationException();
    }
}
