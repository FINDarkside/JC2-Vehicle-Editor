package logic;

import logic.dictionaries.FieldsDictionary;
import gui.MainForm;
import gui.filetree.FileTreeModel;
import jtools.FileTools;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import jtools.GibbedsTools;
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
    private File customSavePath = new File("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Just Cause 2\\DrOpZoNe\\Vehicles");
    private File savePath = customSavePath;

    private Project currentProject;
    private Map<File, Project> projects = new HashMap<>();

    public void setForm(MainForm form) {
        this.form = form;
    }

    public Logic() {

    }

    public void test() {

        File test = new File("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Just Cause 2\\DrOpZoNe\\Vehicles\\lave.v034_sport_buggy.eez");

        loadFile(test);

    }

    public void loadFile(File f) {

        if (projects.containsKey(f)) {
            setCurrentProject(projects.get(f));
            return;
        }

        boolean newVehicle = f.getAbsolutePath().contains(currentPath + "\\Files\\Vehicles\\");

        File unpacked;
        Project project;
        try {
            System.out.println("Unpacking " + f.getAbsolutePath());
            unpacked = GibbedsTools.smallUnpack(f);
            if (newVehicle) {
                System.out.println("Moving " + unpacked.getAbsolutePath() + " to " + savePath);
                unpacked = FileTools.moveToFolder(unpacked, savePath);
            }
            System.out.println("Creating new project...");
            project = new Project(unpacked);
        } catch (Exception e) {
            cleanDefaultVehicleFolder();
            StackTracePrinter.handle(e);
            return;
        }
        projects.put(f, project);
        setCurrentProject(project);
    }

    public void setCurrentProject(Project project) {
        currentProject = project;
        form.setEditPanels(project.getPanels());
    }

    public void saveCurrentProject() {
        try {
            currentProject.save();
        } catch (Exception e) {
            StackTracePrinter.handle(e);
        }
    }

    public void saveAllProjects() {
        for (Project p : projects.values()) {
            try {
                p.save();
            } catch (Exception e) {
                StackTracePrinter.handle(e);
            }
        }

    }

    public void closeProject(File f) {
        projects.get(f).close();
    }

    public void closeAllProjects() {
        for (Project p : projects.values()) {
            p.close();
        }
    }

    public void editModel() {
        throw new UnsupportedOperationException();
    }

    public void cleanDefaultVehicleFolder() {
        for (File f : new File(Settings.currentPath + "\\Files\\Vehicles").listFiles()) {
            for (File f2 : f.listFiles()) {
                if (f2.isDirectory()) {
                    System.out.println("Deleting " + f2.getAbsolutePath());
                    FileTools.deleteFolder(f2);
                }
            }
        }
    }

    public void close() {
        if (Settings.saveOnExit) {
            saveAllProjects();
        }
        if (Settings.deleteUnpackedOnExit) {
            closeAllProjects();
        }
    }

}
