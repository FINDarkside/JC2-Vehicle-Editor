package logic;

import gui.editpanel.Field;
import gui.editpanel.EditPanel;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import jtools.GibbedsTools;
import jtools.XmlTools;
import logic.dictionaries.FieldsDictionary;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class Project {

    public  File file;
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

        this.mvdollXml = GibbedsTools.convert(mvdoll);

        doc = new XmlDocument(mvdollXml);

        if (file.getName().contains("lave")) {
            parseLave();
        } else if (file.getName().contains("arve")) {
            parseArve();
        } else {
            parseSeve();
        }
    }

    public File getFile() {
        return file;
    }
    
    

    public void save() throws TransformerException, IOException, InterruptedException {
        System.out.println("Saving " + mvdollXml.getAbsolutePath());
        for (EditPanel ep : panels) {
            ep.save();
        }

        XmlTools.saveDocument(doc.getDocument(), mvdollXml);

        GibbedsTools.convert(mvdollXml);
        GibbedsTools.smallPack(file);
    }

    public void close() {
        FileTools.deleteFolder(this.file);
    }

    public EditPanel getPanel(int index) {
        return panels.get(index);
    }

    public List<EditPanel> getPanels() {
        return panels;
    }

    private void parseFields(Element doc, Element parseDoc, EditPanel panel) {
        if (doc == null) {
            return;
        }

        NodeList panelList = XmlTools.getChildElementsByTagName(parseDoc, "panel");
        for (int i = 0; i < panelList.getLength(); i++) {
            Element panelElement = (Element) panelList.item(i);
            EditPanel childPanel = new EditPanel(panelElement.getAttribute("name"));
            panel.addPanel(childPanel);
            parseFields(doc, panelElement, childPanel);
        }

        NodeList fieldsList = XmlTools.getChildElementsByTagName(parseDoc, "fields");
        NodeList docObjects = XmlTools.getChildElementsByTagName(doc, "object");
        for (int i = 0; i < fieldsList.getLength(); i++) {
            Element fieldsElement = (Element) fieldsList.item(i);
            if (fieldsElement.getAttribute("root").isEmpty()) {
                parseFields(doc, fieldsElement, panel);
                continue;
            }

            Element docElement = XmlTools.getElementByNameOrId(docObjects, fieldsElement.getAttribute("root"));
            parseFields(docElement, fieldsElement, panel);
        }

        NodeList parseValues = XmlTools.getChildElementsByTagName(parseDoc, "value");
        NodeList docValues = XmlTools.getChildElementsByTagName(doc, "value");
        for (int i = 0; i < parseValues.getLength(); i++) {
            Element targetValue = (Element) parseValues.item(i);
            Element valueElement = XmlTools.getElementByNameOrId(docValues, targetValue.getAttribute("name"));
            if (valueElement != null) {
                String name = targetValue.getTextContent();
                if (name != null && !name.isEmpty()) {
                    panel.createTextField(valueElement, name);
                } else {
                    if (panel.getParent() != null && ((EditPanel) panel.getParent()).getChildPanelCount() != 1) {
                        panel.createTextField(valueElement, null);
                    } else {
                        panel.createTextField(valueElement);
                    }
                }
            }
        }
    }

    private void parseLave() {
        List<EditPanel> panels = new ArrayList<>();

        Document carFields = FieldsDictionary.getCar();
        Element root = carFields.getDocumentElement();
        NodeList panelElements = XmlTools.getChildElementsByTagName(root, "panel");
        for (int i = 0; i < panelElements.getLength(); i++) {
            Element panelElement = (Element) panelElements.item(i);
            EditPanel editPanel = new EditPanel(panelElement.getAttribute("name"));
            parseFields(doc.getDefaultModules(), panelElement, editPanel);
            if (editPanel.getComponents().length != 0) {
                panels.add(editPanel);
            }
        }

        this.panels = panels;
        
    }

    private void parseArve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void parseSeve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
