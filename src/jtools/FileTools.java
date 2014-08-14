

package jtools;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static java.nio.file.StandardCopyOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FINDarkside
 */
public class FileTools {

    private static final String currentPath = Paths.get("").toAbsolutePath().toString();
    private static final String dictionaryPath = currentPath + "/Files/Dictionary";

    private static void alertError(String str) {
        JOptionPane.showMessageDialog(null,
                "Error in class FileTools | method " + str,
                "ERROR",
                JOptionPane.ERROR_MESSAGE);

    }

    public static ArrayList<String> readFile(File file) {
        try {
            ArrayList<String> result = new ArrayList<>();
            BufferedReader reader;

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String str;
            while ((str = reader.readLine()) != null) {

                result.add(str);
            }
            reader.close();
            return result;
        } catch (IOException ex) {
            alertError("readfile\n" + ex.getMessage());
            return null;
        }
    }

    public static File changeFileExtension(File f, String ext) {

        String name = f.getAbsolutePath();
        int i = name.lastIndexOf(".");
        name = name.substring(0, i) + "." + ext;
        f = new File(name);
        return f;
    }

    public static File renameFile(File f, String name) {
        File newFile = new File(f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf('\\') + 1) + name);
        if(newFile.exists()){
            deleteFolder(newFile);
        }

        
        if (!f.renameTo(newFile)) {
            alertError("renameFile\n Can't rename " + f.getAbsolutePath());
            return null;
        }
        return newFile;
    }

    public static File moveFile(File f, File destination) {

        File newFile = new File(destination.getAbsolutePath() + "\\" + f.getName());
        if (newFile.exists()) {
            deleteFolder(newFile);
        }
        try {
            Files.move(f.toPath(), newFile.toPath(), REPLACE_EXISTING);
        } catch (IOException ex) {
            alertError("moveFile\n" + ex.getMessage());
        }
        return newFile;
    }

    public static boolean overWrite(File f, List file) {

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                alertError("overWrite\n" + ex.getMessage());
                return false;
            }
        }
        try {
            PrintWriter writer = new PrintWriter(f, "UTF-8");
            for (int i = 0; i < file.size(); i++) {
                writer.println(file.get(i));
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            alertError("overWrite\n" + e.getMessage() + "\nTrying to overwrite: " + f.getAbsolutePath());
            return false;
        }
        return true;
    }

    public static File convert(File file) {

        File binConverter = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.BinConvert.exe");

        if (!binConverter.exists()) {
            alertError("convert\nGibbedsTools is missing, it should be in\n" + currentPath + "\\GibbedsTools\\Gibbed.Avalanche.BinConvert.exe");
            return null;
        }

        if (!file.exists()) {
            alertError("convert\nFile does not exist\n" + file.getAbsolutePath());
        }

        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1, name.length());

        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + binConverter.getAbsolutePath() + "\" \"" + file.getAbsolutePath() + "\"\"");
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            alertError("convert\n" + ex.getMessage());
            return null;
        }
            
        
        File file2 = changeFileExtension(file, extension.equals("xml") ? "bin" : "xml");

        if (p.exitValue() != 0) {
            alertError("convert\nBinConverter crashed\nExit value: " + p.exitValue());
            return null;
        }

        return file2;
    }

    public static File getFile(String ext, String startPath) {

        java.awt.Frame f = null;
        FileDialog fc = new FileDialog(f, "Select file");
        fc.setFile("*." + ext);

        fc.setDirectory(startPath);

        fc.setVisible(true);

        String directory = fc.getDirectory();
        String name = fc.getFile();
        File file = (name != null) ? new File(directory + "/" + name) : null;

        return file;
    }

    public static String getPath(String startPath) {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select save location");
        fc.setCurrentDirectory(new File(startPath.substring(0, startPath.lastIndexOf("\\"))));

        File file = null;
        do {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return null;
            }

            file = fc.getSelectedFile();
            if (!file.isDirectory()) {
                alertError("getPath\nSelect a folder");
            }
            if (!file.canWrite()) {
                alertError("getPath\nCan't write to direcory " + file.getAbsolutePath() + "select another folder or run this program as administrator.");
            }

        } while (!file.isDirectory());

        return file.getAbsolutePath();
    }

    public static List<File> listFilesInFolder(File folder) {
        List<File> results = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                results.addAll(listFilesInFolder(file));
            } else {
                results.add(file);
            }
        }

        return results;
    }

    public static void deleteFolder(File folder) {
      
        if(folder.isFile()){
            folder.delete();
            return;
        }

        for (File file : folder.listFiles()) {  
            if (file.isDirectory()) {
                deleteFolder(file);
            } else {
                file.delete();
            }
        }
        folder.delete();
    }

    public static File smallUnpack(File file) {
        if (!file.exists()) {
            alertError("smallUnpack\n " + file.getAbsolutePath() + " does not exist");
            return null;
        }
        File smallUnpack = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallUnpack.exe");
        if (!smallUnpack.exists()) {
            alertError("smallUnpack\nGibbedsTools SmallUnpack is missing");
            return null;
        }

        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + smallUnpack.getAbsolutePath() +"\" \"" + file.getAbsolutePath() + "\"\"");
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            alertError("smallUnpack\n" + ex.getMessage());
            return null;
        }

        File result = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\') + 1) + file.getName().substring(0, file.getName().lastIndexOf('.')) + "_unpack");
        result = renameFile(result, result.getName().substring(0, result.getName().lastIndexOf('_')));

        return result;
    }

    public static File smallPack(File file) {
        
        if (!file.exists()) {
            alertError("smallPack\n " + file.getAbsolutePath() + " does not exist");
        }
        if (!new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe").exists()) {
            alertError("smallPack\nGibbedsTools SmallPack is missing");
        }

        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe\" \"" + file.getAbsolutePath() + "\"\"");
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            alertError("smallPack\n" + ex.getMessage());
            return null;
        }
        
        
        File result = new File(file.getAbsolutePath()+".sarc");
        return renameFile(result, changeFileExtension(result, "eez").getName());
    }

}
