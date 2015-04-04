package logic;

import gui.MainForm;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import jtools.DialogTools;
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
    private File savePath = defaultSavePath;

    private Project currentProject;
    private List<Project> projects = new ArrayList<>();

    public Logic() {

    }

    public void setForm(MainForm form) {
        this.form = form;
    }

    public void test() {

    }

    public boolean fileOpened(File f) {
        return getProject(f) != null;
    }

    public Project getProject(File f) {
        for (Project p : projects) {
            if (p.getEez().equals(f)) {
                return p;
            }
        }
        return null;
    }

    public boolean isCurrentProject(File f) {
        return currentProject.getEez().equals(f);
    }

    public void loadFile(File file) {

        if (fileOpened(file)) {
            setCurrentProject(getProject(file));
            return;
        }

        Project project;
        try {
            System.out.println("Creating new project...");
            project = new Project(file);
        } catch (InterruptedException | IOException | ParserConfigurationException | SAXException e) {
            cleanDefaultVehicleFolder();
            StackTracePrinter.handle(e);
            return;
        }
        projects.add(project);
        setCurrentProject(project);
        form.addProject(project);
    }

    public void setCurrentProject(Project project) {
        currentProject = project;
        form.setProject(project);
    }

    public void saveCurrentProject() {
        try {
            saveProject(currentProject);
        } catch (Exception e) {
            StackTracePrinter.handle(e);
        }
    }

    public void saveAllProjects() {
        for (Project p : projects) {
            saveProject(p);
        }
    }

    public void saveProject(File file) {
        saveProject(getProject(file));
    }

    public void saveProject(Project p) {
        try {
            if (p.isDefaultVehicle()) {
                File tmpLocation = p.getEez();
                File location = DialogTools.chooseSaveLocation(form, p.getEez().getName(), savePath.getAbsolutePath());
                p.save(location);
            } else {
                p.save();
            }
        } catch (Exception e) {
            StackTracePrinter.handle(e);
        }
    }

    public void closeProject(File f) {
        Project project = getProject(f);
        if (project.equals(currentProject)) {
            form.setProject(null);
            currentProject = null;
        }
        project.close();
        form.closeProject(project);
        projects.remove(project);

    }

    public void closeAllProjects() {
        for (Project p : projects) {
            p.close();
            form.closeProject(p);
        }
        projects.clear();
        form.setProject(null);
        currentProject = null;
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
        FileTools.deleteFolder(new File(Settings.currentPath + "\\tmp"));
        form.saveState();
    }

}
