/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.customer;

/**
 *
 * @author RoWi
 */
public class CustomerStatus {

    public static final String ACTIVE = "ACTIVE";
    public static final String BlACKLIST = "BLACKLIST";
    public static final String AVARAGE = "AVARAGE";
    public static final String DELETE = "DELETE";

    public static String customerStatus(String status) {
        switch (status) {
            case "0":
                return ACTIVE;
            case "1":
                return DELETE;
            case "4":
                return BlACKLIST;
            case "2":
                return AVARAGE;
            default:
                return ACTIVE;

        }
    }
}
