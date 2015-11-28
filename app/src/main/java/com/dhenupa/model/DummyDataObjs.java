package com.dhenupa.model;

/**
 * Created by Kousalya K G on 18-10-2015.
 */
public class DummyDataObjs {
    String name;
    String address;
    String doj;


    public DummyDataObjs(String nm, String na, String nb){
        super();
        this.name = nm;
        this.address = na;
        this.doj =nb;

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
        address = address;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }


}
