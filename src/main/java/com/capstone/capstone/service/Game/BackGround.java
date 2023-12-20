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
    private int[] arr_bgImg_width= {5760, 11520, 11520, 11520, 5760, 7680, 7680};

    public BackGround(){
        bg_length=1920;
        bg_canvasLength=960;
        bg_x=0;
        bg_count=3;
        bg_xMax=(this.arr_bgImg_width[0] * 960) / 1920 - 1920;
        ratio=1920/960;
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
    public void updateBgMax(int currentStageNum){
        this.bg_xMax = (this.arr_bgImg_width[currentStageNum] * 960) / 1920 - 1920;
    }
}