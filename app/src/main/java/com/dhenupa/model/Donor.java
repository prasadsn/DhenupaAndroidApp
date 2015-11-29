package com.dhenupa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.sql.Date;
//import com.j256.ormlite.field.DatabaseField;

/**
 * Created by prasad on 13-09-2015.
 */
public class Donor implements Parcelable {

    @DatabaseField(generatedId = true, columnName = "_id")
    public int _Id;

    @DatabaseField
    @SerializedName("userid")
    private int userid;

    @DatabaseField
    @SerializedName("name")
    private String name;

    @DatabaseField
    @SerializedName("address")
    private String address;

    @DatabaseField
    @SerializedName("area")
    private String area;

    @DatabaseField
    @SerializedName("city")
    private String city;

    @DatabaseField
    @SerializedName("contactNumber")
    private String contactNumber;

    @DatabaseField
    @SerializedName("donationType")
    private String donationType;

    @DatabaseField
    @SerializedName("amount")
    private int amount;

    @DatabaseField
    @SerializedName("emailId")
    private String emailId;

    @DatabaseField
    @SerializedName("rashi")
    private String rashi;

    @DatabaseField
    @SerializedName("nakshatra")
    private String nakshatra;

    @DatabaseField
    @SerializedName("gothra")
    private String gothra;

    @DatabaseField
    @SerializedName("job")
    private String job;

    @DatabaseField
    @SerializedName("registeredDate")
    private String registeredDate;

    @DatabaseField
    @SerializedName("dob")
    private String dob;

    @DatabaseField
    @SerializedName("imagePath")
    private String imagePath;

    @DatabaseField
    @SerializedName("comment")
    private String comment;

    @DatabaseField
    @SerializedName("last_modified")
    private Date last_modified;

  /*  public String getScriptName(){
        return ScriptName;
    }*/
    @Override
    public int describeContents() {
        return 0;
    }

    public Donor(){
    }

    public Donor(Donor donor){
        userid = donor.getUserid();
        name = donor.getName();
        address = donor.getAddress();
        city = donor.getCity();
        contactNumber = donor.getContactNumber();
        donationType = donor.getDonationType();
        amount = donor.getAmount();
        emailId = donor.getEmailId();
        rashi = donor.getRashi();
        nakshatra = donor.getNakshatra();
        gothra = donor.getGothra();
        job = donor.getJob();
        registeredDate = donor.getRegisteredDate();
        dob = donor.getDob();
        imagePath = donor.getImagePath();
        comment = donor.getComment();
        last_modified = donor.getLast_modified();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userid);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(city);
        dest.writeString(contactNumber);
        dest.writeString(donationType);
        dest.writeString(String.valueOf(amount));

        dest.writeString(emailId);
        dest.writeString(rashi);
        dest.writeString(nakshatra);

        dest.writeString(gothra);
        dest.writeString(job);
        dest.writeString(registeredDate);

        dest.writeString(dob);
        dest.writeString(imagePath);
        dest.writeString(comment);
        dest.writeString(String.valueOf(last_modified));



    }

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

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }
    public static final Creator<Donor> CREATOR = new Creator<Donor>() {
        /*public Donor createFromParcel(Parcel source) {
            return new Donor(source);
        }*/

        @Override
        public Donor createFromParcel(Parcel source) {
            return null;
        }

        public Donor[] newArray(int size) {
            return new Donor[size];
        }
    };





}
