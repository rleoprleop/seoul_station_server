package com.capstone.capstone.service.Game;

import lombok.*;

@Getter
@Setter
@ToString
public class Vel {
    private boolean lookingRight;// = true;

    // 공격하고 있는지 여부
    private boolean attacking;// = false;
    private boolean attacking_motion;// = false;

    //움직이고 있는지 여부
    private boolean moving;// = false;
    private boolean movingRight;// = false;
    private boolean movingLeft;// = false;

    //방어 하고있는지 여부
    private boolean blocking;// = false;
    //상호 작용 관련 수행 여부 (잡기 풀기 등등...)
    private boolean interaction;
    public Vel(){
        lookingRight=true;
        attacking =false;
        attacking_motion =false;
        moving =false;
        movingRight =false;
        movingLeft =false;
        blocking =false;
        interaction = false;
    }
}
