package jtools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import logic.Settings;

/**
 *
 * @author Jesse
 */
public class GibbedsTools {

    private final static String currentPath = Settings.currentPath;

    private static File changeFileExtension(File f, String ext) {
        String name = f.getAbsolutePath();
        int i = name.lastIndexOf(".");
        name = name.substring(0, i) + "." + ext;
        f = new File(name);
        return f;
    }

    public static File smallUnpack(File file) throws InterruptedException, IOException {
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
        int exVal = p.exitValue();
        if (exVal != 0) {
            if (exVal == -2146232576) {
                throw new RuntimeException("GibbedsTools requires .NET framework");
            }
            throw new RuntimeException("SmallUnpack quit with exit value " + exVal);
        }

        File result = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\') + 1) + file.getName().substring(0, file.getName().lastIndexOf('.')) + "_unpack");
        result = FileTools.renameFile(result, result.getName().substring(0, result.getName().lastIndexOf('_')));

        return result;
    }

    public static File smallPack(File file) throws IOException, InterruptedException {
        File smallPack = new File(currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe");

        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " does not exist");
        }
        if (!smallPack.exists()) {
            throw new FileNotFoundException(smallPack.getAbsolutePath() + " does not exist");
        }

        Process p;
        p = Runtime.getRuntime().exec("cmd.exe /c " + "\"\"" + currentPath + "\\Files\\GibbedsTools\\Gibbed.Avalanche.SmallPack.exe\" \"" + file.getAbsolutePath() + "\"\"");
        p.waitFor();
        int exVal = p.exitValue();
        if (exVal != 0) {
            if (exVal == -2146232576) {
                throw new RuntimeException("GibbedsTools requires .NET framework");
            }
            throw new RuntimeException("SmallUnpack quit with exit value " + exVal);
        }

        File result = new File(file.getAbsolutePath() + ".sarc");
        return FileTools.renameFile(result, changeFileExtension(result, "eez").getName());
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
        if (!file2.exists()) {
            file2 = changeFileExtension(file2, "mvdoll");
        }
        if (!file2.exists()) {
            file2 = changeFileExtension(file2, "vdoll");
        }

        int exVal = p.exitValue();
        if (exVal != 0) {
            if (exVal == -2146232576) {
                throw new RuntimeException("GibbedsTools requires .NET framework");
            }
            throw new RuntimeException("SmallUnpack quit with exit value " + exVal);
        }

        return file2;
    }
}
