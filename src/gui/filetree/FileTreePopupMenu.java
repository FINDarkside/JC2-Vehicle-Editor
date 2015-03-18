package gui.filetree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import logic.Logic;
import logic.Settings;
import logic.dictionaries.Icons;

/**
 *
 * @author Jesse
 */
public class FileTreePopupMenu extends JPopupMenu {

    private final JTree tree;
    private final Logic logic;

    private final JMenuItem newVehicle = new JMenuItem("New vehicle", Icons.get("add"));
    private final JMenuItem edit = new JMenuItem("Edit", Icons.get("edit"));
    private final JMenuItem save = new JMenuItem("Save", Icons.get("save"));
    private final JMenuItem saveAll = new JMenuItem("Save all", Icons.get("save"));
    private final JMenuItem saveClose = new JMenuItem("Save & close");
    private final JMenuItem saveCloseAll = new JMenuItem("Save & close all");
    private final JMenuItem close = new JMenuItem("Close", Icons.get("close"));
    private final JMenuItem closeAll = new JMenuItem("Close all", Icons.get("close"));
    //private JMenuItem delete = new JMenuItem("Delete");

    private File file;

    public FileTreePopupMenu(JTree tree, Logic logic) {
        super();
        this.tree = tree;
        this.logic = logic;

        newVehicle.addActionListener((ActionEvent e) -> {
            logic.loadFile(file);
        });

        edit.addActionListener((ActionEvent e) -> {
            logic.loadFile(file);
        });

        save.addActionListener((ActionEvent e) -> {
            logic.saveProject(file);
        });

        saveAll.addActionListener((ActionEvent e) -> {
            logic.saveAllProjects();
        });

        saveClose.addActionListener((ActionEvent e) -> {
            logic.saveProject(file);
            logic.closeProject(file);
        });

        saveCloseAll.addActionListener((ActionEvent e) -> {
            logic.saveAllProjects();
            logic.closeAllProjects();
        });

        close.addActionListener((ActionEvent e) -> {
            logic.closeProject(file);
        });

        closeAll.addActionListener((ActionEvent e) -> {
            logic.closeAllProjects();
        });
    }

    private void setComponents(JMenuItem... items) {
        removeAll();
        for (JMenuItem i : items) {
            add(i);
        }
    }

    @Override
    public void show(Component invoker, int x, int y) {

        FileTreeNode node = (FileTreeNode) tree.getLastSelectedPathComponent();
        this.file = node.getFile();

        if (file.getName().equals(FileTreeModel.projects)) {
            setComponents(saveAll, saveCloseAll, closeAll);
        } else if (file.getAbsolutePath().startsWith(Settings.currentPath + "\\Files\\Default vehicles\\")) {
            setComponents(newVehicle);
        } else if (file.getName().equals(FileTreeModel.projects)) {
            setComponents(saveAll, saveCloseAll, closeAll);
        } else if (logic.fileOpened(file)) {
            setComponents(save, saveClose, close);
        } else if (file.isFile()) {
            setComponents(edit);
        } else {
            return;
        }

        super.show(invoker, x, y);

    }
}
