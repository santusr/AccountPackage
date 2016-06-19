/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Roshan
 */
public class PrintLog {

    public static void out(Throwable er) {

        String date = new Date().toString();
        String erp = getCustomStackTrace(er) + "</body></html>";;
        File directory = new File(".");

        String[] dta = date.split(":");


        try {
            clear();
            String file = directory.getCanonicalPath() + "\\log\\" + dta[0] + "-" + dta[1] + "-" + dta[2] + ".html";

            File fl = new File(directory.getCanonicalPath() + "\\log\\");
            fl.mkdir();

            FileWriter outFile = new FileWriter(file);
            PrintWriter outs = new PrintWriter(outFile);
            outs.println(erp);
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getCustomStackTrace(Throwable aThrowable) {
        //add the class name and any message passed to constructor

        String date = new Date().toString();


        final StringBuilder result = new StringBuilder("<html>"
                + "<head>"
                + "<title>Rabid logs</title>"
                + "</head>"
                + "<body><h1>Rabid Account Package logs</h1>" + date + "<br><br>");
        result.append(aThrowable.toString());
        final String NEW_LINE = "<br>\n";
        result.append(NEW_LINE);

        //add each element of the stack trace
        for (StackTraceElement element : aThrowable.getStackTrace()) {
            result.append(element);
            result.append(NEW_LINE);
        }
        return result.toString();
    }

    public static void clear() {
        try {
            File directory = new File(".");
            File fl = new File(directory.getCanonicalPath() + "\\log\\");


            int d = fl.listFiles().length;


            if (d > 150) {
                deleteDir(fl);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }


        return dir.delete();
    }
}
