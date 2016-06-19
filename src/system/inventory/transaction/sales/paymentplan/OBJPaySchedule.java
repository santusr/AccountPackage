/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.transaction.sales.paymentplan;

/**
 *
 * @author Rabid
 */
public class OBJPaySchedule {
    private String CreditId;
    private String Install;
    private String date;
    private String Amount;
    private String totAmount;
    private static int count = 0;
    private String invoNo;

    public OBJPaySchedule(String date, String Amount, String totAmount) {
        this.date = date;
        this.Amount = Amount;
        this.totAmount = totAmount;
        count++;
    }

    public OBJPaySchedule(String CreditId, String Install, String date, String Amount, String invoNo) {
        this.CreditId = CreditId;
        this.Install = Install;
        this.date = date;
        this.Amount = Amount;
        this.invoNo = invoNo;
    }

    public String getCreditId() {
        return CreditId;
    }

    public void setCreditId(String CreditId) {
        this.CreditId = CreditId;
    }

    public String getInstall() {
        return Install;
    }

    public void setInstall(String Install) {
        this.Install = Install;
    }

    public String getInvoNo() {
        return invoNo;
    }

    public void setInvoNo(String invoNo) {
        this.invoNo = invoNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        OBJPaySchedule.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(String totAmount) {
        this.totAmount = totAmount;
    }
}
