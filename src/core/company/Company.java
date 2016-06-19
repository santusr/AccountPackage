/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.company;

/**
 *
 * @author Rabid
 */
public class Company {

    private String code;
    private String name;
    private String address1;
    private String address2;
    private String address3;
    private String tp;
    private String regNo;
    private String status;
    private String workingDate;
    private String mainCostCenter;

    public Company() {
    }

    public Company(String code, String name, String address1, String address2, String address3, String tp, String regNo, String status, String workingDate, String mainCostCenter) {
        this.code = code;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.tp = tp;
        this.regNo = regNo;
        this.status = status;
        this.workingDate = workingDate;
        this.mainCostCenter = mainCostCenter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getMainCostCenter() {
        return mainCostCenter;
    }

    public void setMainCostCenter(String mainCostCenter) {
        this.mainCostCenter = mainCostCenter;
    }

    @Override
    public String toString() {
        return code;
    }

}
