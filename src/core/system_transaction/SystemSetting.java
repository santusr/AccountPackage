/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.system_transaction;

import core.system_transaction.account_trans.AccountSettingObject;
import core.system_transaction.payment.PaymentSettingObject;
import java.util.ArrayList;

/**
 *
 * @author RoWi
 */
public class SystemSetting {

    public static PaymentSettingObject getPaymentSeting(String paymentSetting) {
        ArrayList<PaymentSettingObject> paymentSettingObjects = accountpackage.AccountPackage.paymentSettingObjects;
        PaymentSettingObject paymentSettingObject = new PaymentSettingObject();
        int i = 0;
        for (PaymentSettingObject object : paymentSettingObjects) {
            if (object.getCode().equals(paymentSetting)) {
                i = 1;
                paymentSettingObject = object;
            }
        }
                return paymentSettingObject;
    }

    public static AccountSettingObject getAccountSeting(String accountSetting) {
        ArrayList<AccountSettingObject> accountSettingObjects = accountpackage.AccountPackage.accountSettingObjects;
        AccountSettingObject accountSettingObject = new AccountSettingObject();
        for (AccountSettingObject object : accountSettingObjects) {
            if (object.getCode().equals(accountSetting)) {
                accountSettingObject = object;
            }
        }
        return accountSettingObject;
    }
}
