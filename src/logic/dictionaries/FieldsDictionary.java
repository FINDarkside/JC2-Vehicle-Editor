package logic.dictionaries;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import jtools.XmlTools;
import logic.Settings;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author FINDarkside
 */
public class FieldsDictionary {

    private static Document car;
    private static Document bike;
    private static Document helicopter;
    private static Document boat;
    private static Document plane;

    static {
        try {
            car = XmlTools.readXml(new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Car.xml"));
            bike = XmlTools.readXml(new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Bike.xml"));
            helicopter = XmlTools.readXml(new File(Settings.currentPath + "\\Files\\Dictionary\\Fields\\Helicopter.xml"));
            boat = XmlTools.readXml(new File(Settings.currentPath+"\\Files\\Dictionary\\Fields\\Boat.xml"));
            plane = XmlTools.readXml(new File(Settings.currentPath+"\\Files\\Dictionary\\Fields\\Plane.xml"));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getCar() {
        return car;
    }

    public static Document getBike() {
        return bike;
    }

    public static Document getHelicopter() {
        return helicopter;
    }

    public static Document getPlane() {
        return plane;
    }

    public static Document getBoat() {
        return boat;
    }

}
