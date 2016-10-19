package com.example.froze.threadbasic_tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by froze on 2016-10-17.
 */

public class Stage extends View {
    public static final int REFRESH = 1;
    public static final int NEWBLOCK =2;
    Paint paint[] = new Paint[10];

    Handler stageHandler;

    int unit = 0;
    final int stageWidth = 14;
    final int stageHeight = 21;
    final int stageTop = 1;
    final int stageLeft = 1;

    final int blockWidth = 4;
    final int blockHeight = 4;

    final int previewWidth = 6;
    final int previewHeight = 6;
    final int previewTop = 1;
    final int previewLeft = 16;

    Block block;
    int[][] cblock = new int[4][4];
    int[][] cNextBlock = new int[4][4];
    static int[][] stageMap = null;

    public Stage(Context context, Handler handler, int unit) {
        super(context);

        stageHandler = handler;
        this.unit = unit;

        setBlock(stageHandler);
        setStage();

        for(int i=0;i<paint.length;i++){
            paint[i] = new Paint();
        }

        paint[0].setColor(getResources().getColor(R.color.background));
        paint[1].setColor(getResources().getColor(R.color.block1));
        paint[2].setColor(getResources().getColor(R.color.block2));
        paint[3].setColor(getResources().getColor(R.color.block3));
        paint[4].setColor(getResources().getColor(R.color.block4));
        paint[5].setColor(getResources().getColor(R.color.block5));
        paint[6].setColor(getResources().getColor(R.color.block6));
        paint[7].setColor(getResources().getColor(R.color.block7));
        paint[9].setColor(getResources().getColor(R.color.border));
    }
    public void setBlock(Handler handler){
        block = new Block(this, handler);
        cNextBlock = block.getBlock()[1];
        cblock = block.getBlock()[0];
        block.start();
    }

    public void setStage(){
        stageMap = stageOne.clone();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1. 스테이지 영역을 모두 그린다
        // 1.1 스테이지 배열에 해당 블럭이 포함된채로 그림을 그린다

        for(int i=0;i<stageWidth;i++){
            for(int j=0;j<stageHeight;j++){
                canvas.drawRect(
                        (stageLeft + i) * unit
                        ,(stageTop + j) * unit
                        ,(stageLeft + i) * unit + unit
                        ,(stageTop + j) * unit + unit
                        , paint[stageOne[j][i]]
                );
            }
        }

        // 2. 프리뷰 영역을 그린다
        for(int i=0;i<previewWidth;i++){
            for(int j=0;j<previewHeight;j++){
                canvas.drawRect(
                        (previewLeft + i) * unit
                        ,(previewTop + j) * unit
                        ,(previewLeft + i) * unit + unit
                        ,(previewTop + j) * unit + unit
                        , paint[previewMap[j][i]]
                );
            }
        }
        // 2.1 프리뷰 영역의 다름 블럭을 그린다
        for(int i=0;i<blockWidth;i++){
            for(int j=0;j<blockHeight;j++){
                if(cNextBlock[j][i]!=0) {
                    canvas.drawRect(
                            (block.preview_x + i) * unit
                            , (block.preview_y + j) * unit
                            , (block.preview_x + i) * unit + unit
                            , (block.preview_y + j) * unit + unit
                            , paint[cNextBlock[j][i]]
                    );
                }
            }
        }


        // 2.1 다음에 보여줄 블럭과 함께 그린다
        // 스테이지를 그린다

        Log.i("Tetris", "Stage : x = "+block.x +"y = "+block.y);
        for(int i=0;i<blockWidth;i++){
            for(int j=0;j<blockHeight;j++){
                if(cblock[j][i]!=0) {
                    canvas.drawRect(
                            (block.x + i) * unit
                            , (block.y + j) * unit
                            , (block.x + i) * unit + unit
                            , (block.y + j) * unit + unit
                            , paint[cblock[j][i]]
                    );
                }
            }
        }
    }

    public void moveLeft(){
        block.x--;
        if(!block.collisionTest()) { //충돌하면 true를 반환한다
            invalidate();
        }else{
            block.x++;
        };
    }

    public void moveRight(){
        block.x++;
        if(!block.collisionTest()) { //충돌하면 true를 반환한다

        }else{
            block.x--;
        };
    }

    public void moveDown(){
        block.y--;
        if(!block.collisionTest()) { //충돌하면 true를 반환한다

        }else{
            block.y++;
        };
    }



    int previewMap[][]={
            {9,0,0,0,0,9},
            {9,0,0,0,0,9},
            {9,0,0,0,0,9},
            {9,0,0,0,0,9},
            {9,0,0,0,0,9},
            {9,9,9,9,9,9},
    };
    static int stageOne[][] = {
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,0,0,9},
            {9,9,9,9,9,9,9,9,9,9,9,9,9,9}
    };

}
