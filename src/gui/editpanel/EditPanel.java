package gui.editpanel;

import java.awt.Color;
import java.awt.Component;
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
    private List<EditPanel> childPanels = new ArrayList<>();

    public EditPanel(String name) {
        this.setBackground(Color.WHITE);
        this.setName(name);
        this.setLayout(new MigLayout("", "", "30"));

    }

    public void createTextField(Element e, String name) {
        if (name != null) {
            JLabel label = new JLabel(name);
            this.add(label);
        }

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
        this.add(field, "width 100:200, height 25:25, gapleft 10, wrap, gaptop 5");
        fields.add(field);
    }

    public void createTextField(Element e) {
        String name = e.getAttribute("name");
        name = name.isEmpty() ? e.getAttribute("id") : name;
        createTextField(e, name);
    }

    public void addPanel(EditPanel p) {

        add(new JLabel(p.getName()), "cell " + childPanels.size() * 2 + " 0, align center");
        if (!childPanels.isEmpty()) {
            JSeparator s = new JSeparator(JSeparator.VERTICAL);
            add(s, "cell " + (childPanels.size() * 2 - 1) + " 1, h 100%");
        }
        add(p, "cell " + childPanels.size() * 2 + " 1");
        childPanels.add(p);
    }

    public int getChildPanelCount() {
        return childPanels.size();
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
        for (EditPanel ep : childPanels) {
            ep.save();
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        Component parent = getParent();

        if (parent == null) {
            return preferredSize;
        }

        Dimension parentSize = parent.getSize();
        Dimension minimumSize = getMinimumSize();

        int width = Math.min(preferredSize.width, parentSize.width);
        width = Math.max(width, minimumSize.width);

        int height = Math.max(super.getPreferredSize().height, parent.getHeight());

        return new Dimension(width, height);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return super.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return this.getMinimumSize().width < this.getWidth();
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}
