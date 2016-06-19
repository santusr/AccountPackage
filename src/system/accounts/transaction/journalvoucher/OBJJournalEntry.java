/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package system.accounts.transaction.journalvoucher;

import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class OBJJournalEntry {
    private String journalEntryNo;
    private String journalEntryDate;
    private String Remark;
    private String amount;
    private ArrayList<OBJEntry> entrys = new ArrayList<OBJEntry>();

    public OBJJournalEntry() {
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

class OBJEntry{
    private String account;
    private String accountName;
    private String naration;
    private String creditAmount;
    private String debitAmount;

    public OBJEntry() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getNaration() {
        return naration;
    }

    public void setNaration(String naration) {
        this.naration = naration;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    @Override
    public String toString() {
        return account + " - " + accountName; //To change body of generated methods, choose Tools | Templates.
    }
    
}