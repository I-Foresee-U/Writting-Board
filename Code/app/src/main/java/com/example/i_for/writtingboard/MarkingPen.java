package com.example.i_for.writtingboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class MarkingPen extends BasePenExtend {

    private Bitmap mBitmap;
    protected Rect mOldRect = new Rect();
    protected RectF mNeedDrawRect = new RectF();
    protected Bitmap mOriginBitmap;

    public MarkingPen (Context context) {
        super(context);
        initTexture();
    }
    private void initTexture() {
        mOriginBitmap = BitmapFactory.decodeResource(
                mContext.getResources(), R.mipmap.brush);
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
            point = getWithPointAlphaPoint(point);
            mHWPointList.add(point);
        }
    }
    private ControllerPoint getWithPointAlphaPoint(ControllerPoint point) {
        ControllerPoint nPoint = new ControllerPoint();
        nPoint.x = point.x;
        nPoint.y = point.y;
        nPoint.width = point.width;
        int alpha = (int) (255 * point.width / mBaseWidth / 2);
        if (alpha < 10) {
            alpha = 10;
        } else if (alpha > 255) {
            alpha = 255;
        }
        nPoint.alpha = alpha;
        return nPoint;
    }

    @Override
    protected void doNeetToDo(Canvas canvas, ControllerPoint point, Paint paint) {
        drawLine(canvas, mCurPoint.x, mCurPoint.y, mCurPoint.width,
                mCurPoint.alpha, point.x, point.y, point.width, point.alpha,
                paint);
    }
    protected void drawLine(Canvas canvas, double x0, double y0, double w0,
                            int a0, double x1, double y1, double w1, int a1, Paint paint) {
        double curDis = Math.hypot(x0 - x1, y0 - y1);
        int factor = 2;
        if (paint.getStrokeWidth() < 6) {
            factor = 1;
        } else if (paint.getStrokeWidth() > 60) {
            factor = 3;
        }
        int steps = 1 + (int) (curDis / factor);
        double deltaX = (x1 - x0) / steps;
        double deltaY = (y1 - y0) / steps;
        double deltaW = (w1 - w0) / steps;
        double deltaA = (a1 - a0) / steps;
        double x = x0;
        double y = y0;
        double w = w0;
        double a = a0;
        for (int i = 0; i < steps; i++) {
            if (w < 1.5)
                w = 1.5;
            mNeedDrawRect.set((float) (x - w / 2.0f), (float) (y - w / 2.0f),
                    (float) (x + w / 2.0f), (float) (y + w / 2.0f));
            paint.setAlpha((int) (a / 3.0f));
            canvas.drawBitmap(mBitmap, mOldRect, mNeedDrawRect, paint);
            x += deltaX;
            y += deltaY;
            w += deltaW;
            a += deltaA;
        }
    }

    @Override
    public void setPaint(Paint paint) {
        super.setPaint(paint);
        setBitmap(mOriginBitmap);
    }
    private void setBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas();
        mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ALPHA_8);
        mBitmap.eraseColor(Color.rgb(Color.red(mPaint.getColor()),
                Color.green(mPaint.getColor()), Color.blue(mPaint.getColor())));
        canvas.setBitmap(mBitmap);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        mOldRect.set(0, 0, mBitmap.getWidth()/4, mBitmap.getHeight()/4);
    }

    @Override
    protected Paint getNewPaint(Paint paint) {
        return new Paint(paint);
    }

}
