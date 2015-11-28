package com.dhenupa.model;

import java.util.Date;

/**
 * Created by Kousalya K G on 23-10-2015.
 */
public class Donor {

    private int userid;
    private String name;
    private String address;
    private String area;
    private String city;
    private String contactNumber;
    private String donationType;
    private int amount;
    private String emailId;
    private String rashi;
    private String nakshatra;
    private String gothra;
    private String job;
    private Date registeredDate;
    private Date dob;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRashi() {
        return rashi;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }

    public String getNakshatra() {
        return nakshatra;
    }

    public void setNakshatra(String nakshatra) {
        this.nakshatra = nakshatra;
    }

    public String getGothra() {
        return gothra;
    }

    public void setGothra(String gothra) {
        this.gothra = gothra;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;

    @Override
    public String toString() {
        return "DonorList [userid=" + userid + ", Name=" + name
                + ", address=" + address + ", dob=" + dob + ", email="
                + emailId + ", contactNumber=" + contactNumber + ", gothra =" + gothra + "]";
    }
}
