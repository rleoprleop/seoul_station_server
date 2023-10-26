package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CrawlingZombie extends NormalZombie{
    private int stageNum;
    private int rangedAttack_left;
    private int rangedAttack_right;
    private boolean spitting;

    private int rangedAttackDelay;
    private int rangedAttackTarget;

    private int rangedWaitCount;
    private boolean rangedAttackDone;

    private int spittingLoop;
    private int deathLoop;
    private int poisonFallingLoop;

    private int spittingCut;
    private int deathCut;
    private int poisonFallingCut;

    private int spittingCount;
    private int deathCount;
    private int poisonFallingCount;

    private int bigX;
    private int smallX;
    CrawlingZombie(int x, int y, int width, int height, int canvasLength, int healthMax){
        super(x,y,width,height,canvasLength,healthMax);
        this.stageNum = 3;
        this.rangedAttack_left = x - 400;
        this.rangedAttack_right = x + canvasLength + 400;
        this.spitting = false;


        this.rangedAttackDelay = 0;// 원거리 공격이 유효해지는 시간 -> 0.5초
        this.rangedAttackTarget = 0; //목표 지점

        //각 동작의 총 컷 수
        this.spittingLoop = 6;
        this.deathLoop = 6;
        this.poisonFallingLoop = 4;

        //각 동작의 현재 몇 번째 컷인지 알려주는 정보
        // this.idleCut = 0;
        // this.walkingCut = 0;
        // this.attackCut = 0;
        this.spittingCut = 0;
        this.deathCut = 0;
        this.poisonFallingCut = 0;

        //각 동작의 현재 몇 번째 프레임인지 알려주는 정보
        // this.idleCount = 0;
        // this.walkingCount = 0;
        // this.attackCount = 0;

        this.spittingCount = 0;
        this.deathCount = 0;
        this.poisonFallingCount = 0;

        //타겟 확인용 bigX, smallX
        this.bigX = 0;
        this.smallX = 0;
    }

    public int biggerX(int p1_x, int p2_x) {
        if (p1_x >= p2_x) {
            return p1_x;
        }
        else {
            return p2_x;
        }
    }

    public int smallerX(int p1_x, int p2_x){
        if (p1_x <= p2_x) {
            return p1_x;
        }
        else {
            return p2_x;
        }
    }

    public void checkBigXSmallX(Player p1, Player p2) {
        if (p1.isDead() == false && p2.isDead() == false) {// 둘 다 살아 있는 경우
            this.bigX = biggerX(p1.getX() + p1.getCanvasLength() - 40, p2.getX() + p2.getCanvasLength() - 40);
            this.smallX = smallerX(p1.getX() + 40, p2.getX() + 40);
        }

        else if (p1.isDead() == true && p2.isDead() == false) { // p2만 살아있는 경우
            this.bigX = p2.getX() + p2.getCanvasLength() - 40;
            this.smallX = p2.getX() + 40;
        }

        else if (p1.isDead() == false && p2.isDead() == true) { //p1만 살아있는 경우
            this.bigX = p1.getX() + p1.getCanvasLength() - 40;
            this.smallX = p1.getX() + 40;
        }

        else { //둘 다 죽은 경우 -> 게임 종료
            this.bigX = -1;
            this.smallX = -1;
        }
    }

    public void checkRangedAttack(Player p1, Player p2) {
        //p1이 맞은 경우
        if (p1.getX() + 40 < this.rangedAttackTarget && this.rangedAttackTarget < p1.getX() + p1.getCanvasLength() - 40) {
            p1.setDamaged(true);
        }
        if (p2.getX() + 40 < this.rangedAttackTarget && this.rangedAttackTarget < p2.getX() + p2.getCanvasLength() - 40) {
            p2.setDamaged(true);
        }
    }

    public void zombieAttack(Player p1, Player p2, int[] collisonCheckX) {
        this.setAttackDone(false);
        this.checkBigXSmallX(p1, p2);
        if ((this.bigX >= this.rangedAttack_left && this.bigX <= this.getX() + 100) || this.rangedAttackDone == false) { //왼쪽으로 공격 하는 경우
            //원거리 공격
            if ((this.bigX >= this.rangedAttack_left && this.bigX < this.getX_attackLeft()) || this.rangedAttackDone == false) {
                this.rangedAttackDone = false;
                this.spitting = true;
                if (this.getWaitCount() < 120 && this.getWaitCount() != 60) {
                    this.addWaitCount(1);
                }
                else if (this.getWaitCount() == 60) {
                    this.rangedAttackTarget = bigX - 60;// 대상 플레이어 가운데 지점
                    this.addWaitCount(1);
                }
                else if (this.getWaitCount() == 120) { //원거리 공격 활성화
                    if (this.rangedAttackDelay < 30) {
                        this.rangedAttackDelay++;
                        this.checkRangedAttack(p1, p2);
                    }
                    else if (this.rangedAttackDelay == 30) {// 원거리 공격 종료
                        this.setAttackDone(true);
                        this.rangedAttackDone = true;
                        this.setWaitCount(0);
                        this.rangedAttackDelay = 0;
                        this.getVel().setAttacking(false);
                        this.spitting = false;
                        this.rangedAttackTarget = 0;
                        if (p1.isDamaged() == true) {
                            p1.subHealthCount(1);
                            p1.checkIsDead();
                        }
                        if (p2.isDamaged() == true) {
                            p2.subHealthCount(1);
                            p2.checkIsDead();
                        }
                    }
                }
            }
            //근거리 공격
            else if (this.getX_attackLeft() <= this.bigX && this.bigX <= this.getX() + 100 && this.rangedAttackDone == true) {
                this.spitting = false;
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //왼쪽 공격 진행중
                    this.setAttackDone(false);
                    //공격 상자 늘리기 전에 플레이어의 방어 확인
                    if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == true && (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() - 6) <= p1.getBlockBox().getX_right()) {
                        // 플레이어1의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == true && (this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() - 6) <= p2.getBlockBox().getX_right()) {
                        // 플레이어2의 오른쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    else {
                        if (this.getWaitCount() < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.addWaitCount(1);
                        }

                        else if (this.getWaitCount() == 30) {
                            if (this.getAttackCount() >= 2) {
                                this.getAttackBox().addOfAttackTimer(6);
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
                }

            }

        }
        else if (this.getX() + 100 < this.smallX && this.smallX <= this.getX_attackRight()) { // 오른쪽 공격
            this.getVel().setLookingRight(true);
            //원거리 공격
            if (this.getX_attackRight() < this.smallX && this.smallX <= this.rangedAttack_right) {
                this.spitting = true;
                if (this.getWaitCount() < 120 && this.getWaitCount() != 60) {
                    this.addWaitCount(1);
                }
                else if (this.getWaitCount() == 60) {
                    this.rangedAttackTarget = smallX + 60;// 대상 플레이어 가운데 지점
                    this.addWaitCount(1);
                }
                else if (this.getWaitCount() == 120) { //원거리 공격 활성화
                    if (this.rangedAttackDelay < 30) {
                        this.rangedAttackDelay++;
                        this.checkRangedAttack(p1, p2);
                    }
                    else if (this.rangedAttackDelay == 30) {// 원거리 공격 종료
                        this.rangedAttackDone = true;
                        this.setWaitCount(0);
                        this.rangedAttackDelay = 0;
                        this.getVel().setAttacking(false);
                        this.spitting = false;
                        this.rangedAttackTarget = 0;
                        if (p1.isDamaged() == true) {
                            p1.subHealthCount(1);
                            p1.checkIsDead();
                        }
                        if (p2.isDamaged() == true) {
                            p2.subHealthCount(1);
                            p2.checkIsDead();
                        }
                    }
                }
            }
            //근거리 공격
            else if (this.getX() + 100 <= this.smallX && this.smallX <= this.getX_attackRight() && this.rangedAttackDone == true) {
                this.spitting = false;
                if (this.getAttackBox().getAtkTimer() <= this.getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격범위 -> 120, 40프레임 소모
                    this.setAttackDone(false);
                    //공격 상자 늘리기 전에 플레이어들의 방어 확인
                    if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == false && (this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() + 6) >= p1.getBlockBox().getX_left()) {
                        // 플레이어1의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }

                    if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == false && (this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() + 6) >= p2.getBlockBox().getX_left()) {
                        //플레이어2의 왼쪽 방어가 먼저 활성화 되었을 때 -> 공격 막힘
                        this.setStunned(true);
                        this.getVel().setAttacking(false);
                        this.getAttackBox().setAtkTimer(0);
                        this.setAttackDone(true);
                    }
                    else {
                        if (this.getWaitCount() < 30) { //몬스터가 공격 하기 전 잠깐 주는 텀
                            this.addWaitCount(1);
                        }

                        else if (this.getWaitCount() == 30) {
                            if (this.getAttackCount() >= 2) {
                                this.getAttackBox().addOfAttackTimer(6);
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
                }

            }
        }
    }

    public void move(int bigX, int smallX,int[]  collisonCheckX, int currentStageNum, Player p1, Player p2) {
        this.checkBigXSmallX(p1, p2);
        this.rangedAttack_left = this.getX() - 400;
        this.rangedAttack_right = this.getX() + this.getCanvasLength() + 400;

        this.setX_attackLeft(this.getX() + 10);
        this.setX_attackRight(this.getX() + this.getCanvasLength() - 10);

        this.getAttackBox().setPosition_x(this.getX() + this.getCanvasLength() / 2);

        if (this.isStunned() == true) { //공격이 막혀 잠시 스턴에 걸린 경우
            this.stun();
        }
        // 몹이 살아있고, 공격하고 있지 않고, 스턴에 걸리지 않은 상태이고, 현재 스테이지에 해당한다면 움직임
        if (this.isDead() == false && this.getVel().isAttacking() == false && this.isStunned() == false && this.stageNum == currentStageNum) {
            for (var i = 0; i <= this.getCanvasLength() - 100; i++) {
                collisonCheckX[this.getX() + 50 + i] = 1;
            }

            if ((bigX >= this.rangedAttack_left && bigX <= this.getX() + 100) || (smallX <= this.rangedAttack_right && smallX >= this.getX() + 100)) { //(원거리)공격 범위 내 플레이어가 들어온 경우
                this.getVel().setAttacking(true);
                this.getVel().setMoving(false);
            }

            else { // 이 좀비는 플레이어를 따라가지 않음. 공격 이외에는 그냥 무작위 움직임
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
                                collisonCheckX[this.getX() + 50] = -1;
                                collisonCheckX[this.getX() + this.getCanvasLength() -49] = 1;
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
                                collisonCheckX[this.getX() + 49] = 1;
                                collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
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
        else if (this.isDead() == true || this.stageNum != currentStageNum) { // 죽었거나 해당 스테이지가 아닐때
            for (int i = 0; i <= this.getWidth(); i++) {
                collisonCheckX[this.getX() + i] = -1;
            }
        }
    }
    public void updateAnimation(int currentStageNum) {
        //crawlingZombie 애니메이션 변수
        if (this.isDead() == false && this.stageNum == currentStageNum) {
            if (this.getWaitCount() >= 110) {// 투사체 떨어지는 부분. 실질적으로 데미지 입는 시간은 waitCount 120부터
                if (this.poisonFallingCount == 10) {
                    this.poisonFallingCut++;
                    this.poisonFallingCount = 0;
                    if (this.poisonFallingCut == 4) {
                        this.poisonFallingCut = 0;
                    }
                }
                else {
                    this.poisonFallingCount++;
                }
            }
            if (this.getVel().isMoving() == false) {
                //플레이어가 해당 몬스터의 공격을 막았을 경우
                if (this.isStunned() == true) {
                    if (this.getStunCount() % 40 == 39) {
                        this.addStunAnimaitonCount(1);
                        this.setStunAnimaitonCount(this.getStunAnimaitonCount() % this.getStunLoop());
                    }
                }
                //텀이 지나고 다시 공격하는 경우
                else if (this.spitting == true) { // 원거리 공격
                    if (this.getWaitCount() >= 60 && this.getWaitCount() <= 90) {
                        if (this.spittingCount == 10) {
                            this.spittingCount = 0;
                            this.spittingCut++;
                            if (this.spittingCut == 3) {
                                this.spittingCut = 0;
                            }
                        }
                        else {
                            this.spittingCount++;
                        }

                    }

                }

                else if (this.getVel().isAttacking() == true && this.getWaitCount() == 30 && this.spitting == false) { // 근거리 공격
                    if (this.getAttackFrame() < 10) {
                        this.addAttackFrame(1);
                    }
                    else if (this.getAttackFrame() == 10) {
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
                    if(this.getIdleCount() == 30) {
                        this.setIdleCount(0);
                        this.addIdleCut(1);
                        this.setIdleCut(this.getIdleCut() % this.getIdleLoop());
                    }
                    this.addIdleCount(1);
                }
            }

            else if (this.getVel().isMoving() == true) { //움직이는 경우
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
}
