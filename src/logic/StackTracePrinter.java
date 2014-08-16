package logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import jtools.FileTools;

/**
 *
 * @author FINDarkside
 */
public class StackTracePrinter {

    private static final String location = Paths.get("").toAbsolutePath().toString() + "\\Stacktraces";

    public static void handle(Exception e) {
        Object[] options = {"Save stacktrace & copy to clipboard", "Continue"};
        int n = JOptionPane.showOptionDialog(null,
                e.getClass().getSimpleName() + ": " + e.getMessage() + "\nIf you don't know what caused this exception, you can save the stacktrace and post it in jc2mods forums",
                e.getClass().getSimpleName(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String stackTrace = stringWriter.toString();

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            Date date = new Date();

            String d = dateFormat.format(date);
            int i = 0;

            File savedFile;
            do {
                String fileName = d + " " + e.getClass().getSimpleName();
                savedFile = new File(location + "\\" + fileName + (i != 0 ? ("(" + i + ")") : "") + ".txt");
                i++;
            } while (savedFile.exists());
            List<String> list = new ArrayList<>();
            list.add("<spoiler><code>\n"+stackTrace+"</code></spoiler>");
            
            try {
                FileTools.overWrite(savedFile, list);
            } catch (IOException ex) {
                handle(e);
            }
            

            
        }

    }

}
