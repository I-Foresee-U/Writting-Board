package com.example.i_for.writtingboard;

import android.graphics.Color;

public interface IPenConfig {

    int CLEAR_CANVAS = 0;
    int BRUSH_PEN = 1;
    int MARKING_PEN = 2;
    int PEN_WIDTH = 80;
    int PEN_COLOR = Color.parseColor("#000000");

    //这个控制笔锋的控制值
    float STROKE_FACTOR = 0.02f;
    //手指在移动的控制笔的变化率  这个值越大，线条的粗细越加明显
    float VELOCITY_FACTOR = 40f;
    //绘制计算的次数，数值越小计算的次数越多，需要折中
    int STEP_FACTOR = 10;

}
