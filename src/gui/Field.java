package gui;

import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import logic.DataType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author FINDarkside
 */
public class Field extends JFormattedTextField {

    DataType dataType;
    Element element;


    public Field(Element e, DataType dt) {
        this.element = e;
    }

    public Element getElement() {
        return element;
    }

    public DataType getDataType() {
        return dataType;
    }
}
