package com.example.i_for.writtingboard;

public class Bezier {

    private ControllerPoint mControl = new ControllerPoint();
    private ControllerPoint mDestination = new ControllerPoint();
    private ControllerPoint mNextControl = new ControllerPoint();
    private ControllerPoint mSource = new ControllerPoint();

    public Bezier(){}

    public void init(ControllerPoint last, ControllerPoint cur){
        init(last.x, last.y, last.width, cur.x, cur.y, cur.width);
    }
    public void init(float lastx, float lasty, float lastWidth, float x, float y, float width){
        mSource.set(lastx, lasty, lastWidth);
        float xmid = getMid(lastx, x);
        float ymid = getMid(lasty, y);
        float wmid = getMid(lastWidth, width);
        mDestination.set(xmid, ymid, wmid);
        mControl.set(getMid(lastx,xmid),getMid(lasty,ymid),getMid(lastWidth,wmid));
        mNextControl.set(x, y, width);
    }
    private float getMid(float x1, float x2){
        return (float)((x1 + x2) / 2.0);
    }

    public void addNode(ControllerPoint cur){
        addNode(cur.x, cur.y, cur.width);
    }
    public void addNode(float x, float y, float width){
        mSource.set(mDestination);
        mControl.set(mNextControl);
        mDestination.set(getMid(mNextControl.x, x), getMid(mNextControl.y, y), getMid(mNextControl.width, width));
        mNextControl.set(x, y, width);
    }

    public void end() {
        mSource.set(mDestination);
        float x = getMid(mNextControl.x, mSource.x);
        float y = getMid(mNextControl.y, mSource.y);
        float w = getMid(mNextControl.width, mSource.width);
        mControl.set(x, y, w);
        mDestination.set(mNextControl);
    }

    public ControllerPoint getPoint(double t){
        float x = (float)getX(t);
        float y = (float)getY(t);
        float w = (float)getW(t);
        ControllerPoint point = new ControllerPoint();
        point.set(x,y,w);
        return point;
    }
    private double getX(double t) {
        return getValue(mSource.x, mControl.x, mDestination.x, t);
    }
    private double getY(double t) {
        return getValue(mSource.y, mControl.y, mDestination.y, t);
    }
    private double getW(double t){
        return getWidth(mSource.width, mDestination.width, t);
    }
    private double getValue(double p0, double p1, double p2, double t){
        double A = p2 - 2 * p1 + p0;
        double B = 2 * (p1 - p0);
        double C = p0;
        return A * t * t + B * t + C;
    }
    private double getWidth(double w0, double w1, double t){
        return w0 + (w1 - w0) * t;
    }

}
