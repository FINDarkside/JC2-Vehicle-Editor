package jtools;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static java.nio.file.StandardCopyOption.*;

/**
 *
 * @author FINDarkside
 */
public class FileTools {

    private static final String currentPath = Paths.get("").toAbsolutePath().toString();
    private static final String dictionaryPath = currentPath + "/Files/Dictionary";

    public static ArrayList<String> readFile(File file) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        String str;
        while ((str = reader.readLine()) != null) {
            result.add(str);
        }
        reader.close();
        return result;

    }

    public static File changeFileExtension(File f, String ext) {

        String name = f.getAbsolutePath();
        int i = name.lastIndexOf(".");
        name = name.substring(0, i) + "." + ext;
        f = new File(name);
        return f;
    }

    public static File renameFile(File f, String name) throws IOException {
        File newFile = new File(f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf('\\') + 1) + name);
        if (newFile.exists()) {
            deleteFolder(newFile);
        }
        if (!f.renameTo(newFile)) {
            throw new IOException("Renaming " + f.getName() + " to " + newFile.getName() + " failed.");
        }
        return newFile;
    }

    public static File moveFile(File f, File destination) throws IOException {

        File newFile = new File(destination.getAbsolutePath() + "\\" + f.getName());
        if (newFile.exists()) {
            deleteFolder(newFile);
        }

        Files.move(f.toPath(), newFile.toPath(), REPLACE_EXISTING);

        return newFile;
    }

    public static void overWrite(File f, List file) throws IOException {

        if (!f.exists()) {
            f.createNewFile();
        }

        PrintWriter writer = new PrintWriter(f, "UTF-8");
        for (int i = 0; i < file.size(); i++) {
            writer.println(file.get(i));
        }
        writer.close();
    }

    public static File convert(File file) throws IOException, InterruptedException {

        File binConverter = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.BinConvert.exe");

        if (!binConverter.exists()) {
            throw new FileNotFoundException(binConverter.getAbsolutePath() + " does not exist");
        }

        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " does not exist");
        }

        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1, name.length());

        Process p;
        p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + binConverter.getAbsolutePath() + "\" \"" + file.getAbsolutePath() + "\"\"");
        p.waitFor();

        File file2 = changeFileExtension(file, extension.equals("xml") ? "bin" : "xml");

        int exVal = p.exitValue();
        if (exVal != 0) {
            throw new RuntimeException(binConverter.getName() + " returned " + exVal);
        }

        return file2;
    }

    public static File chooseFile(String ext, String startPath) {

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

    public static String choosePath(String startPath) {

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
                JOptionPane.showMessageDialog(null, "Select a folder");
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

        if (folder.isFile()) {
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

    public static File smallUnpack(File file) throws FileNotFoundException, IOException, InterruptedException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " does not exist");
        }
        File smallUnpack = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallUnpack.exe");
        if (!smallUnpack.exists()) {
            throw new FileNotFoundException(smallUnpack.getAbsolutePath() + " does not exist");
        }

        Process p;
        p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + smallUnpack.getAbsolutePath() + "\" \"" + file.getAbsolutePath() + "\"\"");
        p.waitFor();

        File result = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\') + 1) + file.getName().substring(0, file.getName().lastIndexOf('.')) + "_unpack");
        result = renameFile(result, result.getName().substring(0, result.getName().lastIndexOf('_')));

        return result;
    }

    public static File smallPack(File file) throws IOException, InterruptedException {
        File smallPack = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe");

        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " does not exist");
        }
        if (smallPack.exists()) {
            throw new FileNotFoundException(smallPack.getAbsolutePath() + " does not exist");
        }

        Process p;
        p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe\" \"" + file.getAbsolutePath() + "\"\"");
        p.waitFor();

        File result = new File(file.getAbsolutePath() + ".sarc");
        return renameFile(result, changeFileExtension(result, "eez").getName());
    }

}
