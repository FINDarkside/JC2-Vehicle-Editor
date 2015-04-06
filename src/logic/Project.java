package logic;

import gui.editpanel.EditPanel;
import jtools.FileTools;
import java.io.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import jtools.GibbedsTools;
import jtools.XmlTools;
import logic.dictionaries.ParseDocuments;
import logic.dictionaries.Vehicles;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class Project {

    private File eez;
    private File unpacked;
    private File mvdoll;
    private File mvdollXml;

    private XmlDocument doc;
    private List<EditPanel> panels = new ArrayList<>();

    public Project(File file) throws InterruptedException, IOException, ParserConfigurationException, SAXException {

        if (file.isFile()) {
            eez = file;
            if (isDefaultVehicle()) {
                unpacked = GibbedsTools.smallUnpack(file, new File(Settings.currentPath + "\\tmp"));
            } else {
                unpacked = GibbedsTools.smallUnpack(file);
            }
        } else {
            unpacked = file;
        }

        for (File f : unpacked.listFiles()) {
            if (f.getName().endsWith(".mvdoll") || f.getName().endsWith(".vdoll")) {
                this.mvdoll = f;
                break;
            }
        }

        this.mvdollXml = GibbedsTools.convert(mvdoll);

        doc = new XmlDocument(mvdollXml);

        parse(ParseDocuments.get(Vehicles.getVehicleType(file)));
    }

    public File getFile() {
        return unpacked;
    }

    public File getEez() {
        return eez;
    }

    public boolean isDefaultVehicle() {
        return eez.getAbsolutePath().startsWith(Settings.currentPath + "\\Files\\Default vehicles\\");
    }

    public void save() throws TransformerException, IOException, InterruptedException {
        System.out.println("Saving " + unpacked.getAbsolutePath());
        for (EditPanel ep : panels) {
            ep.applyChanges();
        }

        XmlTools.saveDocument(doc.getDocument(), mvdollXml);

        GibbedsTools.convert(mvdollXml);
        GibbedsTools.smallPack(unpacked);
    }

    public void save(File location) throws TransformerException, IOException, InterruptedException {
        System.out.println("Saving " + unpacked.getAbsolutePath() + " to " + location.getAbsolutePath());
        long start = System.currentTimeMillis();
        for (EditPanel panel : panels) {
            panel.applyChanges();
        }
        long stop = System.currentTimeMillis();
        System.out.println(((double) stop - start) / 1000);

        unpacked = FileTools.moveToFolder(unpacked, location.getParentFile());
        mvdollXml = new File(unpacked.getPath() + "\\" + mvdollXml.getName());
        XmlTools.saveDocument(doc.getDocument(), mvdollXml);
        mvdoll = GibbedsTools.convert(mvdollXml);
        eez = GibbedsTools.smallPack(unpacked);

    }

    public void close() {
        FileTools.deleteFolder(this.unpacked);
    }

    public EditPanel getPanel(int index) {
        return panels.get(index);
    }

    public List<EditPanel> getPanels() {
        return panels;
    }

    private void parse(Document parseXml) {

        Element root = parseXml.getDocumentElement();
        NodeList panelElements = XmlTools.getChildElementsByTagName(root, "panel");
        for (int i = 0; i < panelElements.getLength(); i++) {
            Element panelElement = (Element) panelElements.item(i);
            EditPanel editPanel = new EditPanel(panelElement.getAttribute("name"));
            parseFields(doc.getDefaultModules(), panelElement, editPanel);
            if (editPanel.getComponents().length != 0) {
                panels.add(editPanel);
            }
        }

    }

    private void parseFields(Element doc, Element parseDoc, EditPanel panel) {
        if (doc == null) {
            return;
        }

        NodeList panelList = XmlTools.getChildElementsByTagName(parseDoc, "panel");
        for (int i = 0; i < panelList.getLength(); i++) {
            Element panelElement = (Element) panelList.item(i);
            EditPanel childPanel = new EditPanel(panelElement.getAttribute("name"));
            parseFields(doc, panelElement, childPanel);
            if (!childPanel.isEmpty()) {
                panel.addPanel(childPanel);
            }
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
                    if (panel.getParent() != null && ((EditPanel) panel.getParent()).getChildPanelCount() != 1) { //Not first child panel = no label
                        panel.createTextField(valueElement, null);
                    } else {
                        panel.createTextField(valueElement);
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Project p = (Project) o;
        return p.eez.equals(eez);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.eez);
        hash = 23 * hash + Objects.hashCode(this.unpacked);
        return hash;
    }

}
