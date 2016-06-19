/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.system_transaction.payment.multy_payment;

import java.util.ArrayList;
import system.accounts.transaction.cheque.OBJCheque;

/**
 *
 * @author RoWi
 */
public class PaymentMethod {
    private static double cash;
    private static ArrayList<OBJCheque> cheques;

    public PaymentMethod() {
    }

    public static double getCash() {
        return cash;
    }

    public static void setCash(double cash) {
        PaymentMethod.cash = cash;
    }

    public static ArrayList<OBJCheque> getCheques() {
        return cheques;
    }

    public static void setCheques(ArrayList<OBJCheque> cheques) {
        PaymentMethod.cheques = cheques;
    }

}
