package gui.editpanel;

import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import logic.DataType;
import logic.StackTracePrinter;
import net.miginfocom.swing.MigLayout;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author FINDarkside
 */
public class EditPanel extends JPanel {

    private List<Field> components = new ArrayList<>();

    protected List<String> text;

    public EditPanel(String name) {
        this.setName(name);
        this.setLayout(new MigLayout("wrap 2",
                "",
                "30"));
    }

    public void createTextField(Element e, String name) {

        JLabel label = new JLabel(name);
        this.add(label);

        DataType dataType;

        String type = e.getAttribute("type");

        switch (type) {
            case "int":
                dataType = DataType.INTEGER;
                break;
            case "float":
                dataType = DataType.FLOAT;
                break;
            case "string":
                dataType = DataType.STRING;
                break;
            case "vec":
                dataType = DataType.STRING;
                break;
            default:
                dataType = DataType.STRING;
                System.err.println("Undefined data type " + type);
        }

        Field field = new Field(e, dataType);
        field.setValue(e.getTextContent());
        this.add(field, "width 100:300, height 25, gapleft 10");
        components.add(field);
    }

    public void createTextField(Element e) {
        String name = e.getAttribute("name");
        name = name.isEmpty() ? e.getAttribute("id") : name;
        createTextField(e, name);
    }

    public void save() {
        for (Field f : components) {
            try {
                f.commitEdit();
            } catch (ParseException ex) {
                StackTracePrinter.handle(ex);
            }
            f.getElement().setTextContent(f.getValue().toString());
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
