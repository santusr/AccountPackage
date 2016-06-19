/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import system.masters.customer.CustomerStatus;

/**
 *
 * @author RoWi
 */
public class CellRender extends DefaultTableCellRenderer {

    private String status;

    public CellRender(String red) {
        status = red;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 1, 9);

        //Get the status for the current row.
        System.out.println(status);
        switch (status) {
            case CustomerStatus.ACTIVE:
                l.setBackground(Color.GREEN);
                break;
            case CustomerStatus.AVARAGE:
                l.setBackground(Color.ORANGE);
                break;
            case CustomerStatus.BlACKLIST:
                l.setBackground(Color.RED);
                break;
        }
        //Return the JLabel which renders the cell.
        return l;
    }
}
