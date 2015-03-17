package gui.filetree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    private JTree tree;
    private Logic logic;

    private JMenuItem newVehicle = new JMenuItem("New vehicle", Icons.get("add"));
    private JMenuItem edit = new JMenuItem("Edit", Icons.get("edit"));
    private JMenuItem save = new JMenuItem("Save", Icons.get("save"));
    private JMenuItem saveAll = new JMenuItem("Save all", Icons.get("save"));
    private JMenuItem saveClose = new JMenuItem("Save & close");
    private JMenuItem saveCloseAll = new JMenuItem("Save & close all");
    private JMenuItem close = new JMenuItem("Close", Icons.get("close"));
    private JMenuItem closeAll = new JMenuItem("Close all", Icons.get("close"));
    //private JMenuItem delete = new JMenuItem("Delete");

    private File file;

    public FileTreePopupMenu(JTree tree, Logic logic) {
        super();
        this.tree = tree;
        this.logic = logic;

        newVehicle.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.loadFile(file);
            }
        });

        edit.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.loadFile(file);
            }
        });

        save.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.saveProject(file);
            }
        });

        saveAll.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.saveAllProjects();
            }
        });

        saveClose.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.saveProject(file);
                logic.closeProject(file);
            }
        });

        saveCloseAll.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.saveAllProjects();
                logic.closeAllProjects();
            }
        });

        close.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.closeProject(file);
            }
        });

        closeAll.addActionListener(new PopupItemListener(this) {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.closeAllProjects();
            }
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
