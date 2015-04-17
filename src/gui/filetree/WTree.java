/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.filetree;

import gui.MainForm;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JTree;

/**
 *
 * @author FINDarkside
 */
public class WTree extends JTree {

    private final static Color selectedColor = new Color(203, 228, 244);
    private final static Color selectedBorderColor = new Color(28, 156, 237);
    MainForm form;

    public WTree() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {

        // paint background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // paint selected node's background and border
        int fromRow = getRowForPath(getSelectionPath());

        if (fromRow != -1) {
            int toRow = fromRow + 1;
            Rectangle fromBounds = getRowBounds(fromRow);
            Rectangle toBounds = getRowBounds(toRow - 1);
            if (fromBounds != null && toBounds != null) {
                g.setColor(selectedColor);
                g.fillRect(1, fromBounds.y, getWidth() - 3, toBounds.height - 1);
                g.setColor(selectedBorderColor);
                g.drawRect(1, fromBounds.y, getWidth() - 3, toBounds.height - 1);
            }
        }

        // perform operation of superclass
        setOpaque(false); // trick not to paint background
        super.paintComponent(g);
        setOpaque(false);
    }

}
