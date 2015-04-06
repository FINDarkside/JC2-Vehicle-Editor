package gui.filetree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import logic.Logic;

/**
 *
 * @author Jesse
 */
public class FileTreeMouseAdapter extends MouseAdapter {

    private JTree tree;
    private Logic logic;
    FileTreePopupMenu popupMenu;

    FileTreeNode lastClicked;

    public FileTreeMouseAdapter(JTree tree, Logic logic) {
        this.tree = tree;
        this.logic = logic;
        popupMenu = new FileTreePopupMenu(tree, logic);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
  
        int selRow = tree.getClosestRowForLocation(e.getX(), e.getY());

        if (selRow == -1) {
            return;
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int selRow = tree.getClosestRowForLocation(e.getX(), e.getY());
        if (selRow == -1) {
            return;
        }
        tree.setSelectionRow(selRow);
        FileTreeNode node = (FileTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (node.isLeaf()) {
                if (logic.fileOpened(node.getFile())) {
                    logic.loadFile(node.getFile());
                } else if (e.getClickCount() >= 2 && lastClicked == node) {
                    logic.loadFile(node.getFile());
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            popupMenu.show(tree, e.getX(), e.getY());
        }

        lastClicked = node;
    }
}
