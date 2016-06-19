/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountpackage;

import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class back {

    public static void main(String[] args) {
       
//        String executeCmd;
//        String dbUser = "root";
//        String dbPass = "123";
//        String dbName = "rap";
//        executeCmd = "C:/Program Files (x86)/MySQL/MySQL Server 5.5/bin/mysqldump --host=localhost --port=3306 --user=" + dbUser + " --password=" + dbPass + " --database=" + dbName + " D:/aaa.sql";
//        try {
//            Connection con = DBConnection.getConnection();
//            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//            int processComplete = runtimeProcess.waitFor();
//            if (processComplete == 0) {
//                out.println("Backup taken successfully");
//            } else {
//                System.out.println(processComplete);
//                out.println("Could not take mysql backup");
//            }
//            con.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
      
    try {
    Runtime rt = Runtime.getRuntime();
    rt.exec("cmd.exe /c start C:\\ab.bat");
    System.exit(0);
    }
    catch(IOException ex) {
    }
        
//         try {
//
//        /*NOTE: Getting path to the Jar file being executed*/
//        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
//        CodeSource codeSource = YourImplementingClass.class.getProtectionDomain().getCodeSource();
//        File jarFile = new File(codeSource.getLocation().toURI().getPath());
//        String jarDir = jarFile.getParentFile().getPath();
//
//
//        /*NOTE: Creating Database Constraints*/
//        String dbName = "YourDBName";
//        String dbUser = "YourUserName";
//        String dbPass = "YourUserPassword";
//
//        /*NOTE: Creating Path Constraints for folder saving*/
//        /*NOTE: Here the backup folder is created for saving inside it*/
//        String folderPath = jarDir + "\\backup";
//
//        /*NOTE: Creating Folder if it does not exist*/
//        File f1 = new File(folderPath);
//        f1.mkdir();
//
//        /*NOTE: Creating Path Constraints for backup saving*/
//        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
//         String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";
//
//        /*NOTE: Used to create a cmd command*/
//        String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
//
//        /*NOTE: Executing the command here*/
//        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//        int processComplete = runtimeProcess.waitFor();
//
//        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
//        if (processComplete == 0) {
//            System.out.println("Backup Complete");
//        } else {
//            System.out.println("Backup Failure");
//        }
//
//    } catch (URISyntaxException | IOException | InterruptedException ex) {
//        JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
//    }
    }
    

}
