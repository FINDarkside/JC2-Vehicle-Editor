package gui.filetree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import logic.Logic;

/**
 *
 * @author Jesse
 */
public class FileTreeMouseAdapter extends MouseAdapter {

    private JTree tree;
    private Logic logic;

    public FileTreeMouseAdapter(JTree tree, Logic logic) {
        this.tree = tree;
        this.logic = logic;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int selRow = tree.getRowForLocation(e.getX(), e.getY());
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
                } else if (e.getClickCount() == 2) {
                    logic.loadFile(node.getFile());
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            new FileTreePopupMenu(tree, logic).show(tree, e.getX(), e.getY());
        }
    }
}
