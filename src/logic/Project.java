package logic;

import gui.editpanel.CarGlobalModule;
import gui.editpanel.EditPanel;
import jtools.FileTools;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import jtools.XmlTools;

/**
 *
 * @author FINDarkside
 */
public class Project {
    
    private File file;
    private Map<String,EditPanel> panes = new HashMap<String,EditPanel>();
    private File mvdoll;
    private File mvdollXml;
    
    private List<String> header; //Stores the start of the xml file
    private List<String> families; // Stores the end of xml file
    
    private int modulesIndex;

    public Project(File file) throws IOException, InterruptedException {
        

        this.file = file;
        
        for (File f : file.listFiles()) {
            if (f.getName().endsWith(".mvdoll") || f.getName().endsWith(".vdoll")) {
                this.mvdoll = f;
                break;
            }
        }
        
        this.mvdollXml = FileTools.convert(mvdoll);
        List<String> xmlList = FileTools.readFile(mvdollXml);
        
        
        if (file.getName().contains("lave")) {
            parseLave(xmlList);
        } else if (file.getName().contains("arve")) {
            parseArve(xmlList);
        } else {
            parseSeve(xmlList);
        }
        
    }
    
    //TODO for loop for better performance
    private void parseLave(List<String> xmlList) {

        for (String s : xmlList) {
            if(s.contains("<object name=\"_default_modules\">")){
                this.modulesIndex = xmlList.indexOf(s);
            }
            if (s.contains("CCarGlobalModule")) {
                EditPanel panel = new CarGlobalModule(XmlTools.getElement(xmlList, xmlList.indexOf(s)-1));
                panes.put("test", panel);
            }
        }
    }
    
    public JPanel getPanel(String name){
        return panes.get(name);
    }
    
    private void parseArve(List<String> xmlList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void parseSeve(List<String> xmlList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
