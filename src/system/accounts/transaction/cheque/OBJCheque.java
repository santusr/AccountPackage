/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.accounts.transaction.cheque;

/**
 *
 * @author dell
 */
public class OBJCheque {
    private String Code;
    private String CustCode;
    private String InvoNo;
    private String ChqeNo;
    private String getDate;
    private String RDate;
    private String Amount;
    private String Bank;
    private String FCCode;
    private String FCRate;
    private String Remarks;
    private String Type;
    private String BankCharge;
    private String InterestCharge;
    private String Net;
    private String bankAccount;
    private String depositAccount;
    private String Status;
    private String transaction;
    private String depositTransaction;
    private String realizeTransaction;

    public OBJCheque() {
    }

    public OBJCheque(String Code, String CustCode, String InvoNo, String ChqeNo, String getDate, String RDate, String Amount, String Banck, String FCCode, String FCRate, String Remarks, String Type, String BankCharge, String InterestCharge, String Net, String bankAccount, String Status, String depositAccount, String transaction, String depositTransaction, String realizeTransaction) {
        this.Code = Code;
        this.CustCode = CustCode;
        this.InvoNo = InvoNo;
        this.ChqeNo = ChqeNo;
        this.getDate = getDate;
        this.RDate = RDate;
        this.Amount = Amount;
        this.Bank = Banck;
        this.FCCode = FCCode;
        this.FCRate = FCRate;
        this.Remarks = Remarks;
        this.Type = Type;
        this.BankCharge = BankCharge;
        this.InterestCharge = InterestCharge;
        this.Net = Net;
        this.bankAccount = bankAccount;
        this.Status = Status;
        this.depositAccount = depositAccount;
        this.transaction = transaction;
        this.depositTransaction = depositTransaction;
        this.realizeTransaction = realizeTransaction;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public String getNet() {
        return Net;
    }

    public void setNet(String Net) {
        this.Net = Net;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String Bank) {
        this.Bank = Bank;
    }

    public String getBankCharge() {
        return BankCharge;
    }

    public void setBankCharge(String BankCharge) {
        this.BankCharge = BankCharge;
    }

    public String getChqeNo() {
        return ChqeNo;
    }

    public void setChqeNo(String ChqeNo) {
        this.ChqeNo = ChqeNo;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getCustCode() {
        return CustCode;
    }

    public void setCustCode(String custCode) {
        this.CustCode = custCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    public String getFCCode() {
        return FCCode;
    }

    public void setFCCode(String FCCode) {
        this.FCCode = FCCode;
    }

    public String getFCRate() {
        return FCRate;
    }

    public void setFCRate(String FCRate) {
        this.FCRate = FCRate;
    }

    public String getInterestCharge() {
        return InterestCharge;
    }

    public void setInterestCharge(String InterestCharge) {
        this.InterestCharge = InterestCharge;
    }

    public String getInvoNo() {
        return InvoNo;
    }

    public void setInvoNo(String InvoNo) {
        this.InvoNo = InvoNo;
    }

    public String getRDate() {
        return RDate;
    }

    public void setRDate(String RDate) {
        this.RDate = RDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getDepositTransaction() {
        return depositTransaction;
    }

    public void setDepositTransaction(String depositTransaction) {
        this.depositTransaction = depositTransaction;
    }

    public String getRealizeTransaction() {
        return realizeTransaction;
    }

    public void setRealizeTransaction(String realizeTransaction) {
        this.realizeTransaction = realizeTransaction;
    }

    @Override
    public String toString() {
        return ChqeNo;
    }
//    
    
}
