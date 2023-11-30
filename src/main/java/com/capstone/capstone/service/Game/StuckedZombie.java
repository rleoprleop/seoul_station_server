package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StuckedZombie {
    private int x;
    private int y;
    private int width;
    private int height;
    private int canvasLength;
    private int attackLoop;
    private int attackCount;
    private int attackCut;
    private int stageNum;
    private boolean stunned;
    private int stunCount;
    private int stunCut;
    private boolean dead;

    private int waitCount;
    private int deathCount;
    private int deathCut;
    private int healthCount;
    private boolean hitCheck;
    public StuckedZombie(int x, int y, int width, int height, int canvasLength) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvasLength = canvasLength;

        this.attackLoop = 3;
        this.attackCount = 0;
        this.attackCut = 0;

        this.stageNum = 0;

        this.stunned = false;
        this.stunCount = 0;
        this.stunCut = 0;
        this.dead = false;

        this.waitCount = 0;

        this.deathCount = 0;
        this.deathCut = 0;

        this.healthCount = 1;
        this.hitCheck = false;
    }

    public void attack(int[] collisonCheckX, Player p1, Player p2) {
        if (this.stunned == false && this.dead == false) {
            this.checkStunned(p1, p2);
            if (this.attackCount < 20 && this.attackCut < 2) {
                this.attackCount++;
            }
            else if(this.attackCount == 20) {
                this.attackCount = 0;
                this.attackCut++;
            }
            if (this.attackCut == 2) {
                if (this.waitCount < 30) {
                    this.waitCount++;
                }
                else if (this.waitCount == 30) {
                    this.waitCount = 0;
                    this.attackCut = 0;
                }
            }
        }
        else if (this.stunned == true && this.dead == false) {
            this.stun();
        }
        else if (this.dead == true) {
//            for (int i = 0; i <= this.canvasLength - 100; i++) {
//                collisonCheckX[this.x + 50 + i] = -1;
//            }
            if (this.deathCount < 20 && this.deathCut < 4) {
                this.deathCount++;
            }
            else if (this.deathCount == 20) {
                this.deathCount = 0;
                this.deathCut++;
            }
        }
//        return collisonCheckX;
    }


    public void stun() {
//        this.checkAttacked(p1.getAttackTimer(), collisonCheckX);
        if (this.stunCount < 30 && this.stunCut < 2) {
            this.stunCount++;
        }
        else if (this.stunCount == 30) {
            this.stunCount = 0;
            this.stunCut++;
        }
    }

    public void checkStunned(Player p1, Player p2) {
        if (this.x - p1.getX() < 170 && p1.getVel().isBlocking() == true) {
            this.stunned = true;
        }

        else if (this.x - p2.getX() < 170 && p2.getVel().isBlocking() == true) {
            this.stunned = true;
        }
    }

    public void checkAttacked(int atkTimer_p1, int[] collisonCheckX) {//공격이 해당 물체에 가해졌는지 확인
        if ((collisonCheckX[atkTimer_p1] == 1) && (this.x <= atkTimer_p1 && atkTimer_p1 <= this.x + this.canvasLength) && this.dead == false) {
            this.healthCount--;
            this.hitCheck=true;
            if (this.healthCount == 0) {
                this.dead = true;
            }
        }
    }

    public void moveObjectRight(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
//            collisonCheckX[x + 50] = -1;
//            collisonCheckX[x + 51] = -1;
//            collisonCheckX[x + canvasLength - 49] = 1;
//            collisonCheckX[x + canvasLength - 48] = 1;
            x+=2;
        }
//        return collisonCheckX;
    }

    public void moveObjectLeft(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
//            collisonCheckX[x + 48] = 1;
//            collisonCheckX[x + 49] = 1;
//            collisonCheckX[x + canvasLength - 50] = -1;
//            collisonCheckX[x + canvasLength - 51] = -1;
            x-=2;
        }
//        return collisonCheckX;
    }
}
