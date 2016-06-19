/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.vendore;

import system.masters.customer.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import system.accounts.transaction.cheque.ChequeStatus;

/**
 *
 * @author RoWi
 */
public class VendoreCellRender extends DefaultTableCellRenderer {

   private static final long serialVersionUID = 1L;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
        Object valueAt = table.getModel().getValueAt(row, 7);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }
        Color r = new Color(255, 153, 153);
        Color g = new Color(153, 255, 153);
        Color o = new Color(255, 204, 102);
        if (s.equalsIgnoreCase(CustomerStatus.ACTIVE)) {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(g);
        } else if (s.equalsIgnoreCase(CustomerStatus.AVARAGE)) {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(o);
        } else if (s.equalsIgnoreCase(CustomerStatus.BlACKLIST)) {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(r);
        } else {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(Color.WHITE);
        }
        c.setFocusable(true);
        table.setRowSelectionAllowed(true);
        if(isSelected){
            c.setBackground(Color.BLUE);
            c.setForeground(Color.WHITE);
        }
        return c;
    }
}
