package gui.filetree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import logic.Logic;

/**
 *
 * @author Jesse
 */
public abstract class PopupItemListener implements ActionListener {

    FileTreePopupMenu popupMenu;

    public PopupItemListener(FileTreePopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
