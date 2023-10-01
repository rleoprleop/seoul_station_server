package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Player extends Creature{
    private String name;
    private int frameCount;// = 0;
    private int damagedCount;// = 0;
    private int refreshRate;// = 10;
    private int attackTimer;// = 0;
    private int attackFrame;// = 0;
    private int damagedLoop;// = 0;
    private BlockBox blockBox;
    public Player(String name, int x, int y, int width, int height, int canvasLength, int healthMax){
        super(x,y,width,height,canvasLength, healthMax);
        this.name=name;
        frameCount=0;
        damagedCount=0;
        refreshRate=10;
        attackTimer=0;
        attackFrame=0;
        damagedLoop=0;
        blockBox=new BlockBox(x+canvasLength/2, x+30,y);
    }

    public void addFrameCount(int i){
        frameCount+=i;
    }

    public void addAttackTimer(int i) {
        attackTimer+=i;
    }

    public void addAttackFrame(int i) {
        attackFrame+=i;
    }
}
