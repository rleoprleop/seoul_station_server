package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackGround {
    private int bg_length;
    private int bg_canvasLength;
    private int bg_x;
    private int bg_count;
    private int bg_xMax;
    private int ratio;
    private boolean bgMovingRight;
    private boolean bgMovingLeft;
    private int stageNum;
    public BackGround(){
        bg_length=2000;
        bg_canvasLength=1000;
        bg_x=0;
        bg_count=4;
        bg_xMax=(2000*4)-2000*2;
        ratio=2;
        bgMovingRight=false;
        bgMovingLeft=false;
        stageNum=0;
    }
    public void addBgX(int i){
        bg_x+=i;
    }
    public void subBgX(int i){
        bg_x-=i;
    }
}