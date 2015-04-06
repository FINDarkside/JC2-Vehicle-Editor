package jtools;

import java.awt.Component;
import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesse
 */
public class DialogTools {

    public static boolean confirm(Component parent, String text, String title) {
        return JOptionPane.showConfirmDialog(parent, text, title, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static File chooseFile(JFrame parent, String ext, String startPath) {

        FileDialog fc = new FileDialog(parent, "Select file", FileDialog.LOAD);

        if (!ext.isEmpty()) {
            fc.setFile("*." + ext);
        }
        fc.setDirectory(startPath);
        fc.setVisible(true);

        String directory = fc.getDirectory();
        String name = fc.getFile();
        File file = (name != null) ? new File(directory + "\\" + name) : null;

        return file;
    }

    public static File chooseSaveLocation(JFrame parent, String fileName, String startPath) {

        FileDialog fc = new FileDialog(parent, "Select save location", FileDialog.SAVE);

        if (!fileName.isEmpty()) {
            fc.setFile(startPath + "\\" + fileName);
        }
        fc.setDirectory(startPath);
        fc.setVisible(true);

        String directory = fc.getDirectory();
        String name = fc.getFile();
        File file = (name != null) ? new File(directory + "\\" + name) : null;

        return file;
    }

    public static String choosePath(String startPath) {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Choose directory");
        fc.setCurrentDirectory(new File(startPath.substring(0, startPath.lastIndexOf('\\'))));

        File file = null;
        do {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return null;
            }

            file = fc.getSelectedFile();
            if (!file.isDirectory()) {
                JOptionPane.showMessageDialog(null, "Select a folder");
            }
        } while (!file.isDirectory());

        return file.getAbsolutePath();
    }
}
