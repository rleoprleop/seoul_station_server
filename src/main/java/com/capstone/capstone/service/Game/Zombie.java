package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Zombie {
    private Map<Integer,NormalZombie> normalZombieMap;//int: mob1,2,3...(1,2,3...)
    private Map<Integer,RunningZombie> runningZombieMap;
    private Map<Integer,CrawlingZombie> crawlingZombieMap;
    public Zombie(Map<Integer,NormalZombie> nz, Map<Integer,RunningZombie> rz, Map<Integer,CrawlingZombie> cz){
        normalZombieMap=nz;
        runningZombieMap=rz;
        crawlingZombieMap=cz;
    }
}
