package logic;

import gui.MainForm;
import jtools.FileTools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Map<File, Project> projects = new HashMap<>();

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
        if (projects.containsKey(f)) {
            form.setEditPanel(projects.get(f).getPanel("test"));
            return true;
        }

        File unpacked = null;
        Project project;
        try {
            unpacked = FileTools.smallUnpack(f);
            unpacked = FileTools.moveFile(unpacked, savePath);
            project = new Project(unpacked);
        } catch (IOException | InterruptedException e) {
            StackTracePrinter.handle(e);
            return false;
        }

        this.currentFile = unpacked;
        form.setEditPanel(project.getPanel("test"));
        //test.update();

        projects.put(f, project);

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
