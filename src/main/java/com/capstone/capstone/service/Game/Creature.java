package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creature {
    private int x;
    private int y;
    private int width;
    private int height;
    private int canvasLength;
    private AttackBox attackBox;
    //각 동작의 총 컷 수
    private int idleLoop;// = 0;
    private int walkingLoop;// = 0;
    private int attackLoop;// = 0;
    //각 동작의 현재 몇 번째 컷인지 알려주는 정보
    private int idleCut;// = 0;
    private int walkingCut;// = 0;
    private int attackCut;// = 0;

        //각 동작의 현재 몇 번째 프레임인지 알려주는 정보
    private int idleCount;// = 0;
    private int walkingCount;// = 0;
    private int attackCount;// = 0;


        //해당 플레이어의 키보드 입력에 영향을 받는 속성들
    private Vel vel;

    //맞았는지 여부
    private boolean damaged;// = false;
    private int damagedCount;// = 0;

    //체력
    private int healthMax;// = 3;
    private int healthCount;// = 3;// this.healthMax;

    public Creature(int x, int y, int width, int height, int canvasLength, int healthMax) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.canvasLength=canvasLength;
        vel=new Vel();
        attackBox=new AttackBox(x+canvasLength/2,y-50);
        idleLoop=0;
        walkingLoop=0;
        attackLoop=0;
        idleCut=0;
        walkingCut=0;
        attackCut=0;
        idleCount=0;
        walkingCount=0;
        attackCount=0;
        damaged =false;
        damagedCount=0;
        this.healthMax=healthMax;
        healthCount=healthMax;
    }


    public void setLocation(int x1, int y1) {
        x = x1;
        y = y1;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public void setCanvasSize(int canvasLength) {
        this.canvasLength = canvasLength;
    }

    public void setAttackBoxSize(int width, int height) {
        attackBox.setWidth(width);
        attackBox.setHeight(height);
    }

    public void setLoops(int idle, int walking, int attack) {
        idleLoop = idle;
        walkingLoop = walking;
        attackLoop = attack;
    }

    public void setCounts(int idleC, int walkingC, int attackC) {
        idleCount = idleC;
        walkingCount = walkingC;
        attackCount = attackC;
    }

    public void setHealthOfHit(int i){
        healthCount-=i;
    }
    public void addX(int i){
        x+=i;
    }
    public void subX(int i){
        x-=i;
    }
    public void addIdleCount(int i){
        idleCount+=i;
    }

    public void subIdleCount(int i){
        idleCount-=i;
    }

    public void addWalkingCount(int i) {
        walkingCount+=i;
    }

    public void subWalkingCount(int i) {
        walkingCount-=i;
    }

    public void addAttackCount(int i) {
        attackCount+=i;
    }

    public void addDamagedCount(int i) {
        damagedCount+=i;
    }

    public void addIdleCut(int i) {
        idleCut+=i;
    }

    public void addWalkingCut(int i) {
        walkingCut+=i;
    }
}