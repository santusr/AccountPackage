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


public class ItemFlow extends JFrame {

    public ItemFlow(String code, String toCode, String fdate, String edate, String rep) throws Exception {
        this(new File(".").getCanonicalPath() + "\\src\\report\\"+rep, fdate, edate, "", code, toCode);

        setTitle("Item Flow Rdeport");
        
        this.setExtendedState(MAXIMIZED_BOTH);

    }

    public ItemFlow(String fileName, String fdate, String edate, String s, String code, String toCode) {
        super("View Report");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ITEM", code);
            params.put("TO_ITEM", toCode);
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
