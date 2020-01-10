package com.example.i_for.writtingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

public abstract class BasePenExtend extends BasePen {

    public ArrayList<ControllerPoint> mHWPointList = new ArrayList<>();
    public ArrayList<ControllerPoint> mPointList = new ArrayList<ControllerPoint>();
    public ControllerPoint mLastPoint = new ControllerPoint(0,0);

    public Paint mPaint;
    public double mBaseWidth;
    public double mLastVel;
    public double mLastWidth;
    public Bezier mBezier = new Bezier();

    protected ControllerPoint mCurPoint;
    protected Context mContext;

    public BasePenExtend(Context context){
        mContext=context;
    }

    public void setPaint (Paint paint){
        mPaint=paint;
        mBaseWidth=paint.getStrokeWidth();
    }

    @Override
    public void draw(Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);
        if(mHWPointList==null||mHWPointList.size()<1)
            return;
        if(mHWPointList.size()<2){
            ControllerPoint point = mHWPointList.get(0);
        }else{
            mCurPoint=mHWPointList.get(0);
            drawNeetToDo(canvas);
        }
    }
    protected abstract void drawNeetToDo(Canvas canvas);

    @Override
    public boolean onTouchEvent(MotionEvent event,Canvas canvas){
        MotionEvent event2 = MotionEvent.obtain(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onDown(createMotionElement(event2));
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(createMotionElement(event2));
                return true;
            case MotionEvent.ACTION_UP:
                onUp(createMotionElement(event2),canvas);
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event,canvas);
    }

    public MotionElement createMotionElement(MotionEvent motionEvent) {
        MotionElement motionElement = new MotionElement(motionEvent.getX(), motionEvent.getY(),
                motionEvent.getPressure(), motionEvent.getToolType(0));
        return motionElement;
    }

    public void onDown(MotionElement mElement){
        if (mPaint==null){
            throw new NullPointerException("paint cannot be null!");
        }
        if (getNewPaint(mPaint)!=null){
            Paint paint=getNewPaint(mPaint);
            mPaint=paint;
            paint=null;
            System.out.println("当绘制的时候是否为新的paint"+mPaint+"原来的对象是否销毁了paint=="+paint);
        }
        mPointList.clear();
        mHWPointList.clear();
        ControllerPoint curPoint = new ControllerPoint(mElement.x, mElement.y);
        if (mElement.tooltype == MotionEvent.TOOL_TYPE_STYLUS) {
            mLastWidth = mElement.pressure * mBaseWidth;
        } else {
            mLastWidth = 0.8 * mBaseWidth;
        }
        curPoint.width = (float) mLastWidth;
        mLastVel = 0;
        mPointList.add(curPoint);
        mLastPoint = curPoint;
    }
    protected  Paint getNewPaint(Paint paint){
        return null;
    }

    public void onMove(MotionElement mElement){
        ControllerPoint curPoint = new ControllerPoint(mElement.x, mElement.y);
        double deltaX = curPoint.x - mLastPoint.x;
        double deltaY = curPoint.y - mLastPoint.y;
        double curDis = Math.hypot(deltaX, deltaY);
        double curVel = curDis * IPenConfig.STROKE_FACTOR;
        double curWidth;
        if (mPointList.size() < 2) {
            if (mElement.tooltype == MotionEvent.TOOL_TYPE_STYLUS) {
                curWidth = mElement.pressure * mBaseWidth;
            } else {
                curWidth = calcNewWidth(curVel, mLastVel, curDis, 1.5,
                        mLastWidth);
            }
            curPoint.width = (float) curWidth;
            mBezier.init(mLastPoint, curPoint);
        } else {
            mLastVel = curVel;
            if (mElement.tooltype == MotionEvent.TOOL_TYPE_STYLUS) {
                curWidth = mElement.pressure * mBaseWidth;
            } else {
                curWidth = calcNewWidth(curVel, mLastVel, curDis, 1.5,
                        mLastWidth);
            }
            curPoint.width = (float) curWidth;
            mBezier.addNode(curPoint);
        }
        mLastWidth = curWidth;
        mPointList.add(curPoint);
        moveNeetToDo(curDis);
        mLastPoint = curPoint;
    }
    protected abstract void moveNeetToDo(double f);

    public double calcNewWidth(double curVel, double lastVel, double curDis,
                               double factor, double lastWidth) {
        double calVel = curVel * 0.6 + lastVel * (1 - 0.6);
        double vfac = Math.log(factor * 2.0f) * (-calVel);
        double calWidth = mBaseWidth * Math.exp(vfac);
        double mMoveThres = curDis * 0.01f;
        if (mMoveThres > IPenConfig.VELOCITY_FACTOR) {
            mMoveThres = IPenConfig.VELOCITY_FACTOR;
        }
        return calWidth;
    }

    public void onUp(MotionElement mElement, Canvas canvas){
        mCurPoint = new ControllerPoint(mElement.x, mElement.y);
        double deltaX = mCurPoint.x - mLastPoint.x;
        double deltaY = mCurPoint.y - mLastPoint.y;
        double curDis = Math.hypot(deltaX, deltaY);
        if (mElement.tooltype == MotionEvent.TOOL_TYPE_STYLUS) {
            mCurPoint.width = (float) (mElement.pressure * mBaseWidth);
        } else {
            mCurPoint.width = 0;
        }
        mPointList.add(mCurPoint);
        mBezier.addNode(mCurPoint);
        int steps = 1 + (int) curDis / IPenConfig.STEP_FACTOR;
        double step = 1.0 / steps;
        for (double t = 0; t < 1.0; t += step) {
            ControllerPoint point = mBezier.getPoint(t);
            mHWPointList.add(point);
        }
        mBezier.end();
        for (double t = 0; t < 1.0; t += step) {
            ControllerPoint point = mBezier.getPoint(t);
            mHWPointList.add(point);
        }
        draw(canvas);
        clear();
    }
    public void clear() {
        mPointList.clear();
        mHWPointList.clear();
    }

    protected void drawToPoint(Canvas canvas, ControllerPoint point, Paint paint) {
        if ((mCurPoint.x == point.x) && (mCurPoint.y == point.y)) {
            return;
        }
        doNeetToDo(canvas, point, paint);
    }
    protected abstract void doNeetToDo(Canvas canvas, ControllerPoint point, Paint paint);

    public boolean isNull() {
        return mPaint == null;
    }

}
