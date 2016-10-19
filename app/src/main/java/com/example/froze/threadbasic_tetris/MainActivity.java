package com.example.froze.threadbasic_tetris;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout ground;
    Button btnUp,btnDown,btnLeft,btnRight;

    int deviceWidth = 0;
    int deviceHeight = 0;

    int block_pixel_unit = 0;
    private static final int WIDTH_MAX_COUNT = 23;

    Message msg;
    Stage stage;
    public static int blockSettingFlag=0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Stage.REFRESH:
                    Log.i("Tetris", "MainActivity : REFRESH 도착");
                    stage.invalidate();
                    break;
                case Stage.NEWBLOCK:
                    Log.i("Tetris", "MainActivity : NEWBLOCK 도착");
                    stage.setBlock(handler);
                    stage.invalidate();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
        block_pixel_unit = deviceWidth / WIDTH_MAX_COUNT;
        stage = new Stage(this, handler, block_pixel_unit);

        ground = (FrameLayout)findViewById(R.id.frameLayout);
        btnUp = (Button)findViewById(R.id.buttonUp);
        btnDown = (Button)findViewById(R.id.buttonDown);
        btnRight = (Button)findViewById(R.id.buttonRight);
        btnLeft = (Button)findViewById(R.id.buttonLeft);

        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        // stageMap 배열에 Stage 객체에 정의된 배열값을 세팅한다
        // 뷰 객체를 생성한다

        ground.addView(stage);
        stage.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonUp    :
                //ground.postInvalidate();
                break;    // 위로가면 unit 만큼 y좌표 감소
           // case R.id.buttonDown  : player_y = player_y + 1; break;
            case R.id.buttonLeft  :
                stage.moveLeft();
                break;
            case R.id.buttonRight :
                stage.moveRight();
                break;
            case R.id.buttonDown :
                stage.moveDown();
                break;
            }

        }

}
