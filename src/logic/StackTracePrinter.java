package logic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import jtools.FileTools;

/**
 *
 * @author FINDarkside
 */
public class StackTracePrinter {

    private static final String location = Settings.currentPath + "\\Stacktraces";

    public static void handle(Throwable t) {
        t.printStackTrace(System.err);
        
        Object[] options = {"Save stacktrace & copy to clipboard", "Continue"};
        int n = JOptionPane.showOptionDialog(null,
                t.getClass().getSimpleName() + ": " + t.getMessage() + "\nIf you don't know what caused this exception, you can save the stacktrace and post it in jc2mods forums",
                t.getClass().getSimpleName(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            t.printStackTrace(printWriter);
            String stackTrace = stringWriter.toString();
            stackTrace = "[spoiler][code]" + System.lineSeparator() + stackTrace + "[/code][/spoiler]";

            StringSelection stringSelection = new StringSelection(stackTrace);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            Date date = new Date();

            String d = dateFormat.format(date);
            int i = 0;

            File savedFile;
            do {
                String fileName = d + " " + t.getClass().getSimpleName();
                savedFile = new File(location + "\\" + fileName + (i != 0 ? ("(" + i + ")") : "") + ".txt");
                i++;
            } while (savedFile.exists());

            try {
                FileTools.overWrite(savedFile, Arrays.asList(stackTrace));
            } catch (IOException ex) {
                handle(ex);
            }

        }

    }

}
