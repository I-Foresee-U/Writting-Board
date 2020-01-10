package com.example.i_for.writtingboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBack;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private String s1;
    private String s2;
    private String s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Bundle bundle = getIntent().getExtras();
        s1 = bundle.getString("et1");
        s2 = bundle.getString("et2");
        s3 = bundle.getString("et3");
        findViews();
        doSomeThing();
    }

    private void doSomeThing() {
        mBtnBack.setOnClickListener(this);
    }

    private void findViews() {
        mBtnBack = findViewById(R.id.btn_back);
        text1 = findViewById(R.id.score0);
        //text1.setText("Filling Rate: "+String.valueOf(s1));
        text1.setText("Filling Rate: "+s1);
        text2 = findViewById(R.id.score3);
        text2.setText("Synthesis Score\n"+s2);
        text3 = findViewById(R.id.scoretips);
        switch (s3) {
            case "1":
                text3.setText("       Excellant! You just did a good job! ......");
                break;
            case "2":
                text3.setText("       OK, not bad, but I believe you can do it better. ......");
                break;
            case "3":
                text3.setText("       Oh! Please be more careful. You need more practice. ......");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
