package com.bm.fusionworker.utils;


import com.bm.fusionworker.constants.Constant;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChoiceManager {

    public static ChoiceManager instance;

    private int statue=1;
    //距离 默认距离是100 这里测试改成500
    private double distance= Constant.DEFAULT_DISTANCE;

    private ChoiceManager(){

    }

    public static ChoiceManager getInstance(){
        if(instance==null){
            instance=new ChoiceManager();
        }
        return instance;
    }

    public void resetChoice(){
        statue=1;
        distance= Constant.DEFAULT_DISTANCE;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
