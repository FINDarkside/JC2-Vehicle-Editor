package gui.filetree;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import logic.dictionaries.Icons;
import logic.dictionaries.VehicleType;
import logic.dictionaries.Vehicles;

/**
 *
 * @author Jesse
 */
public class FileTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        if (leaf) {
            FileTreeNode node = (FileTreeNode) value;
            //setToolTipText(node.getFile().getAbsolutePath());

            VehicleType type = Vehicles.getVehicleType(node.getFile());
            setLeafIcon(Icons.get(type.toString().toLowerCase()));
        }
        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        c.setBackground(new Color(0, 0, 0));
        return c;
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
        return null;
    }

    @Override
    public Color getBackgroundSelectionColor() {
        return null;
    }

    @Override
    public Color getBackground() {
        return null;
    }

    @Override
    public Color getTextSelectionColor() {
        return new Color(0, 0, 0);
    }

}
