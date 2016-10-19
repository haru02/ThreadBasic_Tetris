package com.example.froze.threadbasic_tetris;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by froze on 2016-10-18.
 */

public class Block extends Thread{

    int x = 5;
    int y = 0;

    int preview_x = 18;
    int preview_y = 1;

    int orientation_limit = 4;
    int orientation = 0;
    private int blocktype[][][];
    int tempBlock[][]=new int[4][4];
    int nextBlock[][][]=new int[2][4][4];

    boolean alive = true;
    int interval = 200;
    Handler blockhandler;
    BlockFactory blockFactory;
    Stage sg;


    public Block(Stage sg, Handler handler){
        this.sg = sg;
        this.blockhandler = handler;
        Log.i("Tetris", "Block class added");
    }

    public int[][][] getBlock(){
        //if(MainActivity.blockSettingFlag==0) {

            Log.i("Tetris", "Block setting flag = "+MainActivity.blockSettingFlag);
            for(int i = 0;i<2;i++) {
                Random random = new Random();
                int blockIndex = random.nextInt(7);
                blocktype = blockFactory.blockGroup[blockIndex];
                nextBlock[i] = blocktype[orientation];
            }
      /*      MainActivity.blockSettingFlag=1;
        }else{
            Log.i("Tetris", "Block setting flag = "+MainActivity.blockSettingFlag);
            tempBlock = nextBlock[1].clone();
            Random random = new Random();
            int blockIndex = random.nextInt(7);
            blocktype = blockFactory.blockGroup[blockIndex];
            nextBlock[0]=tempBlock;
            nextBlock[1] = blocktype[orientation];
        }*/
        return nextBlock;
    }

    // 회전
    public void rotate(){
        orientation++;
        orientation = orientation%orientation_limit;
    }

    // 생성되고 화면에 세팅되면
    public void run(){
        while(y<20) {
            y++;
            blockhandler.sendEmptyMessage(Stage.REFRESH);
            Log.i("Tetris", "Block : "+y);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        blockhandler.sendEmptyMessage(Stage.NEWBLOCK);
    }


    public boolean collisionTest(){ //충돌하면 true를 반환한다
        boolean result = false;
        for(int i=0 ;i < 4 ; i++){
            for(int j=0;j < 4 ; j++){
                int cBlockValue = nextBlock[0][j][i];
                int sum = 0;
                if( cBlockValue > 0){
                    sum= cBlockValue + Stage.stageMap[y+j][x+i];
                    if(sum > cBlockValue){ // 두개 셀을 더한값이 블럭셀의 값보다 크면 충돌
                        return true;
                    }
                }
            }
        }
        return result;
    }

}
