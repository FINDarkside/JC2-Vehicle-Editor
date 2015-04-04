package gui.editpanel;

import javax.swing.InputVerifier;
import javax.swing.JTextField;
import logic.DataType;
import org.w3c.dom.Element;

/**
 *
 * @author FINDarkside
 */
public class Field extends JTextField {


    private Element element;
    private String value;

    public Field(Element e) {
        this.element = e;
    }

    public Element getElement() {
        return element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String s) {
        value = s;
    }

    public void commitEdit() {
        this.getInputVerifier().verify(this);
    }

    @Override
    public void setText(String s) {
        super.setText(s);
        value = s;
    }

}
