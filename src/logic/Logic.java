package logic;

import gui.MainForm;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import jtools.DialogTools;
import jtools.GibbedsTools;
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
    
    public void loadFile(File file) {
        
        if (fileOpened(file)) {
            setCurrentProject(projects.get(file));
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
        projects.put(file, project);
        setCurrentProject(project);
        form.addProject(file);
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
        for (File f : projects.keySet()) {
            saveProject(f);
        }
    }
    
    public void saveProject(File file) {
        saveProject(projects.get(file));
    }
    
    public void saveProject(Project p) {
        try {
            if (p.isDefaultVehicle) {
                File location = DialogTools.chooseSaveLocation(form, p.getFile().getName(), savePath.getAbsolutePath());
            } else {
                p.save();
            }
        } catch (Exception e) {
            StackTracePrinter.handle(e);
        }
    }
    
    public void closeProject(File f) {
        if (isCurrentProject(f)) {
            form.setProject(null);
            currentProject = null;
        }
        projects.get(f).close();
        form.closeProject(f);
        projects.remove(f);
        
    }
    
    public void closeAllProjects() {
        for (File f : projects.keySet()) {
            projects.get(f).close();
            form.closeProject(f);
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
