package gui.filetree;

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
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
    
}
