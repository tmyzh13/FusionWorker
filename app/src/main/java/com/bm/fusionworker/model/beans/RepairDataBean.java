package com.bm.fusionworker.model.beans;

import java.io.Serializable;

/**
 * 上拉显示信息，根据实际情况更改下列属性
 * Created by issuser on 2018/5/9.
 */

public class RepairDataBean implements Serializable{
    public int id;
    public double latitude;
    public double longitude;
    public String title;
    public String time;
    public String distance;
    public String address;
    public int status;
}
