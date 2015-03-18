package logic;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import jtools.XmlTools;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class XmlDocument {

    private Document doc;
    private Element defaultModules;

    public XmlDocument(File f) throws ParserConfigurationException, SAXException, IOException {
        doc = XmlTools.readXml(f);

        defaultModules = XmlTools.getElementByAttribute(doc.getElementsByTagName("object"), "name", "_default_modules");

    }

    public Document getDocument() {
        return doc;
    }

    public Element getDefaultModules() {
        return defaultModules;
    }

}
