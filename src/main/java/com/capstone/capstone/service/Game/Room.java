package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Room {
    private boolean active;
    private Map<String, Player> playerMap;//string: userId(player)
    private BackGround backGround;
    private Zombie zombie;
    private int[] checkXMap;
    private int currentStageNum;
    private long time;
    public Room(Map<String,Player> p, Zombie z, BackGround bg, int[] cX){
        active=false;
        playerMap=p;
        zombie=z;
        backGround=bg;
        checkXMap=cX;
        time=System.currentTimeMillis();
        currentStageNum=0;
    }
    public void addCurrentStageNum(int i){
        currentStageNum+=i;
    }
}
