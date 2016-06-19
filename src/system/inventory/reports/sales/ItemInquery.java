package system.inventory.reports.sales;

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


public class ItemInquery extends JFrame {

    public ItemInquery(String code, String fdate, String edate, String rep) throws Exception {
        this(new File(".").getCanonicalPath() + "\\src\\report\\"+rep, fdate, edate, "", code);

        setTitle("Sales Rdeport");
        
        this.setExtendedState(MAXIMIZED_BOTH);

    }

    public ItemInquery(String fileName, String fdate, String edate, String s, String code) {
        super("View Report");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ITEM", code);
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
