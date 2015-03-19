package jtools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                result.add(str);
            }
        }
        return result;
    }

    private static File changeFileExtension(File f, String ext) {
        String name = f.getAbsolutePath();
        int i = name.lastIndexOf(".");
        name = name.substring(0, i) + "." + ext;
        f = new File(name);
        return f;
    }

    /**
     * renameFile
     *
     * Renames file
     *
     * @param file File to rename
     * @param name New name
     * @return renamed file
     * @throws java.io.IOException
     */
    public static File renameFile(File file, String name) throws IOException {
        File newFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\') + 1) + name);
        if (newFile.exists()) {
            deleteFolder(newFile);
        }
        if (!file.renameTo(newFile)) {
         throw new IOException("Renaming " + file.getName() + " to " + newFile.getName() + " failed.");
         }

        Path dir = file.toPath().getParent();

        return newFile;
    }

    public static File moveToFolder(File f, File folder) throws IOException {

        File newFile = new File(folder.getAbsolutePath() + "\\" + f.getName());
        if (newFile.exists()) {
            deleteFolder(newFile);
        }

        Files.move(f.toPath(), newFile.toPath(), ATOMIC_MOVE);

        return newFile;
    }

    public static void overWrite(File f, List<String> file) throws IOException {

        if (!f.exists()) {
            f.createNewFile();
        }

        try (PrintWriter writer = new PrintWriter(f, "UTF-8")) {
            for (String s : file) {
                writer.println(s);
            }
        }
    }

    public static List<File> listAllFiles(File folder) {
        List<File> results = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                results.addAll(listAllFiles(file));
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

    public static File copyFile(File file, File folder) throws IOException {

        File newFile = new File(folder.getAbsolutePath() + "\\" + file.getName());

        if (newFile.exists()) {
            deleteFolder(newFile);
        }

        Files.copy(file.toPath(), newFile.toPath(), REPLACE_EXISTING);
        return newFile;
    }

}
