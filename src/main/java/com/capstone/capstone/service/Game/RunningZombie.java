package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class RunningZombie extends NormalZombie{
    private int stageNum;
    private boolean running;
    private boolean grabbing;

    private int grabWaitCount;

    private int runningLoop;
    private int deathLoop;
    private int runningCut;
    private int deathCut;

    private int runningCount;
    private int deathCount;
    RunningZombie(int x, int y, int width, int height, int canvasLength, int healthMax){
        super(x,y,width,height,canvasLength,healthMax);
        this.stageNum = 2;
        this.running = false;
        this.grabbing = false;

        this.grabWaitCount = 0;

        //각 동작의 총 컷 수
        this.runningLoop = 6;
        this.deathLoop = 6;

        //각 동작의 현재 몇 번째 컷인지 알려주는 정보
        // this.idleCut = 0;
        // this.walkingCut = 0;
        // this.attackCut = 0;
        this.runningCut = 0;
        this.deathCut = 0;

        //각 동작의 현재 몇 번째 프레임인지 알려주는 정보
        // this.idleCount = 0;
        // this.walkingCount = 0;
        // this.attackCount = 0;

        this.runningCount = 0;
        this.deathCount = 0;
    }

    public void checkGrabbingCancelled(Player p1, Player p2) {
        if (p1.isGrabbed() == true) { //p1이 잡혀있는 경우
            if (p2.getVel().isInteraction() == true) { // p1이 interaction key를 누르고 있는 경우
                log.info("p2 interaction count: {}", p2.getInteractionPressCount());
                p2.addInteractionPressCount(1);
            }
            log.info("{}",(Math.abs(p2.getX() - p1.getX())));
            if ((Math.abs(p2.getX() - p1.getX()) < 70 && p2.getInteractionPressCount() >= 150 ) || p1.isDead() == true) { //p2가 풀어준 경우이거나, p1이 죽었을 때
                this.grabbing = false;
                //몬스터 공격 정보 초기화
                this.grabWaitCount=0;
                this.setWaitCount(0);
                this.getAttackBox().setAtkTimer(0);
                this.getVel().setAttacking(false);
                this.setAttackDone(true);
                this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생

                p1.setGrabbed(false);

                if (p1.getVel().isLookingRight() == true) { //플레이어가 왼쪽에서 잡혔을 경우
                    p1.setX(this.getX() - this.getCanvasLength() - 20);
                    p1.getAttackBox().setPosition_x(p1.getX() + p1.getCanvasLength() / 2);
                }

                else if (p1.getVel().isLookingRight() == false) {//오른쪽에서 잡혔을 경우
                    p1.setX(this.getX() + this.getCanvasLength() + 20);
                    p1.getAttackBox().setPosition_x(p1.getX() + (p1.getCanvasLength() / 2));
                }
            }
        }

        else if (p2.isGrabbed() == true) {//p2이 잡혀있는 경우
            if (p1.getVel().isInteraction() == true) { // p1이 interaction key를 누르고 있는 경우
                log.info("p1 interaction count: {}", p1.getInteractionPressCount());
                p1.addInteractionPressCount(1);
            }
            log.info("{}",(Math.abs(p2.getX() - p1.getX())));
            if ((Math.abs(p2.getX() - p1.getX()) < 70 && p1.getInteractionPressCount() >= 150) || p2.isDead() == true) { //p1이 풀어준 경우이거나, p2가 죽었을 때
                this.grabbing = false;
                //몬스터 공격 정보 초기화
                this.grabWaitCount=0;
                this.setWaitCount(0);
                this.getAttackBox().setAtkTimer(0);
                this.getVel().setAttacking(false);
                this.setAttackDone(true);
                this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생

                p2.setGrabbed(false);

                if (p2.getVel().isLookingRight()) { //플레이어가 왼쪽에서 잡혔을 경우
                    p2.setX(this.getX() - this.getCanvasLength() - 20);
                    p2.getAttackBox().setPosition_x(p2.getX() + p2.getCanvasLength() / 2);
                }

                else if (p2.getVel().isLookingRight() == false) {//오른쪽에서 잡혔을 경우
                    p2.setX(this.getX() + this.getCanvasLength() + 20);
                    p2.getAttackBox().setPosition_x(p2.getX() + p2.getCanvasLength() / 2);
                }
            }
        }
    }

    public void zombieAttack(Player p1,Player p2, int[] collisonCheckX) { //매개변수가 너무 많이 들어가니까 오류가 뜸-> 매개변수의 수를 줄이니 오류 안뜸
        log.info("running {}, {}, {}, {}", getX(), getAttackBox().getPosition_x(), getAttackBox().getAtkTimer(), getAttackBox().getWidth());
        this.getVel().setMoving(false);

        if (this.grabbing == true) {
            log.info("grabbing is true=====================");
            this.checkGrabbingCancelled(p1, p2);
            if (this.grabWaitCount < 300) {
                this.grabWaitCount++;
            }
            else if (this.grabWaitCount == 300) { //2초가 지나면 데미지를 입힘
                this.grabWaitCount=0;
                if (p1.isGrabbed() == true) {
                    p1.subHealthCount(1);
                    p1.checkIsDead();
                }
                else if(p2.isGrabbed() == true){
                    p2.subHealthCount(1);
                    p2.checkIsDead();
                }
            }
        }


        if (this.getAttackRandomNum() >= 6 && this.grabbing == false) {// 9, 8, 7, 6 -> 일반 공격
            log.info("normal attack================");
            log.info("{}, {}, {}", getWaitCount(), getAttackBox().getAtkTimer(), getAttackCount());
            if (this.getVel().isLookingRight() == true) { // 몬스터가 오른쪽 보고있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격범위 -> 120, 40프레임 소모
                    this.setAttackDone(false);
                    //공격 상자 늘리기 전에 플레이어들의 방어 확인
                    if (p1.getVel().isBlocking() && !p1.getVel().isLookingRight() && (this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() + 6) >= p1.getBlockBox().getX_left()) {
                        // 플레이어1의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setWaitCount(0);
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }

                    else if (p2.getVel().isBlocking() && !p2.getVel().isLookingRight() && (this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() + 6) >= p2.getBlockBox().getX_left()) {
                        //플레이어2의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setWaitCount(0);
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    else {
                        if (this.getWaitCount() < 60) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.addWaitCount(1);
                        }

                        else if (this.getWaitCount() == 60) {
                            if (this.getAttackCount() >= 3) {
                                this.getAttackBox().addAtkTimer(6);
                            }
                        }


                        if (collisonCheckX[this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer()] == 0) { //공격이 플레이어에게 닿은 경우
                            //어느 플레이어에 닿았는지 확인해야 함
                            if (p1.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                                // 플레이어 1에 공격이 닿았을 경우
                                p1.setDamaged(true);
                            }

                            if (p2.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                                // 플레이어 2에 공격이 닿았을 경우
                                p2.setDamaged(true);
                            }
                        }
                    }
                }

                else { //공격 종료
                    this.setAttackDone(true);
                    if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }

                    if (p2.isDamaged() == true) {
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    this.setWaitCount(0);
                    this.getAttackBox().setAtkTimer(0);
                    this.getVel().setAttacking(false);
                    this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                }
            }

            else { //왼쪽을 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //왼쪽 공격 진행중
                    this.setAttackDone(false);
                    //공격 상자 늘리기 전에 플레이어의 방어 확인
                    if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == true && (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() - 6) <= p1.getBlockBox().getX_right()) {
                        // 플레이어1의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setWaitCount(0);
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    else if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == true && (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() - 6) <= p2.getBlockBox().getX_right()) {
                        // 플레이어2의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setWaitCount(0);
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    else {
                        if (this.getWaitCount() < 60) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.addWaitCount(1);
                        }

                        else if (this.getWaitCount() == 60) {
                            if (this.getAttackCount() >= 3) {
                                this.getAttackBox().addAtkTimer(6);
                            }
                        }

                        if (collisonCheckX[this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()] == 0) {//공격이 플레이어에게 닿은 경우
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
                        //ctx.fillRect(this.attackBox.position_x - this.attackBox.atkTimer, this.attackBox.position_y, this.attackBox.atkTimer, this.attackBox.height);
                    }
                }

                else { //공격 종료
                    this.setAttackDone(true);
                    if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                        p1.subHealthCount(1);
                        p1.checkIsDead();
                    }
                    if (p2.isDamaged() == true) { //플레이어2가 해당 몬스터의 공격을 받았을 경우
                        p2.subHealthCount(1);
                        p2.checkIsDead();
                    }

                    //몬스터 공격 정보 초기화
                    this.setWaitCount(0);
                    this.getAttackBox().setAtkTimer(0);
                    this.getVel().setAttacking(false);
                    this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                }
            }
        }

        else if (this.getAttackRandomNum() < 6 && this.grabbing == false) { // 5, 4, 3, 2, 1, 0 -> 잡기 공격
            log.info("grab======================");
            log.info("{}, {}, {}", getWaitCount(), getAttackBox().getAtkTimer(), getAttackCount());
            if (this.getVel().isLookingRight() == true) { // 몬스터가 오른쪽 보고있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격범위 -> 120, 40프레임 소모
                    this.setAttackDone(false);
                    //방어 확인 X
                    if (this.getWaitCount() < 60) { //몬스터가 공격 하기 전 잠깐 주는 텀
                        this.addWaitCount(1);
                    }

                    else if (this.getWaitCount() == 60) {
                        if (this.getAttackCount() >= 3) {
                            this.getAttackBox().addAtkTimer(6);
                        }
                    }


                    if (collisonCheckX[this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer()] == 0) { //잡기 공격이 플레이어에게 닿은 경우 -> 먼저 닿은 플레이어만 잡힘
                        //어느 플레이어에 닿았는지 확인해야 함
                        if (p1.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                            // 플레이어 1에 공격이 닿았을 경우
                            this.setWaitCount(0);
                            p1.setGrabbed(true);
                            p1.getVel().setLookingRight(false); //캐릭터는 왼쪽을 향함
                            p1.setX(this.getX()+50);
                            this.grabbing = true;
                        }

                        else if (p2.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength()) {
                            // 플레이어 2에 공격이 닿았을 경우
                            this.setWaitCount(0);
                            p2.setGrabbed(true);
                            p2.getVel().setLookingRight(false);//캐릭터는 왼쪽을 향함
                            p2.setX(this.getX()+50);
                            this.grabbing = true;
                        }
                    }
                }

                else { //공격 종료
                    if(this.grabbing == false) {
                        this.setAttackDone(true);
                        //몬스터 공격 정보 초기화
                        this.setWaitCount(0);
                        this.getAttackBox().setAtkTimer(0);
                        this.getVel().setAttacking(false);
                        this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                    }
                }
            }

            else { //왼쪽을 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //왼쪽 공격 진행중
                    this.setAttackDone(false);
                    //방어 확인 X
                    if (this.getWaitCount() < 60) { //몬스터가 공격 하기 전 잠깐 주는 텀
                        this.addWaitCount(1);
                    }

                    else if (this.getWaitCount() == 60) {
                        if (this.getAttackCount() >= 3) {
                            this.getAttackBox().addAtkTimer(6);
                        }
                    }

                    if (collisonCheckX[this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()] == 0) {//잡기 공격이 플레이어에게 닿은 경우 -> 먼저 잡힌 플레이어만
                        //어느 플레이어에 공격이 닿았는지 확인 해야함
                        if (p1.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength()) {
                            // 플레이어 1에 공격이 닿았을 경우
                            this.setWaitCount(0);
                            p1.setGrabbed(true);
                            p1.getVel().setLookingRight(true); // 캐릭터는 오른쪽을 향함
                            p1.setX(this.getX()-50);
                            this.grabbing = true;
                        }

                        else if ((p2.getX() < (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer())) && ((this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()) < (p2.getX() + p2.getCanvasLength()))) {
                            // 플레이어 2에 공격이 닿았을 경우
                            this.setWaitCount(0);
                            p2.setGrabbed(true);
                            p2.getVel().setLookingRight(true); // 캐릭터는 오른쪽을 향함
                            p2.setX(this.getX() - 50);
                            this.grabbing = true;
                        }
                    }
                }

                else { //공격 종료
                    if(this.grabbing == false) {
                        this.setAttackDone(true);
                        //몬스터 공격 정보 초기화
                        this.setWaitCount(0);
                        this.getAttackBox().setAtkTimer(0);
                        this.getVel().setAttacking(false);
                        this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                    }
                }
            }
        }
    }

    public void move(int bigX, int smallX, int[] collisonCheckX, int currentStageNum) {
        log.info("move===========");

        //몹의 공격 범위 갱신
        this.setX_detectLeft(this.getX() - 150);
        this.setX_detectRight(this.getX() + this.getCanvasLength() + 150);

        this.setX_attackLeft(this.getX() + 10);
        this.setX_attackRight(this.getX() + this.getCanvasLength() - 10);

        this.getAttackBox().setPosition_x(this.getX() + this.getCanvasLength() / 2);

        if (this.isStunned() == true) { //공격이 막혀 잠시 스턴에 걸린 경우
            this.stun();
        }
        // 몹이 살아있고, 공격하고 있지 않고, 스턴에 걸리지 않은 상태이고, 현재 스테이지에 해당한다면 움직임
        if (this.isDead() == false && this.getVel().isAttacking() == false && this.isStunned() == false && this.stageNum == currentStageNum) {
//            for (int i = 0; i <= this.getCanvasLength() - 100; i++) {
//                collisonCheckX[this.getX() + 50 + i] = 1;
//            }


            // 플레이어가 탐지 범위 안에 들어온 경우
            if((this.getX_detectLeft() <= bigX && bigX <= this.getX() + this.getCanvasLength()) || (this.getX() <= smallX && smallX <= this.getX_detectRight())) {
                log.info("1");
                //플레이어가 공격 범위 안에 들어온 경우
                if ((this.getX_attackLeft() <= bigX && bigX <= this.getX() + this.getCanvasLength()) || (this.getX() <= smallX && smallX <= this.getX_attackRight())) {
                    log.info("2");
                    this.getVel().setAttacking(true); //공격 활성화

                    if (this.getX_attackLeft() <= bigX && bigX <= this.getX() + this.getCanvasLength()) { // 왼쪽 방향으로 감지 했을 경우
                        this.getVel().setLookingRight(false);
                    }
                    else if (this.getX() <= smallX && smallX <= this.getX_attackRight()){ //오른쪽으로 감지 했을 경우
                        this.getVel().setLookingRight(true);
                    }
                    this.running = false;
                }

                else { //탐지 범위 안에 들어왔지만 공격 범위는 아닌 경우 -> 플레이어 따라가기 / 뛰는 좀비는 속도 4
                    log.info("3");
                    if (this.getX_detectLeft() < bigX && bigX < this.getX_attackLeft()) { //왼쪽으로 이동
                        this.getVel().setMoving(true);
                        this.getVel().setLookingRight(false);
                        this.running = true;
//                        collisonCheckX[this.getX() + 49] = 1;
//                        collisonCheckX[this.getX() + 48] = 1;
//                        collisonCheckX[this.getX() + 47] = 1;
//                        collisonCheckX[this.getX() + 46] = 1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 51] = -1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 52] = -1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 53] = -1;
                        this.subX(4);
                    }

                    else if (this.getX_attackRight() < smallX && smallX <= this.getX_detectRight()) { //오른쪽으로 이동
                        log.info("4");
                        this.getVel().setMoving(true);
                        this.getVel().setLookingRight(true);
                        this.running = true;
//                        collisonCheckX[this.getX() + 50] = -1;
//                        collisonCheckX[this.getX() + 51] = -1;
//                        collisonCheckX[this.getX() + 52] = -1;
//                        collisonCheckX[this.getX() + 53] = -1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 49] = 1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 48] = 1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 47] = 1;
//                        collisonCheckX[this.getX() + this.getCanvasLength() - 46] = 1;
                        this.addX(4);
                    }
                }
            }

            else if((this.getX() + 50 < this.xMax_left) || (this.xMax_right < this.getX() + this.getCanvasLength() - 50)) {//지정된 구역을 벗어난 경우
                log.info("5");
                this.running = false;
                this.comeBackToPosition(collisonCheckX);
            }

            else { // 탐지가 된것도 아니고, 지정된 구역을 벗어난 경우도 아닌 경우 -> 일반 무작위 움직임
                log.info("{},{},{},{},{},{}",isMovingDone(), getMoveCount(), getMove_randNum());
                this.running = false;
                if (this.isMovingDone() == true) { // 움직임이 끝난 상태일 때
                    if (this.getMoveCount() < 90) {// 1.5초 동안 잠시 멈췄다가
                        this.getVel().setMoving(false);
                        this.addMoveCount(1);
                    }

                    else { // 다시 움직임 재개
                        this.setMoveCount(0);
                        this.setMove_randNum((int) Math.floor(Math.random() * this.getMove_range()));
                        // floor -> 정수로 반올림, random -> 0~1사이 난수 발생 여기선 move_range만큼 곱해줌

                        this.setMovingDone(false);
                    }

                }

                else { //움직임이 끝나지 않았을 때
                    if (this.getMove_randNum() <= 10 && this.getMoveCount() < this.getMove_randNum()) { //난수가 일정 수보다 작으면 가만히 서 있는 걸로
                        this.getVel().setMoving(false);
                        this.addMoveCount(this.getSpeed());
                    }

                    else { //움직이는 경우

                        if ((this.getMove_randNum() % 2 == 0) && this.getMoveCount() < this.getMove_randNum()) { //짝수인 경우 -> 오른쪽으로 이동
                            if (this.getX() + this.getCanvasLength() + this.getSpeed() <= this.xMax_right) { //고정 범위 안에 있는 경우
                                this.getVel().setMoving(true);
//                                collisonCheckX[this.getX() + 50] = -1;
//                                collisonCheckX[this.getX() + this.getCanvasLength() -49] = 1;
                                this.getVel().setLookingRight(true);
                                this.addX(this.getSpeed());
                                this.addMoveCount(this.getSpeed());
                            }
                            else { // 고정 범위 끝까지 간 경우 -> 움직임 마쳤다고 판단
                                this.getVel().setMoving(false);
                                this.setMovingDone(true);
                            }

                        }
                        else if ((this.getMove_randNum() % 2 == 1) && this.getMoveCount() < this.getMove_randNum()) {//홀수인 경우 -> 왼쪽으로 이동
                            //console.log(this.x - this.speed);
                            if (this.getX() - this.getSpeed() >= this.xMax_left) { //고정 범위 안에 있는 경우
                                this.getVel().setMoving(true);
//                                collisonCheckX[this.getX() + 49] = 1;
//                                collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
                                this.getVel().setLookingRight(false);
                                this.subX(this.getSpeed());
                                this.addMoveCount(this.getSpeed());
                            }
                            else { // 고정 범위 끝까지 간 경우 -> 움직임 마쳤다고 판단
                                this.getVel().setMoving(false);
                                this.setMovingDone(true);
                            }
                        }

                        else if (this.getMoveCount() >= this.getMove_randNum()) {
                            this.getVel().setMoving(false);
                            this.setMovingDone(true);
                            this.setMoveCount(0);
                        }
                    }
                }
            }
        }

//        else if (this.isDead() == true) { //몹이 죽었거나, 현재 스테이지에 해당하지 않는 경우
//            for (int i = 0; i <= this.getCanvasLength()-100; i++) {
//                collisonCheckX[this.getX()+50 + i] = -1;
//            }
//        }
//        return collisonCheckX;
    }

    public void moveObjectRight(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
//            collisonCheckX[getX() + 50] = -1;
//            collisonCheckX[getX() + 51] = -1;
//            collisonCheckX[getX() + getCanvasLength() - 49] = 1;
//            collisonCheckX[getX() + getCanvasLength() - 48] = 1;
            addX(2);

            xMax_left+=2;
            xMax_right+=2;

//            setFixedRange(xMax_left+2, xMax_right+2);
        }
