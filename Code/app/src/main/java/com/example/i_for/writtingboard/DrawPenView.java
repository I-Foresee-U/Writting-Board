package com.example.i_for.writtingboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawPenView extends View {

    private static final String TAG = "DrawPenView";
    private Paint mPaint;
    private Canvas mCanvas;
    public static Bitmap mBitmap;
    private Context mContext;
    public static int mCanvasCode = IPenConfig.BRUSH_PEN;
    private BasePenExtend mPen;
    private boolean mIsCanvasDraw;
    private int mPenConfig;

//    public Bitmap getImage(){
//        return mBitmap;
//    }

    public DrawPenView(Context context) {
        super(context);
        initParameter(context);
    }

    public DrawPenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParameter(context);
    }

    public DrawPenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParameter(context);
    }

    private void initParameter(Context context) {
        mContext = context;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mBitmap = Bitmap.createBitmap(dm.widthPixels,dm.heightPixels, Bitmap.Config.ALPHA_8);
        mPen = new BrushPen(context);
        initPaint();
        initCanvas();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(IPenConfig.PEN_COLOR);
        mPaint.setStrokeWidth(IPenConfig.PEN_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAlpha(0xFF);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeMiter(1.0f);
        mPen.setPaint(mPaint);
    }

    private void initCanvas() {
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        switch (mCanvasCode){
            case IPenConfig.BRUSH_PEN:
            case IPenConfig.MARKING_PEN:
                mPen.draw(canvas);
                break;
            case IPenConfig.CLEAR_CANVAS:
                reset();
                break;
            default:
                Log.e(TAG,"onDraw"+Integer.toString(mCanvasCode));
                break;
        }
        super.onDraw(canvas);
    }

    public void setCanvasCode(int canvasCode){
        mCanvasCode = canvasCode;
        switch (mCanvasCode){
            case IPenConfig.BRUSH_PEN:
                mPen = new BrushPen(mContext);
                break;
            case IPenConfig.MARKING_PEN:
                mPen = new MarkingPen(mContext);
                break;
        }
        if (mPen.isNull()){
            mPen.setPaint(mPaint);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event1){
        mIsCanvasDraw = true;
        MotionEvent event2 = MotionEvent.obtain(event1);
        mPen.onTouchEvent(event2,mCanvas);
        switch (event2.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if (mGetTimeListner!=null)
                    mGetTimeListner.stopTime();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mGetTimeListner!=null)
                    mGetTimeListner.stopTime();
                break;
            case MotionEvent.ACTION_UP:
                long time = System.currentTimeMillis();
                if (mGetTimeListner!=null)
                    mGetTimeListner.getTime(time);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public TimeListener mGetTimeListner;
    public interface TimeListener{
        void getTime(long l);
        void stopTime();
    }

//    public void setGetTimeListner(TimeListener l){
//        mGetTimeListner = l;
//    }
//
//    public boolean getHasDraw(){
//        return mIsCanvasDraw;
//    }
//
//    public Bitmap getmBitmap(){
//        return mBitmap;
//    }
//
//    public void setmPenConfig(int penconfig){
//        mPenConfig = penconfig;
//    }

    public void reset(){
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(null);
        mIsCanvasDraw = false;
        mPen.clear();
        mCanvasCode = mPenConfig;
    }

    private int mBackColor = Color.TRANSPARENT;
//    public Bitmap clearBlank(int blank){
//        if (mBitmap!=null){
//            int HEIGHT = mBitmap.getHeight();
//            int WIDTH = mBitmap.getWidth();
//            int top=0,left=0,right=0,bottom=0;
//            int[] pixs = new int[WIDTH];
//            boolean isStop;
//            for (int y=0;y<HEIGHT;y++){
//                mBitmap.getPixels(pixs,0,WIDTH,0,y,WIDTH,1);
//                isStop = false;
//                for (int pix:pixs){
//                    if (pix!=mBackColor){
//                        top=y;
//                        isStop=true;
//                        break;
//                    }
//                }
//                if (isStop){
//                    break;
//                }
//            }
//            for (int y=HEIGHT-1;y>=0;y--){
//                mBitmap.getPixels(pixs,0,WIDTH,0,y,WIDTH,1);
//                isStop = false;
//                for (int pix:pixs){
//                    if(pix!=mBackColor){
//                        bottom=y;
//                        isStop=true;
//                        break;
//                    }
//                }
//                if (isStop){
//                    break;
//                }
//            }
//            pixs=new int[HEIGHT];
//            for (int x=0;x<WIDTH;x++){
//                mBitmap.getPixels(pixs,0,1,x,0,1,HEIGHT);
//                isStop=false;
//                for (int pix:pixs){
//                    if (pix!=mBackColor){
//                        left=x;
//                        isStop=true;
//                        break;
//                    }
//                }
//                if (isStop){
//                    break;
//                }
//            }
//            for (int x=WIDTH-1;x>0;x--){
//                mBitmap.getPixels(pixs,0,1,x,0,1,HEIGHT);
//                isStop=false;
//                for (int pix:pixs){
//                    if(pix!=mBackColor){
//                        right=x;
//                        isStop=true;
//                        break;
//                    }
//                }
//                if (isStop){
//                    break;
//                }
//            }
//            if (blank<0){
//                blank=0;
//            }
//            left=left-blank>0?left-blank:0;
//            top=top-blank>0?top-blank:0;
//            right=right+blank>WIDTH-1?WIDTH-1:right+blank;
//            bottom=bottom+blank>HEIGHT-1?HEIGHT-1:bottom+blank;
//            return Bitmap.createBitmap(mBitmap,left,top,right-left,bottom-top);
//        }else{
//            return null;
//        }
//    }
}
