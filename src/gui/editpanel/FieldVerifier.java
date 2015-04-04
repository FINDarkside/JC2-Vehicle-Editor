package gui.editpanel;

import javax.swing.JComponent;

/**
 *
 * @author Jesse
 */
public abstract class FieldVerifier extends javax.swing.InputVerifier {
    
    @Override
    public boolean verify(JComponent input) {
        return verify(((Field) input).getText());
    }
    
    public abstract boolean verify(String input);
    
    @Override
    public boolean shouldYieldFocus(JComponent input) {
        Field field = (Field) input;
        if (!verify(input)) {
            field.setText(field.getValue());
        } else {
            field.setText(field.getText());
        }
        return true;
    }
}
