package com.capstone.capstone.service.Game;

public class BossZombie extends NormalZombie{
    private int centerPoint;
    private int distanceOfSmallX;
    private int distanceOfBigX;
    private boolean flying;
    private int moveWaitCount;
    private int moveRandNum;
    private int jumpCount;
    private int delayCount;
    private int fallingCount;
    private int fallingWarningCount;
    private int fallingTargetPoint;
    private int fallingTargetRandNum;

    private boolean blockStayedP1;
    private boolean blockStayedP2;

    private int comboAttackMax;
    private int comboAttackTime;
    private int jumpLoop;
    private int walkingLoop;
    private int idleLoop;
    private int deathLoop;
    private int attackLoop;
    private int comboAttackLoop;
    private int landLoop;
    private int walkingCut;
    private int idleCut;
    private int deathCut;
    private int attackCut;
    private int comboAttackCut;
    private int jumpCut;
    private int landCut;

    private int walkingCount;
    private int idleCount;
    private int attackCount;
    private int deathCount;
    private int comboAttackCount;

    private int stageNum;

    BossZombie(int x, int y, int width, int height, int canvasLength, int healthMax) {
        super(x, y, width, height, canvasLength, healthMax);
        this.centerPoint = x + canvasLength / 2;

        //움직임 관련 변수
        this.distanceOfSmallX = 0;
        this.distanceOfBigX = 0;

        this.flying = false;
        this.moveWaitCount = 0; //움직일 때 1초 측정
        this.moveRandNum = 0; // 1초마다 갱신되는 난수. 공중 이동, 일반 이동 결정하는 요인

        this.jumpCount = 0;
        this.delayCount = 0;
        this.fallingCount = 0;
        this.fallingWarningCount = 0;
        this.fallingTargetPoint = 0; //착지 지점
        this.fallingTargetRandNum = 0;

        //공격 관련 변수
        this.blockStayedP1 = false; // 플레이어가 방어 유지 했는지 확인
        this.blockStayedP2 = false;

        this.comboAttackMax = 3;
        this.comboAttackTime = 0;

        //애니메이션 변수

        //Loop
        this.walkingLoop = 4;
        this.idleLoop = 4;
        this.deathLoop = 9;
        this.attackLoop = 4;
        this.comboAttackLoop = 12;
        this.jumpLoop = 4;
        this.landLoop = 6;

        //Cut
        this.walkingCut = 0;
        this.idleCut = 0;
        this.deathCut = 0;
        this.attackCut = 0;
        this.comboAttackCut = 0;
        this.jumpCut = 0;
        this.landCut = 0;

        //Count
        this.walkingCount = 0;
        this.idleCount = 0;
        this.attackCount = 0;
        this.deathCount = 0;
        this.comboAttackCount = 0;
        //점프와 착지는 위 Count사용

        this.getAttackBox().setWidth(160);

        this.stageNum = 6;
    }
    public void checkDistance(int bigX, int smallX) {
        this.distanceOfSmallX = Math.abs(this.centerPoint - smallX);
        this.distanceOfBigX = Math.abs(this.centerPoint - bigX);
    }

