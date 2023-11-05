package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class NormalZombie extends Creature{
    private int move_range;
    private int move_randNum;
    private int moveCount;
    private int speed;
    int xMax_left;
    int xMax_right;
    private int x_detectLeft;
    private int x_detectRight;
    private int x_attackLeft;
    private int x_attackRight;
    private boolean movingDone;
    private boolean dead;
    private int attackFrame;
    private boolean stunned;
    private int stunCount;
    private int stunAnimaitonCount;
    private int stunLoop;
    private int deathLoop;
    private int waitCount;
    private int deathCount;
    private int deathFrame;
    private int stageNum;
    private int attackRandomNum;
    private boolean attackDone;
    private int deathCut;

    public NormalZombie(int x, int y, int widgh, int height, int canvasLength,int healthMax){
        super(x,y,widgh,height,canvasLength,healthMax);
        move_range = 100; // 몹이 무작위로 움직이는 최대 범위
        move_randNum = 0; // 몹이 무작위로 움직이는 범위
        moveCount = 0;
        speed = 1;        // 몹 움직이는 속도
        xMax_left = 0;
        xMax_right = 0;
        x_detectLeft = x - 150; //몹이 왼쪽에서 플레이어를 감지 할 수 있는 범위
        x_detectRight = x + canvasLength + 150; //몹이 오른쪽에서 플레이어를 감지 할 수 있는 범위
        x_attackLeft = x + 30; //몹이 왼쪽에서 플레이어를 공격 할 수 있는 범위
        x_attackRight = x + canvasLength - 30;
        movingDone = true;
        dead = false;
        attackFrame=0;
        stunned = false;
        stunCount = 0;
        stunAnimaitonCount = 0;
        stunLoop = 0;
        waitCount = 0;
        deathCount = 0;
        deathLoop = 0;
        deathCut = 0;
        deathFrame = 0;
        stageNum = 1;
        attackRandomNum = 0;
        attackDone = true;
    }
    public void setFixedRange(int xMax_left, int xMax_right) {
        this.xMax_left = xMax_left;
        this.xMax_right = xMax_right;
    }

    public void comeBackToPosition(int[] collisonCheckX) {
        getVel().setMoving(true);
        if(getX() < (xMax_left + xMax_right) / 2) { //왼쪽으로 벗어난 경우
            if (getX() != (xMax_left + xMax_right) / 2) { //가운데로 올 때까지 이동
                getVel().setLookingRight(true);
                collisonCheckX[getX() + 50] = -1;
                collisonCheckX[getX() + getCanvasLength() - 49] = 1;
                addX(1);
            }
        }
        else if ((xMax_left + xMax_right) / 2 < getX()) {  // 오른쪽으로 벗어난 경우
            if (getX() != (xMax_left + xMax_right) / 2) { //가운데로 올 때까지 이동

                getVel().setLookingRight(false);
                collisonCheckX[getX() + 49] = 1;
                collisonCheckX[getX() + getCanvasLength() - 50] = -1;
                subX(1);
            }
        }
    }

    public void stun() {
        getVel().setMoving(false);
        if (stunCount < 120) {
            stunCount++;
        }
        else {
            stunned = false;
            stunCount = 0;
        }
    }

    public void zombieAttack(Player p1, Player p2, int[] collisonCheckX) {
        getVel().setMoving(false);
        if(attackRandomNum>=6){
            if (getVel().isLookingRight()) { // 오른쪽 보고있는 경우
                if (getAttackBox().getAtkTimer() <= getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격범위 -> 100, 프레임당 2. 50프레임 소모
                    attackDone=false;
                    //공격 상자 늘리기 전에 플레이어들의 방어 확인
                    if (p1.getVel().isBlocking() && !p1.getVel().isLookingRight() &&(getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() + 6) >= p1.getBlockBox().getX_left()) {
                        // 플레이어1의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        stunned = true;
                        getVel().setAttacking(false);
                        getAttackBox().setAtkTimer(0);
                        attackDone=true;
                    }

                    if (p2.getVel().isBlocking() && !p2.getVel().isLookingRight() &&(getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() + 6) >= p2.getBlockBox().getX_left()) {
                        //플레이어2의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        stunned = true;
                        getVel().setAttacking(false);
                        getAttackBox().setAtkTimer(0);
                        attackDone=true;
                    }
                    else {
                        if (this.waitCount < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.waitCount++;
                        }

                        else if (this.waitCount == 30) {
                            if(getAttackCount()>=2){
                                getAttackBox().addOfAttackTimer(6);
                            }
                        }


                        if (collisonCheckX[getAttackBox().getPosition_x() + getAttackBox().getAtkTimer()] == 0) { //공격이 플레이어에게 닿은 경우
                            //어느 플레이어에 닿았는지 확인해야 함
                            if (p1.getX() < getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() && getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                                // 플레이어 1에 공격이 닿았을 경우
                                p1.setDamaged(true);
                            }

                            if (p2.getX() < getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() && getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                                // 플레이어 2에 공격이 닿았을 경우
                                p2.setDamaged(true);
                            }
                        }
                    }
                }

                else { //공격 종료
                    attackDone=true;
                    if (p1.isDamaged()) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }

                    if (p2.isDamaged()) {
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    waitCount = 0;
                    getAttackBox().setAtkTimer(0);
                    getVel().setAttacking(false);
                    attackRandomNum = (int) Math.floor(Math.random() * 10);
                }
            }

            else { //왼쪽을 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //왼쪽 공격 진행중
                    attackDone=false;
                    //공격 상자 늘리기 전에 플레이어의 방어 확인
                    if (p1.getVel().isBlocking() && p1.getVel().isLookingRight() && (getAttackBox().getPosition_x() - getAttackBox().getAtkTimer() - 6) <= p1.getBlockBox().getX_right()) {
                        // 플레이어1의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        stunned = true;
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        attackDone=true;
                    }
                    if (p2.getVel().isBlocking() && p2.getVel().isLookingRight() && (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() - 6) <= p2.getBlockBox().getX_right()) {
                        // 플레이어2의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        stunned = true;
                        getVel().setAttacking(false);
                        getAttackBox().setAtkTimer(0);
                        attackDone=true;
                    }
                    else {
                        if (this.waitCount < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.waitCount++;
                        }

                        else if (this.waitCount == 30) {
                            if(getAttackCount()>=2){
                                getAttackBox().addOfAttackTimer(6);
                            }
                        }

                        if (collisonCheckX[getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()] == 0) {//공격이 플레이어에게 닿은 경우
                            //어느 플레이어에 공격이 닿았는지 확인 해야함
                            if (p1.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                                // 플레이어 1에 공격이 닿았을 경우
                                p1.setDamaged(true);
                            }

                            if (p2.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                                // 플레이어 2에 공격이 닿았을 경우
                                p2.setDamaged(true);
                            }
                        }
                    }
                }

                else { //공격 종료
                    attackDone=true;
                    if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }
                    if (p2.isDamaged() == true) { //플레이어2가 해당 몬스터의 공격을 받았을 경우
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    this.waitCount = 0;
                    this.getAttackBox().setAtkTimer(0);
                    this.getVel().setAttacking(false);
                    attackRandomNum = (int) (Math.random() * 10);
                }
            }
        }
        else if(attackRandomNum>=0){ // 5, 4, 3, 2, 1, 0 -> 방어 불가 공격
            if (getVel().isLookingRight()) { // 오른쪽 보고있는 경우
                if (getAttackBox().getAtkTimer() <= getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격범위 -> 100, 프레임당 2. 50프레임 소모
                    attackDone=false;
                    if (this.waitCount < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                        this.waitCount++;
                    }

                    else if (this.waitCount == 30) {
                        if(getAttackCount()>=2){
                            getAttackBox().addOfAttackTimer(6);
                        }
                    }


                    if (collisonCheckX[getAttackBox().getPosition_x() + getAttackBox().getAtkTimer()] == 0) { //공격이 플레이어에게 닿은 경우
                        //어느 플레이어에 닿았는지 확인해야 함
                        if (p1.getX() < getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() && getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                            // 플레이어 1에 공격이 닿았을 경우
                            p1.setDamaged(true);
                        }

                        if (p2.getX() < getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() && getAttackBox().getPosition_x() + getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                            // 플레이어 2에 공격이 닿았을 경우
                            p2.setDamaged(true);
                        }
                    }

                }

                else { //공격 종료
                    attackDone=true;
                    if (p1.isDamaged()) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }

                    if (p2.isDamaged()) {
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    waitCount = 0;
                    getAttackBox().setAtkTimer(0);
                    getVel().setAttacking(false);
                    attackRandomNum = (int) Math.floor(Math.random() * 10);
                }
            }

            else { //왼쪽을 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //왼쪽 공격 진행중
                    attackDone=false;
                    if (this.waitCount < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                        this.waitCount++;
                    }

                    else if (this.waitCount == 30) {
                        if(getAttackCount()>=2){
                            getAttackBox().addOfAttackTimer(6);
                        }
                    }

                    if (collisonCheckX[getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()] == 0) {//공격이 플레이어에게 닿은 경우
                        //어느 플레이어에 공격이 닿았는지 확인 해야함
                        if (p1.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                            // 플레이어 1에 공격이 닿았을 경우
                            p1.setDamaged(true);
                        }

                        if (p2.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                            // 플레이어 2에 공격이 닿았을 경우
                            p2.setDamaged(true);
                        }
                    }
                }

                else { //공격 종료
                    attackDone=true;
                    if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }
                    if (p2.isDamaged() == true) { //플레이어2가 해당 몬스터의 공격을 받았을 경우
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    this.waitCount = 0;
                    this.getAttackBox().setAtkTimer(0);
                    this.getVel().setAttacking(false);
                    attackRandomNum = (int) Math.floor(Math.random() * 10);
                }
            }
        }

    }

    public void move(int bigX, int smallX, int[] collisonCheckX, int currentStageNum) {

        //몹의 공격 범위 갱신
        this.x_detectLeft = this.getX() - 150;
        this.x_detectRight = this.getX() + this.getCanvasLength() + 150;

        this.x_attackLeft = this.getX() + 10;
        this.x_attackRight = this.getX() + this.getCanvasLength() - 10;

        this.getAttackBox().setPosition_x(this.getX() + this.getCanvasLength() / 2);

        if (this.stunned) { //공격이 막혀 잠시 스턴에 걸린 경우
            this.stun();
        }


        if (this.dead == false && this.getVel().isAttacking() == false && this.stunned == false && this.stageNum == currentStageNum) {
            for (int i = 0; i <= this.getCanvasLength() - 100; i++) {
                collisonCheckX[this.getX() + 50 + i] = 1;
            }


            // 플레이어가 탐지 범위 안에 들어온 경우
            if((this.x_detectLeft <= bigX && bigX <= this.getX() + 50) || (this.getX() + this.getCanvasLength() - 50 <= smallX && smallX <= this.x_detectRight)) {
                //플레이어가 공격 범위 안에 들어온 경우
                if ((this.x_attackLeft <= bigX && bigX <= this.getX() + 50) || (this.getX() + this.getCanvasLength() - 50 <= smallX && smallX <= this.x_attackRight)) {
                    if (this.x_attackLeft <= bigX && bigX <= this.getX() + 100) { // 왼쪽 방향으로 감지 했을 경우
                        this.getVel().setLookingRight(false);
                    }
                    else { //오른쪽으로 감지 했을 경우
                        this.getVel().setLookingRight(true);
                    }
                    this.getVel().setAttacking(true);
                }

                else { //탐지 범위 안에 들어왔지만 공격 범위는 아닌 경우 -> 플레이어 따라가기
                    if (this.x_detectLeft < bigX && bigX < this.x_attackLeft) { //왼쪽으로 이동
                        this.getVel().setMoving(true);
                        this.getVel().setLookingRight(false);
                        collisonCheckX[this.getX() + 49] = 1;
                        collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
                        this.subX(1);
                    }

                    else if (this.x_attackRight < smallX && smallX <= this.x_detectRight) { //오른쪽으로 이동
                        this.getVel().setMoving(true);
                        this.getVel().setLookingRight(true);
                        collisonCheckX[this.getX() + 50] = -1;
                        collisonCheckX[this.getX() + this.getCanvasLength() - 49] = 1;
                        this.addX(1);
                    }
                }
            }

            else if((this.getX() + 50 < this.xMax_left) || (this.xMax_right < this.getX() + this.getCanvasLength() - 40)) {//지정된 구역을 벗어난 경우
                comeBackToPosition(collisonCheckX);
            }

            else { // 탐지가 된것도 아니고, 지정된 구역을 벗어난 경우도 아닌 경우 -> 일반 무작위 움직임
                if (this.movingDone) { // 움직임이 끝난 상태일 때
                    if (this.moveCount < 90) {// 1.5초 동안 잠시 멈췄다가
                        this.getVel().setMoving(false);
                        this.moveCount++;
                    }

                    else { // 다시 움직임 재개
                        this.moveCount = 0;
                        this.move_randNum = (int) Math.floor(Math.random() * this.move_range);
                        // floor -> 정수로 반올림, random -> 0~1사이 난수 발생 여기선 move_range만큼 곱해줌

                        this.movingDone = false;
                    }

                }

                else { //움직임이 끝나지 않았을 때
                    if (this.move_randNum <= 10 && this.moveCount < this.move_randNum) { //난수가 일정 수보다 작으면 가만히 서 있는 걸로
                        this.getVel().setMoving(false);
                        this.moveCount+=this.speed;
                    }

                    else { //움직이는 경우

                        if ((this.move_randNum % 2 == 0) && this.moveCount < this.move_randNum) { //짝수인 경우 -> 오른쪽으로 이동
                            if (this.getX() + this.getCanvasLength() + this.speed <= this.xMax_right) { //고정 범위 안에 있는 경우
                                this.getVel().setMoving(true);
                                collisonCheckX[this.getX() + 50] = -1;
                                collisonCheckX[this.getX() + this.getCanvasLength() -49] = 1;
                                this.getVel().setLookingRight(true);
                                this.addX(speed);
                                this.moveCount+=this.speed;
                            }
                            else { // 고정 범위 끝까지 간 경우 -> 움직임 마쳤다고 판단
                                this.getVel().setMoving(false);
                                this.movingDone = true;
                            }

                        }
                        else if ((this.move_randNum % 2 == 1) && this.moveCount < this.move_randNum) {//홀수인 경우 -> 왼쪽으로 이동
                            if (this.getX() - this.speed >= this.xMax_left) { //고정 범위 안에 있는 경우
                                this.getVel().setMoving(true);
                                collisonCheckX[this.getX() + 49] = 1;
                                collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
                                this.getVel().setLookingRight(false);
                                this.subX(speed);
                                this.moveCount+=this.speed;
                            }
                            else { // 고정 범위 끝까지 간 경우 -> 움직임 마쳤다고 판단
                                this.getVel().setMoving(false);
                                this.movingDone = true;
                            }
                        }

                        else if(this.moveCount >= this.move_randNum) {
                            this.getVel().setMoving(false);
                            this.movingDone = true;
                            this.moveCount = 0;
                        }
                    }
                }
            }
        }

        else if (this.dead || (this.stageNum != currentStageNum)) { //몹이 죽었을 경우
            log.info("{}",getX());
            for (int j = 0; j <= this.getAttackBox().getWidth(); j++) {
                collisonCheckX[this.getX() + j] = -1;
            }
        }
    }

    public void checkAttacked(int atkTimer_p1,int[] collisonCheckX) {//공격이 해당 물체에 가해졌는지 확인
        if ((collisonCheckX[atkTimer_p1] == 1) && (this.getX() <= atkTimer_p1 && atkTimer_p1 <= this.getX() + this.getCanvasLength()) && !this.dead) {
            this.subHealthCount(1);
            if (this.getHealthCount() == 0) {
                this.dead = true;
            }
        }
    }
    public void  updateAnimation(int currentStageNum) {
        //NormalZombie 애니메이션 변수
        if (this.dead == false && this.stageNum == currentStageNum) {
            if (this.getVel().isMoving() == false) {
                //플레이어가 해당 몬스터의 공격을 막았을 경우
                if (this.stunned == true) {
                    if (this.stunCount % 40 == 39) {
                        this.stunAnimaitonCount++;
                        this.stunAnimaitonCount = this.stunAnimaitonCount % this.stunLoop;
                    }
                }
                //텀이 지나고 다시 공격하는 경우
                else if (this.getVel().isAttacking() == true && this.waitCount == 30) {
                    if (this.attackFrame < 10) {
                        this.attackFrame++;
                    }
                    else if (this.attackFrame == 10) {
                        this.attackFrame = 0;
                        if (this.getAttackCount() < this.getAttackLoop() - 1) {
                            this.addAttackCount(1);
                        }
                        else {
                            this.setAttackCount(0);
                        }
                    }
                }
                //가만히 서 있는 경우
                else {
                    if(this.getIdleCount() == 30) {
                        this.setIdleCount(0);
                        this.addIdleCut(1);
                        this.setIdleCut(this.getIdleCut() % this.getIdleLoop());
                    }
                    this.addIdleCount(1);
                }
            }

            else if (this.getVel().isMoving() == true) {
                if (this.getWalkingCount() == 30) {
                    this.setWalkingCount(0);
                    this.addWalkingCut(1);
                    this.setWalkingCut(this.getWalkingCut() % this.getWalkingLoop());
                }
                this.addWalkingCount(1);
            }
        }
        else if (this.isDead() == true) {
            if (this.deathCount == 30 && this.deathCut < this.deathLoop) {
                this.deathCount = 0;
                this.deathCut++;
            }
            else if (this.deathCount < 30) {
                this.deathCount++;
            }
        }
    }

    public void addStunAnimaitonCount(int i) {
        stunAnimaitonCount+=i;
    }
    public void subStunAnimaitonCount(int i) {
        stunAnimaitonCount-=i;
    }

    public void addAttackFrame(int i) {
        attackFrame+=i;
    }

    public void addDeathFrame(int i){
        deathFrame+=i;
    }

    protected void addWaitCount(int i) {
        waitCount+=i;
    }

    protected void addMoveCount(int i) {
        moveCount+=i;
    }
}
