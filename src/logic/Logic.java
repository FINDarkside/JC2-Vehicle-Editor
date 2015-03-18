package logic;

import gui.MainForm;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import jtools.DialogTools;
import jtools.GibbedsTools;

/**
 *
 * @author FINDarkside
 */
public class Logic {

    private MainForm form;

    private final String currentPath = Settings.currentPath;

    private File defaultSavePath = new File(currentPath + "\\Dropzone");
    private File customSavePath = new File("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Just Cause 2\\DrOpZoNe\\Vehicles");
    private File savePath = defaultSavePath;

    private Project currentProject;
    private Map<File, Project> projects = new HashMap<>();

    public Logic() {

    }

    public void setForm(MainForm form) {
        this.form = form;
    }

    public void test() {

    }

    public boolean fileOpened(File f) {
        return projects.containsKey(f);
    }

    public boolean isCurrentProject(File f) {
        return projects.get(f) == currentProject;
    }

    public void loadFile(File f) {

        boolean newVehicle = f.getAbsolutePath().startsWith(currentPath + "\\Files\\Default vehicles\\");
        File location = new File(newVehicle ? savePath + "\\" + f.getName() : f.getAbsolutePath());

        if (fileOpened(location)) {
            setCurrentProject(projects.get(location));
            return;
        }

        File unpacked;
        Project project;
        try {
            if (newVehicle) {

                if (location.exists() && !DialogTools.confirm(form, location.getAbsolutePath() + " already exists. Overwrite?", "Confirm")) {
                    return;
                }
                System.out.println("Copying " + f.getAbsolutePath() + " to " + location.getAbsolutePath());
                f = FileTools.copyFile(f, savePath);
            }
            System.out.println("Unpacking " + f.getAbsolutePath());
            unpacked = GibbedsTools.smallUnpack(f);
            System.out.println("Creating new project...");
            project = new Project(unpacked);
        } catch (Exception e) {
            cleanDefaultVehicleFolder();
            StackTracePrinter.handle(e);
            return;
        }
        projects.put(f, project);
        setCurrentProject(project);
        form.addProject(f);
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
        for (File f : projects.keySet()) {
            saveProject(f);
        }
    }

    public void saveProject(File file) {
        try {
            projects.get(file).save();
        } catch (Exception e) {
            StackTracePrinter.handle(e);
        }
    }

    public void closeProject(File f) {
        if (isCurrentProject(f)) {
            form.setEditPanels(null);
        }
        projects.get(f).close();
        form.closeProject(f);
        projects.remove(f);

    }

    public void closeAllProjects() {
        Iterator<File> it = projects.keySet().iterator();
        while (it.hasNext()) {
            File f = it.next();
            projects.get(f).close();
            form.closeProject(f);
            it.remove();
        }
        form.setEditPanels(null);
    }

    public void editModel() {
        throw new UnsupportedOperationException();
    }

    public void cleanDefaultVehicleFolder() {
        for (File f : new File(Settings.currentPath + "\\Files\\Default vehicles").listFiles()) {
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
        if (Settings.closeProjectsOnExit) {
            closeAllProjects();
        }
        form.saveState();
    }

}