    public void flyToTarget(Player p1, Player p2, int[] collisonCheckX) {
        if (this.jumpCount == 1) {
            for (var i = 0; i <= this.getCanvasLength() - 100; i++) { // 점프 하는 자리 없는 걸로 취급
                collisonCheckX[this.getX() + 50 + i] = -1;
            }
        }
        if (this.jumpCount < 60) {//1초동안 점프
            this.jumpCount++;
        }

        else if (this.jumpCount == 60) {
            if (this.delayCount < 60) { //1초동안 딜레이
                this.delayCount++;
            }

            else if (this.delayCount == 60) {
                //플레이어가 둘 다 살아 있을 때 착륙 지점을 랜덤으로 타겟 지정
                //타겟 지정 한 번 하면 안바뀜
                if (this.fallingWarningCount == 0) {
                    this.fallingWarningCount++;
                    this.fallingTargetRandNum = (int) Math.floor(Math.random() * 2);
                    if (p1.isDead() == false && p2.isDead() == false) {
                        if (this.fallingTargetRandNum == 0) { // p1한테 가는 경우
                            this.fallingTargetPoint = p1.getX() + p1.getCanvasLength() / 2;
                        }

                        else if (this.fallingTargetRandNum == 1) { //p2한테 가는 경우
                            this.fallingTargetPoint = p2.getX() + p2.getCanvasLength() / 2;
                        }
                    }

                    else if (p1.isDead() == false && p2.isDead() == true) { //p1만 남아있는 경우
                        this.fallingTargetPoint = p1.getX() + p1.getCanvasLength() / 2;
                    }

                    else if (p1.isDead() == true && p2.isDead() == false) { //p2만 남아있는 경우
                        this.fallingTargetPoint = p2.getX() + p2.getCanvasLength() / 2;
                    }
                }

                else if (this.fallingWarningCount > 0 && this.fallingWarningCount < 150) { //2.5초동안 경고 표시
                    this.fallingWarningCount++;
                }
                else if (this.fallingWarningCount == 150) {
//                    this.setX(this.fallingTargetPoint - this.getCanvasLength() / 2);//떨어지는 지점으로 위치 이동
                    if (this.fallingCount < 30) {
                        this.fallingCount++;
                    }
                    else if (this.fallingCount == 30) {//착륙 완료. 플레이어가 데미지 입었는지 확인 해야함.
                        for (int i = 0; i <= this.getCanvasLength() - 100; i++) { // 착지하는 자리에 좌표 갱신
                            collisonCheckX[this.getX() + 50 + i] = 1;
                        }
                        if (p1.getX() + 50 <= this.fallingTargetPoint && this.fallingTargetPoint <= p1.getX() + p1.getCanvasLength() - 50) { // p1이 착륙지점에 있었을 때
                            p1.setDamaged(true);
                            p1.subHealthCount(1);
                            p1.checkIsDead();
                            if(p1.getX() + p1.getCanvasLength() / 2 <= this.fallingTargetPoint) { //왼쪽으로 밀려나는 경우
                                if (p1.getX() - 300 < 1) {// 맵 왼쪽 끝보다 더 멀리 밀리는 경우 -> 반대방향으로 이동
                                    p1.addX(350); //원래는 300 밀려야 되는데 반대 방향이라 50추가
                                }
                                else {
                                    p1.subX(300);
                                }
                            }
                            else {//오른쪽으로 밀려나는 경우
                                if (p1.getX() + p1.getCanvasLength() + 300 > 1910) { //맵 오른쪽 끝보다 더 멀리 밀리는 경우 -> 반대 방향으로 이동
                                    p1.subX(350);//원래는 300 밀려야 되는데 반대 방향이라 50추가
                                }
                                else {
                                    p1.addX(300);
                                }
                            }
                        }

                        if (p2.getX() + 50 <= this.fallingTargetPoint && this.fallingTargetPoint <= p2.getX() + p2.getCanvasLength() - 50) { // p2이 착륙지점에 있었을 때
                            p2.setDamaged(true);
                            p2.subHealthCount(1);
                            p2.checkIsDead();
                            if(p2.getX() + p2.getCanvasLength() / 2 <= this.fallingTargetPoint) { //왼쪽으로 밀려나는 경우
                                if (p2.getX() - 300 < 1) {// 맵 왼쪽 끝보다 더 멀리 밀리는 경우 -> 반대방향으로 이동
                                    p2.addX(350); //원래는 300 밀려야 되는데 반대 방향이라 50추가
                                }
                                else {
                                    p2.subX(300);
                                }
                            }
                            else {//오른쪽으로 밀려나는 경우
                                if (p2.getX() + p2.getCanvasLength() + 300 > 1910) { //맵 오른쪽 끝보다 더 멀리 밀리는 경우 -> 반대 방향으로 이동
                                    p2.subX(350);//원래는 300 밀려야 되는데 반대 방향이라 50추가
                                }
                                else {
                                    p2.addX(300);
                                }
                            }
                        }

                        this.fallingCount++;

                    }
                    else if (this.fallingCount > 30 && this.fallingCount < 90) { // 착지 완료 후 1초 딜레이
                        this.fallingCount++;
                    }

                    else if (this.fallingCount == 90) {
                        this.flying = false;
                        this.jumpCount = 0;
                        this.delayCount = 0;
                        this.fallingWarningCount = 0;
                        this.fallingCount = 0;
                        this.moveRandNum = (int) Math.floor(Math.random() * 10); //0~9 사이 난수 발생
                    }

                }
            }
        }
    }

