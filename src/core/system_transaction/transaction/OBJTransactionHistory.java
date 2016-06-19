/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.system_transaction.transaction;

/**
 *
 * @author RoWi
 */
public class OBJTransactionHistory {

    private String action;
    private String note;
    private String user;
    private OBJTransaction transaction;

    public OBJTransactionHistory() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public OBJTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(OBJTransaction transaction) {
        this.transaction = transaction;
    }

}
