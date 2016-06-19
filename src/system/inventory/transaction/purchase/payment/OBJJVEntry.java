/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.inventory.transaction.purchase.payment;

import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class OBJJVEntry {
     private String journalEntryNo;
    private String journalEntryDate;
    private String Remark;
    private String amount;
    private ArrayList<OBJEntry> entrys = new ArrayList<OBJEntry>();

    public OBJJVEntry() {
    }

    public String getJournalEntryNo() {
        return journalEntryNo;
    }

    public void setJournalEntryNo(String journalEntryNo) {
        this.journalEntryNo = journalEntryNo;
    }

    public String getJournalEntryDate() {
        return journalEntryDate;
    }

    public void setJournalEntryDate(String journalEntryDate) {
        this.journalEntryDate = journalEntryDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ArrayList<OBJEntry> getEntrys() {
        return entrys;
    }

    public void setEntrys(ArrayList<OBJEntry> entrys) {
        this.entrys = entrys;
    }
    
}
