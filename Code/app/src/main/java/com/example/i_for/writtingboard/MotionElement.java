package com.example.i_for.writtingboard;

public class MotionElement {

    public float x;
    public float y;
    public float pressure;
    public int tooltype;

    public MotionElement(float mx,float my,float mp,int ttype){
        x=mx;
        y=my;
        pressure=mp;
        tooltype=ttype;
    }
}
