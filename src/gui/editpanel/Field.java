package gui.editpanel;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import logic.StackTracePrinter;
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
        
        
        //Adding custom mouselisteners to fix textfield caret placement behaviour
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Rectangle rect = modelToView(0);//for y value
                    int loc = viewToModel(new Point(e.getX(), rect.y));
                    setSelectionStart(loc);
                    setSelectionEnd(loc);
                } catch (BadLocationException ex) {
                    StackTracePrinter.handle(ex);
                }
            }
        });
        
        //Drag selection won't work if default mouseMotionListener is not deleted
        for(MouseMotionListener ml : getMouseMotionListeners()){
            removeMouseMotionListener(ml);
        }

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    Rectangle rect = modelToView(0);//for y value
                    int loc = viewToModel(new Point(e.getX(), rect.y));
                    if (getSelectionStart() < loc) {
                        setSelectionStart(getSelectionStart());
                        setSelectionEnd(loc);
                    } else {
                        setSelectionStart(loc);
                        setSelectionEnd(getSelectionEnd());
                    }
                } catch (BadLocationException ex) {
                    StackTracePrinter.handle(ex);
                }
            }
        });

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