//        return collisonCheckX;
    }

    public void moveObjectLeft(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
//            collisonCheckX[getX() + 48] = 1;
//            collisonCheckX[getX() + 49] = 1;
//            collisonCheckX[getX() + getCanvasLength() - 50] = -1;
//            collisonCheckX[getX() + getCanvasLength() - 51] = -1;
            subX(2);

            xMax_left-=2;
            xMax_right-=2;
//            setFixedRange(xMax_left-2, xMax_right-2);
        }
//        return collisonCheckX;
    }

    public void updateAnimation(int currentStageNum) {
        this.setHitCheck(false);
        //RunningZombie 애니메이션 변수
        if (this.isDead() == false && this.stageNum == currentStageNum) {
            if (this.getVel().isMoving() == false) {
                log.info("animation {}, {}", grabbing, getVel().isAttacking());
                //플레이어가 해당 몬스터의 공격을 막았을 경우
                if (this.isStunned() == true) {
                    this.setAttackFrame(0);
                    this.setAttackCount(0);
                    if (this.getStunCount() % 40 == 39) {
                        this.addStunAnimaitonCount(1);
                        this.setStunAnimaitonCount(this.getStunAnimaitonCount() % this.getStunLoop());
                    }
                }
                //텀이 지나고 다시 공격하는 경우
                else if (this.getVel().isAttacking() == true && this.getWaitCount() == 60 && this.grabbing == false) {
                    if (this.getAttackFrame() < 8) {
                        this.addAttackFrame(1);
                    }
                    else if (this.getAttackFrame() == 8) {
                        this.setAttackFrame(0);
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
                    this.setAttackFrame(0);
                    this.setAttackCount(0);
                    if(this.getIdleCount() == 30) {
                        this.setIdleCount(0);
                        this.addIdleCut(1);
                        this.setIdleCut(this.getIdleCut() % this.getIdleLoop());
                    }
                    this.addIdleCount(1);
                }
            }

            else if (this.getVel().isMoving() == true) { //움직이는 경우
                if (this.running == false) {//걷는 경우
                    if (this.getWalkingCount() == 30) {
                        this.setWalkingCount(0);
                        this.addWalkingCut(1);
                        this.setWalkingCut(this.getWalkingCut() % this.getWalkingLoop());
                    }
                    this.addWalkingCount(1);
                }
                else { //뛰는 경우 -> 0.2초마다 한 컷 -> 12프레임 마다 한 컷
                    if (this.runningCount == 12) {
                        this.runningCount = 0;
                        this.runningCut++;
                        this.runningCut = this.runningCut % this.runningLoop;
                    }
                    this.runningCount++;
                }
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

}
