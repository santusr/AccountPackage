package system.inventory.transaction.purchase;

import core.DBConnection;
import core.Exp;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class PrintGRN extends JFrame {

    public PrintGRN(String invNo) throws Exception {

        this(new File(".").getCanonicalPath() + "\\src\\report\\REPGRN.jasper", invNo);


        setTitle("GRN");
        this.setExtendedState(MAXIMIZED_BOTH);
        
        //Toolkit t = getToolkit();
        //Dimension d = t.getScreenSize();
        //int wdh = d.width;
        //int hgh = d.height - 140;
        //setBounds(0, 0, wdh, hgh);

    }

    public PrintGRN(String fileName, String invNo) {
        super("View Report");
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("invoNo", invNo);

            Connection cn = DBConnection.getConnection();
           
            JasperPrint print = JasperFillManager.fillReport(fileName, params, cn);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);
        } catch (SQLException | JRException cnfe) {
            Exp.Handle(cnfe);
        }


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }
//
//    public static void main(String args[]) {
//        /*
//         * A sample calling
//         */
//        /*
//         * HashMap param=new HashMap();
//         * param.put("reportParameterName",valueForTheParameter); MyReportViewer
//         * viewer=new MyReportViewer("Report File Name With Extension",param);
//viewer.setVisible(true);
//         */
//    }
}
