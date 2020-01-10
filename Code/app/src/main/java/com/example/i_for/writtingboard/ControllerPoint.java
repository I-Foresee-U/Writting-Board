package com.example.i_for.writtingboard;

public class ControllerPoint {

    public float x;
    public float y;
    public float width;
    public int alpha=255;

    public ControllerPoint(){}
    public ControllerPoint(float x,float y){
        this.x=x;
        this.y=y;
    }

    public void set(float x,float y,float w){
        this.x=x;
        this.y=y;
        this.width=w;
    }

    public  void set(ControllerPoint point){
        this.x=point.x;
        this.y=point.y;
        this.width=point.width;
    }

    public String toString(){
        String str="X="+x+";Y="+y+"W="+width;
        return str;
    }
}
