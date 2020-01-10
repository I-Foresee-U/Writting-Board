package com.example.i_for.writtingboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class SecondDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBrushPen;
    private Button mBtnMarkingPen;
    private Button mBtnClearCanvas;
    private Button mBtnSaveImage;
    private DrawPenView mDrawPenView;

    //private Bitmap mBitmap;
    private Bitmap bmp;

//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_demo);
        findViews();
        doSomeThing();
    }

    private void doSomeThing() {
        //mBitmap = mDrawPenView.getImage();
        mBtnBrushPen.setOnClickListener(this);
        mBtnMarkingPen.setOnClickListener(this);
        mBtnClearCanvas.setOnClickListener(this);
        mBtnSaveImage.setOnClickListener(this);
    }

    private void findViews() {
        mBtnBrushPen = (Button) findViewById(R.id.btn_brush_pen2);
        mBtnMarkingPen = (Button) findViewById(R.id.btn_marking_pen2);
        mBtnClearCanvas = (Button) findViewById(R.id.btn_clear_canvas2);
        mDrawPenView = (DrawPenView) findViewById(R.id.draw_pen_view2);
        mBtnSaveImage = (Button) findViewById(R.id.btn_save_image2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_brush_pen2:
                Toast.makeText(this,"You chose the brush pen.",Toast.LENGTH_SHORT).show();
                mDrawPenView.setCanvasCode(IPenConfig.BRUSH_PEN);
                break;
            case R.id.btn_marking_pen2:
                Toast.makeText(this,"You chose the marking pen.",Toast.LENGTH_SHORT).show();
                mDrawPenView.setCanvasCode(IPenConfig.MARKING_PEN);
                break;
            case R.id.btn_clear_canvas2:
                Toast.makeText(this,"Please choose a pen style again.",Toast.LENGTH_SHORT).show();
                mDrawPenView.setCanvasCode(IPenConfig.CLEAR_CANVAS);
                break;
            case R.id.btn_save_image2:
//                try {
//                    int permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
//                    if (permission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                String root = Environment.getExternalStorageDirectory() + File.separator + "jia1080hei.png";
                bmp = BitmapFactory.decodeFile(root);

                //ContextCompat.getDrawable(this,R.drawable.im361hui);
//
//                Resources res = getResources();
//                bmp = BitmapFactory.decodeResource(res,R.drawable.im361hei);

                Comparation ie = new Comparation();
                ie.outputpixels(DrawPenView.mBitmap, bmp);
                Intent intent = new Intent(SecondDemoActivity.this, ScoreActivity.class);
                intent.putExtra("et1", ie.score0);
                intent.putExtra("et2", ie.score3);
                intent.putExtra("et3",ie.level);
                startActivity(intent);
                break;
        }
    }
}
