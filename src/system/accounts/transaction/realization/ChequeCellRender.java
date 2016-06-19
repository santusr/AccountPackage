/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.realization;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import system.accounts.transaction.cheque.ChequeStatus;

/**
 *
 * @author RoWi
 */
public class ChequeCellRender extends DefaultTableCellRenderer {

   private static final long serialVersionUID = 1L;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
        Object valueAt = table.getModel().getValueAt(row, 8);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }
        if (s.equalsIgnoreCase(ChequeStatus.REALIZE)) {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.GREEN);
        } else if (s.equalsIgnoreCase(ChequeStatus.DEPOSIT)) {
            c.setForeground(Color.black);
            c.setBackground(Color.ORANGE);
        } else if (s.equalsIgnoreCase(ChequeStatus.BOUNCED)) {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.RED);
        } else if (s.equalsIgnoreCase(ChequeStatus.PENDING)) {
            c.setForeground(Color.black);
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
