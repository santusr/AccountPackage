/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.journalvoucher.cancel;

/**
 *
 * @author RoWi
 */
public class JVHeader {
    private String JVNo;
    private String JVDate;
    private String Remark;
    private String Amount;

    public JVHeader() {
    }

    public String getJVNo() {
        return JVNo;
    }

    public void setJVNo(String JVNo) {
        this.JVNo = JVNo;
    }

    public String getJVDate() {
        return JVDate;
    }

    public void setJVDate(String JVDate) {
        this.JVDate = JVDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    @Override
    public String toString() {
        return JVNo; //To change body of generated methods, choose Tools | Templates.
    }
    
}
