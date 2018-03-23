package com.example.oryossipof.alphahotal;


import java.io.Serializable;

public class MyActivity implements Serializable {

    public String activityName;
    public String info;


    public MyActivity(String activityName,String info) {
        this.activityName = activityName;
        this.info = info;
    }
}
