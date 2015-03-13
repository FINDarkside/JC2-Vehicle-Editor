package gui.editpanel;

import java.awt.Dimension;
import java.awt.Rectangle;
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
public class EditPanel extends JPanel implements Scrollable {

    private List<Field> fields = new ArrayList<>();

    protected List<String> text;

    public EditPanel(String name) {
        this.setName(name);
        this.setLayout(new MigLayout("wrap 2", "", "30"));

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
        this.add(field, "width 50:500, height 25, gapleft 10");
        fields.add(field);
    }

    public void createTextField(Element e) {
        String name = e.getAttribute("name");
        name = name.isEmpty() ? e.getAttribute("id") : name;
        createTextField(e, name);
    }

    public void save() {
        for (Field f : fields) {
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

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return super.getPreferredSize(); //tell the JScrollPane that we want to be our 'preferredSize' - but later, we'll say that vertically, it should scroll.
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;//set to 16 because that's what you had in your code.
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;//set to 16 because that's what you had set in your code.
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return this.getMinimumSize().width < this.getWidth();
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false; //we don't want to track the height, because we want to scroll vertically.
    }

}
