package com.example.oryossipof.alphahotal;

import java.io.Serializable;


public class Employee implements Serializable{

    public String firtstname;
    public String lastname;
    public String deppartment;
    public String imageStr;


    public Employee(String firtstname, String lastname, String deppartment, String imageStr) {
        this.firtstname = firtstname;
        this.lastname = lastname;
        this.deppartment = deppartment;
        this.imageStr = imageStr;
    }

    public String getFirtstname() {
        return firtstname;
    }

    public void setFirtstname(String firtstname) {
        this.firtstname = firtstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDeppartment() {
        return deppartment;
    }

    public void setDeppartment(String deppartment) {
        this.deppartment = deppartment;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }
}

