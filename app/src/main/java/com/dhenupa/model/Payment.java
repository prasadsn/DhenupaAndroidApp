package com.dhenupa.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.field.DatabaseField;

/**
 * Created by prasad on 19-12-2015.
 */
public class Payment implements Parcelable {

    @DatabaseField(generatedId = true, columnName = "_id")
    public int _Id;

    @DatabaseField
    public int donorId;

    @DatabaseField
    @SerializedName("payment_id")
    private int payment_id;

    @DatabaseField
    @SerializedName("userid")
    private int userid;

    @DatabaseField
    @SerializedName("amount")
    private int amount;

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    @DatabaseField
    @SerializedName("payment_date")
    private String payment_date;

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    @DatabaseField
    @SerializedName("payment_mode")
    private String payment_mode;

    @DatabaseField
    @SerializedName("lastModified")
    private String lastModified;

    @DatabaseField
    @SerializedName("status")
    private int status;

  /*  public String getScriptName(){
        return ScriptName;
    }*/
    @Override
    public int describeContents() {
        return 0;
    }

    public Payment(){
    }

    public Payment(Payment donor){
        userid = donor.getUserid();
        amount = donor.getAmount();
        lastModified = donor.getLastModified();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userid);
        dest.writeString(String.valueOf(amount));
        dest.writeString(String.valueOf(lastModified));
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getLastModified() {
        return lastModified;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }
    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        /*public Donor createFromParcel(Parcel source) {
            return new Donor(source);
        }*/

        @Override
        public Payment createFromParcel(Parcel source) {
            return null;
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
}
