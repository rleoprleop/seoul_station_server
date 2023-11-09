package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttackBox {
    private int position_x; // this.x + this.CanvasLength / 2;
    private int position_y;// = this.y - 50;
    private int width;
    private int height;
    private int atkTimer;
    public AttackBox(int position_x, int position_y){
        this.position_x=position_x;
        this.position_y=position_y;
        width=120;
        height=50;
        atkTimer=0;
    }
    public void addAtkTimer(int i){
        atkTimer+=i;
    }

    public void subPosition_x(int i) {
        position_x-=i;
    }

    public void addPosition_x(int i) {
        position_x+=i;
    }
}
