package com.example.i_for.writtingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class BrushPen extends BasePenExtend {

    public BrushPen(Context context) {
        super(context);
    }

    @Override
    protected void drawNeetToDo(Canvas canvas) {
        for (int i = 1; i < mHWPointList.size(); i++) {
            ControllerPoint point = mHWPointList.get(i);
            drawToPoint(canvas, point, mPaint);
            mCurPoint = point;
        }
    }

    @Override
    protected void moveNeetToDo(double curDis) {
        int steps = 1 + (int) curDis / IPenConfig.STEP_FACTOR;
        double step = 1.0 / steps;
        for (double t = 0; t < 1.0; t += step) {
            ControllerPoint point = mBezier.getPoint(t);
            mHWPointList.add(point);
        }
    }

    @Override
    protected void doNeetToDo(Canvas canvas, ControllerPoint point, Paint paint) {
        drawLine(canvas, mCurPoint.x, mCurPoint.y, mCurPoint.width, point.x,
                point.y, point.width, paint);
    }

    private void drawLine(Canvas canvas, double x0, double y0, double w0, double x1, double y1, double w1, Paint paint) {
        double curDis = Math.hypot(x0 - x1, y0 - y1);
        int steps = 1;
        if (paint.getStrokeWidth() < 6) {
            steps = 1 + (int) (curDis / 2);
        } else if (paint.getStrokeWidth() > 60) {
            steps = 1 + (int) (curDis / 4);
        } else {
            steps = 1 + (int) (curDis / 3);
        }
        double deltaX = (x1 - x0) / steps;
        double deltaY = (y1 - y0) / steps;
        double deltaW = (w1 - w0) / steps;
        double x = x0;
        double y = y0;
        double w = w0;
        for (int i = 0; i < steps; i++) {
            RectF oval = new RectF();
            oval.set((float) (x - w / 4.0f), (float) (y - w / 2.0f), (float) (x + w / 4.0f), (float) (y + w / 2.0f));
            canvas.drawOval(oval, paint);
            x += deltaX;
            y += deltaY;
            w += deltaW;
        }
    }

}