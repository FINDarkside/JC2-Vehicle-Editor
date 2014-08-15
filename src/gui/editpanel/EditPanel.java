package gui.editpanel;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author FINDarkside
 */
public abstract class EditPanel extends JPanel{

    private List<JComponent> components = new ArrayList<>();

    protected final static int topMargin = 5;
    protected final static int leftMargin = 5;
    protected final static int verticalSpace = 10; //Vertical space between elements
    protected final static int horizontalSpace = 20;
    protected final static int height = 26;

    protected int y = topMargin;
    
    public EditPanel(){
        this.setLayout(null);
    }

    protected void createTextField(String s) {

        String key = s.substring(s.indexOf('\"') + 1, s.indexOf('\"', s.indexOf('\"') + 1));
        String value = s.substring(s.indexOf('>') + 1, s.lastIndexOf('<'));


        JLabel label = new JLabel(key);

        label.setBounds(leftMargin, y, 200, height);
        this.add(label);

        JTextField field = new JTextField(value);
        field.setBounds(leftMargin + label.getWidth() + horizontalSpace, y, 200, height);

        this.add(field);
        
        y += field.getHeight() + verticalSpace;
        
        this.setBounds(0, 0, 0, y);

    }
    
    public List<String> getXml(){
        return null;
    }
}
