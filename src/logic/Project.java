package logic;

import gui.Field;
import gui.editpanel.EditPanel;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import jtools.XmlTools;
import logic.dictionaries.FieldsDictionary;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class Project {

    private File file;
    private List<EditPanel> panels = new ArrayList<>();
    private File mvdoll;
    private File mvdollXml;

    private XmlDocument doc;

    public Project(File file) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

        this.file = file;

        for (File f : file.listFiles()) {
            if (f.getName().endsWith(".mvdoll") || f.getName().endsWith(".vdoll")) {
                this.mvdoll = f;
                break;
            }
        }

        this.mvdollXml = FileTools.convert(mvdoll);

        doc = new XmlDocument(mvdollXml);

        if (file.getName().contains("lave")) {
            parseLave();
        } else if (file.getName().contains("arve")) {
            parseArve();
        } else {
            parseSeve();
        }

    }

    public void saveXml() {
        System.out.println("Saving " + mvdollXml.getAbsolutePath());
        for (EditPanel ep : panels) {
            ep.save();
        }
        try {
            XmlTools.saveDocument(doc.getDocument(), mvdollXml);
        } catch (TransformerException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EditPanel getPanel(int index) {
        return panels.get(index);
    }

    public List<EditPanel> getPanels() {
        return panels;
    }

    private void parseLave() {

        List<FieldList> fieldLists = FieldsDictionary.getCar();
        for (FieldList fl : fieldLists) {
            String objectName = fl.getObjectName();
            Element e = XmlTools.getElementByAttribute(doc.getDefaultModules(), objectName);

            if (e != null) {
                EditPanel panel = new EditPanel();
                panel.setName(objectName);
                panels.add(panel);

                for (String id : fl.getFieldNames()) {
                    Element fieldElement = XmlTools.getElementByAttribute(e, id);

                    if (fieldElement != null) {

                        panel.createTextField(fieldElement);
                    }
                }
            }
        }

    }
    
 

    private void parseArve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void parseSeve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
