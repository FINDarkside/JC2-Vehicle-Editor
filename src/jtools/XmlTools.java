package jtools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logic.NodeArrayList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class XmlTools {

    /**
     *
     * @param nodes
     * @param attributeName
     * @param attributeValue
     * @return Element that contains given attribute or null if no such element
     * was found
     */
    public static Element getElementByAttribute(NodeList nodes, String attributeName, String attributeValue) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.hasAttributes()) {
                continue;
            }
            Element e = (Element) n;
            if (e.getAttribute(attributeName).equals(attributeValue)) {
                return e;
            }
        }
        return null;
    }

    public static Element getElementByNameOrId(NodeList nodes, String nameOrID) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.hasAttributes()) {
                continue;
            }
            Element e = (Element) n;
            if (e.getAttribute("name").equals(nameOrID)) {
                return e;
            }
            if (e.getAttribute("id").equals(nameOrID)) {
                return e;
            }
        }
        return null;
    }

    public static NodeList getChildElementsByTagName(Element e, String tagName) {
        NodeArrayList result = new NodeArrayList();
        NodeList nodes = e.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeName().equals(tagName)) {
                result.add(n);
            }
        }
        return result;
    }

    public static Document readXml(File f) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return documentBuilder.parse(f);
    }
    
    /**
     * Saves document to given location
     *
     * @param doc Document to save
     * @param location Where xml file will be saved
     */
    public static void saveDocument(Document doc, File location) throws TransformerConfigurationException, TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(location);
        Source input = new DOMSource(doc);

        transformer.transform(input, output);
    }
}
