package logic;

import logic.dictionaries.FieldsDictionary;
import gui.MainForm;
import jtools.FileTools;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import logic.dictionaries.VehicleNames;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class Logic {

    private MainForm form;

    private final String currentPath = Settings.currentPath;

    private File defaultSavePath = new File(currentPath + "\\Dropzone");
    private File customSavePath = null;
    private File savePath = defaultSavePath;

    private Project currentProject;
    private Map<File, Project> projects = new HashMap<>();

    public void setForm(MainForm form) {
        this.form = form;
    }

    public Logic() {
        try {
            VehicleNames.init();
            FieldsDictionary.init();
            Settings.init();
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
            //form.setEditPanel(projects.get(f).getPanel("test"));
            currentProject = projects.get(f);
            return true;
        }

        File unpacked;
        Project project;
        try {
            unpacked = FileTools.smallUnpack(f);
            unpacked = FileTools.moveFile(unpacked, savePath);
            project = new Project(unpacked);
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
            StackTracePrinter.handle(e);
            return false;
        }

        form.setEditPanels(project.getPanels());
        projects.put(f, project);
        currentProject = project;

        return true;

    }

    public void saveCurrentProject() {
        System.out.println("dasda");
        currentProject.saveXml();
        /*try {
         File packed = FileTools.smallPack(currentFile);
         } catch (IOException | InterruptedException e) {
         StackTracePrinter.handle(e);
         }*/
    }

    public void saveAllProjects() {

    }

    public void editModel() {
        throw new UnsupportedOperationException();
    }

}
