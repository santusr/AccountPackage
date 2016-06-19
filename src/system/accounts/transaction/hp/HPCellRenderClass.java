/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.hp;

import system.accounts.transaction.voucher_cancel.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RoWi
 */
public class HPCellRenderClass extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
//        Object valueAt = table.getModel().getValueAt(row, 8);
//        String s = "";
//        if (valueAt != null) {
//            s = valueAt.toString();
//        }

        if (table.getColumnCount() == 5) {
            if (col > 0 && col < 4) {
                setHorizontalAlignment(JLabel.RIGHT);
            } else {
                setHorizontalAlignment(JLabel.LEFT);
            }
        } else {
            if (col > 4 && col < 8) {
                setHorizontalAlignment(JLabel.RIGHT);
            } else {
                setHorizontalAlignment(JLabel.LEFT);
            }
        }

        Color bg = new Color(200, 234, 255);
        Color bgs = new Color(53, 155, 255);
        if (row % 2 == 0) {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(bg);
        } else {
            c.setForeground(Color.DARK_GRAY);
            c.setBackground(Color.WHITE);
        }
        c.setFocusable(true);
        table.setRowSelectionAllowed(true);
        if (isSelected) {
            c.setBackground(bgs);
            c.setForeground(Color.WHITE);
        }
        return c;
    }

}
