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
    private boolean grabbed;
    private int interactionLoop;
    private int interactionCut;
    private int interactionCount;
    public Player(String name, int x, int y, int width, int height, int canvasLength, int healthMax){
        super(x,y,width,height,canvasLength, healthMax);
        this.name=name;
        frameCount=0;
        damagedCount=0;
        refreshRate=10;
        attackTimer=0;
        attackFrame=0;
        damagedLoop=0;
        interactionLoop=3;
        interactionCut=0;
        interactionCount=0;
        blockBox=new BlockBox(x+canvasLength - 10, x+10,y+60);
        grabbed = false;
    }

    public void checkIsDead() {
        if (this.getHealthCount() <= 0) {
            this.setDead(true);
        }
        else {
            this.setDead(false);
        }
    }    public void addFrameCount(int i){
        frameCount+=i;
    }

    public void addAttackTimer(int i) {
        attackTimer+=i;
    }

    public void addAttackFrame(int i) {
        attackFrame+=i;
    }

    public void addDamagedCount(int i) { damagedCount+=i; }

    public void addInteractionCut(int i) {
        interactionCut+=i;
    }

    public void addInteractionCount(int i) {
        interactionCount+=i;
    }

    public void initPlayerPoint(int x, int canvasLength){
        setX(x);
        getBlockBox().setX_right(x+canvasLength-10);
        getBlockBox().setX_left(x+10);
        getAttackBox().setPosition_x(x+canvasLength/2);

    }
    public void setPositionX(int x, int canvasLength){
        getAttackBox().setPosition_x(x+canvasLength/2);
    }
    public void moveObjectRight(int[] collisonCheckX) {
        collisonCheckX[getX() + 50] = -1;
        collisonCheckX[getX() + 51] = -1;
        collisonCheckX[getX() + getCanvasLength() - 49] = 0;
        collisonCheckX[getX() + getCanvasLength() - 48] = 0;
        addX(2);
    }

    public void moveObjectLeft(int[] collisonCheckX) {
        collisonCheckX[getX() + 48] = 0;
        collisonCheckX[getX() + 49] = 0;
        collisonCheckX[getX() + getCanvasLength() - 50] = -1;
        collisonCheckX[getX() + getCanvasLength() - 51] = -1;
        subX(2);
    }

    public void subAttackTimer(int i) {
        attackTimer-=i;
    }
}