    public int updateMoveRandNum() {
        if (this.moveWaitCount < 60) {
            this.moveWaitCount++;
            return this.moveRandNum;
        }
        else {
            this.moveWaitCount = 0;
            return (int) Math.floor(Math.random() * 10); //0~9 사이 난수 발생
        }
    }

    public void zombieAttack(Player p1, Player p2, int[] collisonCheckX) {
        this.getVel().setMoving(false);
        if (this.getAttackRandomNum() <= 6) {// 0, 1, 2, 3, 4, 5, 6 -> 일반 공격
            if (this.getVel().isLookingRight() == true) { //오른쪽 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() < this.getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격 범위 -> 160, 40프레임
                    this.setAttackDone(false);
                    if (this.getWaitCount() < 120) {
                        this.addWaitCount(1);
                    }
                    else if (this.getWaitCount() == 120) {
                        if (this.attackCut >= 2) { // 플레이어 방어 확인. 보스는 스턴 모션 없음
                            this.getAttackBox().addAtkTimer(8);

                            if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == false ) {
                                this.blockStayedP1 = true;
                            }
                            else if (p1.getVel().isBlocking() == false) {
                                this.blockStayedP1 = false;
                            }

                            if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == false ) {
                                this.blockStayedP2 = true;
                            }
                            else if (p2.getVel().isBlocking() == false) {
                                this.blockStayedP2 = false;
                            }
                        }

                        if (collisonCheckX[this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer()] == 0) {
                            if (p1.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength() && this.blockStayedP1 == false) {
                                p1.setDamaged(true);
                            }
                            if (p2.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength() && this.blockStayedP2 == false) {
                                p2.setDamaged(true);
                            }
                        }
                    }

                }
                else { // 공격 종료
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
            else {//왼쪽 보고 있는 경우
                if (this.getAttackBox().getAtkTimer() < this.getAttackBox().getWidth()) { //왼쪽 공격 진행중. 공격 범위 -> 160, 40프레임
                    this.setAttackDone(false);
                    if (this.getWaitCount() < 120) {
                        this.addWaitCount(1);
                    }
                    else if (this.getWaitCount() == 120) {
                        if (this.attackCut >= 2) { // 플레이어 방어 확인. 보스는 스턴 모션 없음
                            this.getAttackBox().addAtkTimer(8);

                            if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == true) {
                                this.blockStayedP1 = true;
                            }
                            else if (p1.getVel().isBlocking() == false) {
                                this.blockStayedP1 = false;
                            }

                            if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == true) {
                                this.blockStayedP2 = true;
                            }
                            else if (p2.getVel().isBlocking() == false) {
                                this.blockStayedP2 = false;
                            }
                        }

                        if (collisonCheckX[this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer()] == 0) {
                            if (p1.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength() && this.blockStayedP1 == false) {
                                p1.setDamaged(true);
                            }
                            if (p2.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength() && this.blockStayedP2 == false) {
                                p2.setDamaged(true);
                            }
                        }
                    }

                }
                else { // 공격 종료
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
        }
        else { // 특수공격 -> 3번 연속 공격/ 120프레임
            if (this.getWaitCount() < 120) {
                this.addWaitCount(1);
            }
            else if (this.getWaitCount() == 120) {
                if (this.getVel().isLookingRight() == true) {//오른쪽 공격
                    if (this.comboAttackTime < this.comboAttackMax) {//3번발동
                        if (this.getAttackBox().getAtkTimer() < this.getAttackBox().getWidth()) { //오른쪽 공격 진행중. 공격 범위 -> 160, 40프레임
                            this.setAttackDone(false);
                            if (this.attackCut >= 2) { // 플레이어 방어 확인. 보스는 스턴 모션 없음
                                this.getAttackBox().addAtkTimer(8);

                                if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == false ) {
                                    this.blockStayedP1 = true;
                                }
                                else if (p1.getVel().isBlocking() == false) {
                                    this.blockStayedP1 = false;
                                }

                                if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == false ) {
                                    this.blockStayedP2 = true;
                                }
                                else if (p2.getVel().isBlocking() == false) {
                                    this.blockStayedP2 = false;
                                }
                            }

                            if (collisonCheckX[this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer()] == 0) {
                                if (p1.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength() && this.blockStayedP1 == false) {
                                    p1.setDamaged(true);
                                }
                                if (p2.getX() < this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength() && this.blockStayedP2 == false) {
                                    p2.setDamaged(true);
                                }
                            }

                        }
                        else { // 공격 종료
                            this.comboAttackTime++;
                            if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                                p1.subHealthCount(1);
                                p1.checkIsDead();
                            }

                            if (p2.isDamaged() == true) {
                                p2.subHealthCount(1);
                                p2.checkIsDead();
                            }

                            //몬스터 공격 정보 초기화
                            this.getAttackBox().setAtkTimer(0);
                            if (this.comboAttackTime == this.comboAttackMax) { // 마지막 공격 끝났을 시 완전 종료
                                this.setAttackDone(true);
                                this.setWaitCount(0);
                                this.getVel().setAttacking(false);
                                this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                            }
                        }
                    }
                }

                else {//왼쪽 공격
                    if (this.comboAttackTime < this.comboAttackMax) {//3번발동
                        if (this.getAttackBox().getAtkTimer() < this.getAttackBox().getWidth()) { //왼쪽 공격 진행중. 공격 범위 -> 160, 40프레임
                            this.setAttackDone(false);
                            if (this.attackCut >= 2) { // 플레이어 방어 확인. 보스는 스턴 모션 없음
                                this.getAttackBox().addAtkTimer(8);

                                if (p1.getVel().isBlocking() == true && p1.getVel().isLookingRight() == true) {
                                    this.blockStayedP1 = true;
                                }
                                else if (p1.getVel().isBlocking() == false) {
                                    this.blockStayedP1 = false;
                                }

                                if (p2.getVel().isBlocking() == true && p2.getVel().isLookingRight() == true) {
                                    this.blockStayedP2 = true;
                                }
                                else if (p2.getVel().isBlocking() == false) {
                                    this.blockStayedP2 = false;
                                }
                            }

                            if (collisonCheckX[this.getAttackBox().getPosition_x() + this.getAttackBox().getAtkTimer()] == 0) {
                                if (p1.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p1.getX() + p1.getCanvasLength() && this.blockStayedP1 == false) {
                                    p1.setDamaged(true);
                                }
                                if (p2.getX() < this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() && this.getAttackBox().getPosition_x() - this.getAttackBox().getAtkTimer() < p2.getX() + p2.getCanvasLength() && this.blockStayedP2 == false) {
                                    p2.setDamaged(true);
                                }
                            }

                        }
                        else { // 공격 종료
                            this.comboAttackTime++;
                            if (p1.isDamaged() == true) { //플레이어1이 해당 몬스터의 공격을 받았을 경우
                                p1.subHealthCount(1);
                                p1.checkIsDead();
                            }

                            if (p2.isDamaged() == true) {
                                p2.subHealthCount(1);
                                p2.checkIsDead();
                            }

                            //몬스터 공격 정보 초기화
                            this.getAttackBox().setAtkTimer(0);
                            if (this.comboAttackTime == this.comboAttackMax) { // 마지막 공격 끝났을 시 완전 종료
                                this.setAttackDone(true);
                                this.setWaitCount(0);
                                this.getVel().setAttacking(false);
                                this.setAttackRandomNum((int) Math.floor(Math.random() * 10)); // 0~9 정수 난수 발생
                            }
                        }
                    }
                }

            }

        }

    }

    public void move(Player p1, Player p2, int bigX, int smallX, int[] collisonCheckX, int currentStageNum) {

        this.centerPoint = this.getX() + this.getCanvasLength() / 2;
        //몹의 공격 범위 갱신
        this.checkDistance(bigX, smallX);

        this.setX_attackLeft(this.getX() + 10);
        this.setX_attackRight(this.getX() + this.getCanvasLength() - 10);

        this.getAttackBox().setPosition_x(this.getX() + this.getCanvasLength() / 2);

        //살아있고, 공격중이 아니고, 현재 스테이지에 해당되면 움직임
        if (this.isDead() == false && this.getVel().isAttacking() == false && this.stageNum == currentStageNum) {
            this.getVel().setMoving(true);
            for (var i = 0; i <= this.getCanvasLength() - 100; i++) { // 위치 정보 갱신
                collisonCheckX[this.getX() + 50 + i] = 1;
            }

            if (this.moveRandNum >= 8) { //공중 이동중
                this.flyToTarget(p1, p2,collisonCheckX);
            }

            else if (this.moveRandNum < 8) { // 공중이동 아닌 경우 -> 일반 이동
                this.moveRandNum = this.updateMoveRandNum();//1초마다 난수 갱신

                //보스가 플레이어들 사이에 있는 경우. 플레이어가 1명 남았을 때는 이 로직에 진입하지 않음
                if (smallX < this.centerPoint && this.centerPoint < bigX) {
                    if (this.distanceOfBigX <= this.distanceOfSmallX) {// 오른쪽에 있는 플레이어가 더 가까울 경우
                        this.getVel().setLookingRight(true);
                        //오른쪽 플레이어가 공격 범위 내에 있는 경우
                        if (bigX - 200 <= this.getX_attackRight()) {
                            this.getVel().setAttacking(true);
                        }

                        //오른쪽 플레이어에 따라가기 - 속도 2
                        else {
                            collisonCheckX[this.getX() + 50] = -1;
                            collisonCheckX[this.getX() + 51] = -1;
                            collisonCheckX[this.getX() + this.getCanvasLength() - 49] = 1;
                            collisonCheckX[this.getX() + this.getCanvasLength() - 48] = 1;
                            this.addX(2);
                        }
                    }

                    else { // 왼쪽 플레이어가 더 가까운 경우
                        this.getVel().setLookingRight(false);
                        //왼쪽 플레이어가 공격 범위 내에 있는 경우
                        if (this.getX_attackLeft() <= smallX + 200) {
                            this.getVel().setAttacking(true);
                        }

                        //왼쪽 플레이어 따라가기 - 속도 2
                        else {
                            collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
                            collisonCheckX[this.getX() + this.getCanvasLength() - 51] = -1;
                            collisonCheckX[this.getX() + 49] = 1;
                            collisonCheckX[this.getX() + 48] = 1;
                            this.subX(2);
                        }
                    }
                }
                else if (bigX < this.centerPoint) { //전부 왼쪽에 있는 경우 (혹은 혼자)
                    this.getVel().setLookingRight(false);
                    //공격 범위 내에 있는 경우
                    if (this.getX_attackLeft() <= bigX) {
                        this.getVel().setAttacking(true);
                    }

                    //왼쪽 따라가기 - 속도 2
                    else {
                        collisonCheckX[this.getX() + this.getCanvasLength() - 50] = -1;
                        collisonCheckX[this.getX() + this.getCanvasLength() - 51] = -1;
                        collisonCheckX[this.getX() + 49] = 1;
                        collisonCheckX[this.getX() + 48] = 1;
                        this.subX(2);
                    }
                }
                else if (this.centerPoint < smallX) { // 전부 오른쪽에 있는 경우 (혹은 혼자)
                    this.getVel().setLookingRight(true);
                    //공격 범위 내에 있는 경우
                    if (smallX <= this.getX_attackRight()) {
                        this.getVel().setAttacking(true);
                    }

                    //오른쪽  따라가기 - 속도 2
                    else {
                        collisonCheckX[this.getX() + 50] = -1;
                        collisonCheckX[this.getX() + 51] = -1;
                        collisonCheckX[this.getX() + this.getCanvasLength() - 49] = 1;
                        collisonCheckX[this.getX() + this.getCanvasLength() - 48] = 1;
                        this.addX(2);
                    }
                }
            }

        }
        else if (this.isDead() == true) { //몹이 죽은 경우
            for (int i = 0; i <= this.getWidth(); i++) {
                collisonCheckX[this.getX() + i] = -1;
            }
        }

    }
    public void checkAttacked(int atkTimer_p1, int[] collisonCheckX) {//공격이 해당 물체에 가해졌는지 확인
        if ((collisonCheckX[atkTimer_p1] == 1) && (this.getX() <= atkTimer_p1 && atkTimer_p1 <= this.getX() + this.getCanvasLength()) && this.isDead() == false && this.flying == false) {
            this.subHealthCount(1);
            this.setHitCheck(true);
            if (this.getHealthCount() == 0) {
                //console.log('nz1 dead');
                this.setDead(true);
            }
        }
    }

    public void updateAnimation() {
        this.setHitCheck(false);
        if (this.getVel().isMoving() == true) { //움직이는 경우
            if (this.moveRandNum >= 8) {//공중 이동
                if (this.jumpCount <= 60) {//점프 모션
                    if (this.jumpCount < 15) {
                        this.jumpCut = 0;
                    }
                    else if (this.jumpCount < 30) {
                        this.jumpCut = 1;
                    }
                    else if (this.jumpCount < 45) {
                        this.jumpCut = 2;
                    }
                    else if (this.jumpCount <= 60) {
                        this.jumpCut = 3;
                    }
                }

                if (this.fallingCount <= 90) { //착지 모션
                    if (this.fallingCount < 10) {
                        this.landCut = 0;
                    }
                    else if (this.fallingCount < 20) {
                        this.landCut = 1;
                    }
                    else if (this.fallingCount < 30) {
                        this.landCut = 2;
                    }
                    else if (this.fallingCount < 40) {
                        this.landCut = 3;
                    }
                    else if (this.fallingCount < 50) {
                        this.landCut = 4;
                    }
                    else {
                        this.landCut = 5;
                    }
                }
            }
            else {// 일반 이동
                if (this.walkingCount == 15) {
                    this.walkingCount = 0;
                    this.walkingCut++;
                    this.walkingCut = this.walkingCut % this.walkingLoop;
                }
                this.walkingCount++;
            }

        }

        else {//움직이지 않는 경우
            if (this.getVel().isAttacking() == true) {// 공격하는 경우

                if (this.getWaitCount() < 120) { //공격 중 텀
                    if (this.idleCount == 10) {
                        this.idleCount = 0;
                        this.idleCut++;
                        this.idleCut = this.idleCut % this.idleLoop;
                    }
                    else {
                        this.idleCount++;
                    }
                }

                else if (this.getWaitCount() == 120) {
                    if (this.getAttackRandomNum() <= 6) {//일반 공격
                        if (this.attackCount == 10) {
                            this.attackCount = 0;
                            this.attackCut++;
                            this.attackCut = this.attackCut % this.attackLoop;
                        }
                        else {
                            this.attackCount++;
                        }
                    }
                    else {//연속 공격
                        if (this.comboAttackCount == 10) {
                            this.comboAttackCount = 0;
                            this.comboAttackCut++;
                            this.comboAttackCut = this.comboAttackCut % this.comboAttackLoop;
                        }
                        else {
                            this.comboAttackCount++;
                        }
                    }
                }
            }

            else if (this.isDead() == true) { //죽는 경우
                if (this.deathCount == 15 && this.deathCut < this.deathLoop) {
                    this.deathCount = 0;
                    this.deathCut++;
                }
                else if (this.deathCount < 15) {
                    this.deathCount++;
                }
            }

        }
    }
    public void moveObjectRight(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
            collisonCheckX[getX() + 50] = -1;
            collisonCheckX[getX() + 51] = -1;
            collisonCheckX[getX() + getCanvasLength() - 49] = 1;
            collisonCheckX[getX() + getCanvasLength() - 48] = 1;
            addX(2);

            setFixedRange(xMax_left+2, xMax_right+2);
        }

    }

    public void moveObjectLeft(int[] collisonCheckX, int objStageNum, int currentStageNum) {
        if (objStageNum == currentStageNum) {
            collisonCheckX[getX() + 48] = 1;
            collisonCheckX[getX() + 49] = 1;
            collisonCheckX[getX() + getCanvasLength() - 50] = -1;
            collisonCheckX[getX() + getCanvasLength() - 51] = -1;
            subX(2);
            setFixedRange(xMax_left-2, xMax_right-2);
        }

    }
}
