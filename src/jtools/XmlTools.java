package jtools;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class XmlTools {

    private static DocumentBuilder documentBuilder = null;

    public static Element getElementByAttribute(Element e, String attr) {
        return getElementByAttribute(e.getChildNodes(), attr);
    }

    public static Element getElementByAttribute(NodeList nodes, String attr) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.hasAttributes()) {
                continue;
            }
            NamedNodeMap attrs = n.getAttributes();
            for (int j = 0; j < attrs.getLength(); j++) {
                String a = attrs.item(j).getNodeValue();
                if (a.equals(attr)) {
                    return (Element) n;
                }
            }
        }
        return null;
    }

    public static Node getNodeByAttribute(Element e, String attr) {
        NodeList nodes = e.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.hasAttributes()) {
                continue;
            }
            NamedNodeMap attrs = n.getAttributes();
            for (int j = 0; j < attrs.getLength(); j++) {
                String a = attrs.item(j).getNodeValue();
                if (a.equals(attr)) {
                    return n;
                }
            }
        }
        return null;
    }

    public static Document readXml(File f) throws SAXException, IOException, ParserConfigurationException {
        if (documentBuilder == null) {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
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
