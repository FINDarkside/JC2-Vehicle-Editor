package gui.editpanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author FINDarkside
 */
public class CarGlobalModule extends EditPanel {

    private List<String> text;

    public CarGlobalModule(List<String> text) {
        this.text = text;

        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));
        createTextField(text.get(4));

    }

    @Override
    public List<String> getXml() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
