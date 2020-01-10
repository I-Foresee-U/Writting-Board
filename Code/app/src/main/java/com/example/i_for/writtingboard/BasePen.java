package com.example.i_for.writtingboard;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class BasePen {

    public abstract void draw(Canvas canvas);

    public boolean onTouchEvent(MotionEvent event, Canvas canvas){
        return false;
    }

}
