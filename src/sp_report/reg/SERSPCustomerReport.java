package sp_report.reg;

import core.DBConnection;
import core.Exp;
import javax.swing.*;
import java.awt.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;


public class SERSPCustomerReport extends JFrame {

    public SERSPCustomerReport(String code, String fdate, String edate, String rep, String title) throws Exception {
        this(new File(".").getCanonicalPath() + "\\src\\report\\"+rep, code, fdate, edate);

        setTitle(title);
        
        this.setExtendedState(MAXIMIZED_BOTH);

    }

    public SERSPCustomerReport(String fileName, String code, String fdate, String edate) {
        super("View Report");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("customer", code);
            params.put("edate", edate);
            params.put("fdate", fdate);
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
}
