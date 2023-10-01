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
    private Map<Integer,NormalZombie> normalZombieMap;//int: mob1,2,3...(1,2,3...)
    private int[] checkXMap;
    private long time;
    public Room(Map<String,Player> p, Map<Integer,NormalZombie> nz, BackGround bg, int[] cX){
        active=false;
        playerMap=p;
        normalZombieMap=nz;
        backGround=bg;
        checkXMap=cX;
        time=System.currentTimeMillis();
    }
}
