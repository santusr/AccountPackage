/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.master.accountcreation;

/**
 *
 * @author Rabid
 */
public class OBJAccountCreation {
    private String AccCode;
    private String AccName;
    private String L1Type;
    private String L2Type;
    private String L3Type;
    private String OPBalance;
    private String Debit;
    private String Credit;
    private String AccType;
    private String PCode;

    public OBJAccountCreation(String AccCode, String AccName, String L1Type, String L2Type, String L3Type, String OPBalance, String Debit, String Credit, String AccType, String PCode) {
        this.AccCode = AccCode;
        this.AccName = AccName;
        this.L1Type = L1Type;
        this.L2Type = L2Type;
        this.L3Type = L3Type;
        this.OPBalance = OPBalance;
        this.Debit = Debit;
        this.Credit = Credit;
        this.AccType = AccType;
        this.PCode = PCode;
    }

    public String getAccCode() {
        return AccCode;
    }

    public void setAccCode(String AccCode) {
        this.AccCode = AccCode;
    }

    public String getAccName() {
        return AccName;
    }

    public void setAccName(String AccName) {
        this.AccName = AccName;
    }

    public String getAccType() {
        return AccType;
    }

    public void setAccType(String AccType) {
        this.AccType = AccType;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String Credit) {
        this.Credit = Credit;
    }

    public String getDebit() {
        return Debit;
    }

    public void setDebit(String Debit) {
        this.Debit = Debit;
    }

    public String getL1Type() {
        return L1Type;
    }

    public void setL1Type(String L1Type) {
        this.L1Type = L1Type;
    }

    public String getL2Type() {
        return L2Type;
    }

    public void setL2Type(String L2Type) {
        this.L2Type = L2Type;
    }

    public String getL3Type() {
        return L3Type;
    }

    public void setL3Type(String L3Type) {
        this.L3Type = L3Type;
    }

    public String getOPBalance() {
        return OPBalance;
    }

    public void setOPBalance(String OPBalance) {
        this.OPBalance = OPBalance;
    }

    public String getPCode() {
        return PCode;
    }

    public void setOPCode(String PCode) {
        this.PCode = PCode;
    }
    
}
