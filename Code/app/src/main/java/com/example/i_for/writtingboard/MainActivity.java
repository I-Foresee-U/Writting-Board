package com.example.i_for.writtingboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnShi;
    private Button mBtnJia;
    private Button mBtnFu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        doSomeThing();
    }

    private void doSomeThing() {
        mBtnShi.setOnClickListener(this);
        mBtnJia.setOnClickListener(this);
        mBtnFu.setOnClickListener(this);
    }

    private void findViews() {
        mBtnShi = findViewById(R.id.btn_shi);
        mBtnJia = findViewById(R.id.btn_jia);
        mBtnFu = findViewById(R.id.btn_fu);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.btn_shi:
                intent = new Intent(MainActivity.this,DemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_jia:
                intent = new Intent(MainActivity.this,SecondDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_fu:
                intent = new Intent(MainActivity.this,ThirdDemoActivity.class);
                startActivity(intent);
                break;
        }

    }
}
