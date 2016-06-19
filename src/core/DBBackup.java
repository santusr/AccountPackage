/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.HeadlessException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author RoWi
 */
public class DBBackup {

    public static boolean backupDataWithOutDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hhmmss");
            Date date = new Date();
            String filepath = "backup - " + database + "- (" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (!password.equals("")) {
//only backup the data not included create database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                JOptionPane.showMessageDialog(null, "Backup created successfully...." + database + " in " + host + ":" + port, "Successful...", JOptionPane.INFORMATION_MESSAGE);
            } else {
                status = false;
                JOptionPane.showMessageDialog(null, "Could not create the backup... " + database + " in " + host + ":" + port, "Error...", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Could not create the backup... " + database + " in " + host + ":" + port, "Error...", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
            //log.error(ioe, ioe.getCause());
        } catch (HeadlessException | InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Could not create the backup... " + database + " in " + host + ":" + port, "Error...", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            //log.error(e, e.getCause());
        }
        return status;
    }
}
