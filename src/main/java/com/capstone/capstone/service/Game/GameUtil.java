package com.capstone.capstone.service.Game;

import com.capstone.capstone.dto.PlayRoomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class GameUtil {
    private int canvasWidth =1920;
    private int canvasHeight = 960;
    public Room createGameState(String userId1, String userId2) {
        log.info("create stage start");
        Map<String,Player> playerMap=new HashMap<>();
        Map<Integer,NormalZombie> normalZombieMap=new HashMap<>();
        Map<Integer,RunningZombie> runningZombieMap=new HashMap<>();
        Map<Integer,CrawlingZombie> crawlingZombieMap=new HashMap<>();
        BackGround bg = new BackGround();
        //constructor(x, y, width, height, CanvasLength)
        Player p1 = new Player(userId1,200, 700, 500, 500, 200, 3);
        p1.setLoops(4, 8, 6, 0);
        Player p2 = new Player(userId2, 500, 700, 500, 500, 200, 3);
        p2.setLoops(4, 8, 6, 0);

        playerMap.put(userId1,p1);
        playerMap.put(userId2,p2);

        NormalZombie nz1 = new NormalZombie(1200, 700, 500, 500, 200, 3);
        nz1.setLoops(6, 7, 4, 8);
        nz1.setFixedRange(1000, 1400);
        nz1.setStunLoop(3);

        RunningZombie rz1 = new RunningZombie(1200, 664, 500, 500, 200, 3);
        rz1.setLoops(4, 4, 5, 6);
        rz1.setFixedRange(1000, 1400);
        rz1.setStunLoop(3);

        CrawlingZombie cz1 = new CrawlingZombie(1500,664, 500, 500, 200, 3);
        cz1.setLoops(4, 4, 4, 7);
        cz1.setFixedRange(1400, 1700);
        cz1.setStunLoop(3);

        normalZombieMap.put(0,nz1);
        runningZombieMap.put(0,rz1);
        crawlingZombieMap.put(0,cz1);

        Zombie zombie = new Zombie(normalZombieMap,runningZombieMap,crawlingZombieMap);
        int[] checkXMap=collisonCheckX(2000,-1);

        Room room=new Room(playerMap,zombie,bg,checkXMap);
        log.info("create stage end");
        return room;
    }

    public int biggerX(int p1_x, int p2_x) {
        if (p1_x >= p2_x) {
            return p1_x;
        }
        else {
            return p2_x;
        }
    }

    public int smallerX(int p1_x, int p2_x) {
        if (p1_x <= p2_x) {
            return p1_x;
        }
        else {
            return p2_x;
        }
    }

    public int[] collisonCheckX(int len, int n){
        int[] intArr=new int[len];
        Arrays.fill(intArr,n);
        return intArr;
    }
    public void updateBlockBox(Player player, double x, double y) {
        player.getBlockBox().setX_right(x + player.getCanvasLength() - 10);
        player.getBlockBox().setX_left(x + 10);
        player.getBlockBox().setY(y + 60);
    }

    public void gameLoop(Room room, String userId1, String userId2) {
        if (room==null) {
            return;
        }

        Player p1 = room.getPlayerMap().get(userId1);
        Player p2 = room.getPlayerMap().get(userId2);
        Zombie z = room.getZombie();
        BackGround bg = room.getBackGround();
        int[] collisonCheckX = room.getCheckXMap();

        int bigX;
        int smallX;

        if (p1.isDead() == false && p2.isDead() == false) {// 둘 다 살아 있는 경우
            bigX = biggerX(p1.getX() + p1.getCanvasLength() - 40, p2.getX() + p2.getCanvasLength() - 40);
            smallX = smallerX(p1.getX() + 40, p2.getX() + 40);

            for (var i = 0; i <= p1.getCanvasLength() - 80; i++) { //플레이어1이 서 있는 곳은 0 으로 표시
                collisonCheckX[p1.getX() + 40 + i] = 0;
            }

            for (var i = 0; i <= p2.getCanvasLength() - 80; i++) { //플레이어2가 서 있는 곳은 0 으로 표시
                collisonCheckX[p2.getX() + 40 + i] = 0;
            }
        }

        else if (p1.isDead() == true && p2.isDead() == false) { // p2만 살아있는 경우
            bigX = p2.getX() + p2.getCanvasLength() - 40;
            smallX = p2.getX() + 40;

            for (var i = 0; i <= p1.getCanvasLength(); i++) { //플레이어1이 서 있던 곳은 -1 으로 표시
                collisonCheckX[p1.getX() + i] = -1;
            }

            for (var i = 0; i <= p2.getCanvasLength() - 80; i++) { //플레이어2가 서 있는 곳은 0 으로 표시
                collisonCheckX[p2.getX() + 40 + i] = 0;
            }
        }

        else if (p1.isDead() == false && p2.isDead() == true) { //p1만 살아있는 경우
            bigX = p1.getX() + p1.getCanvasLength() - 40;
            smallX = p1.getX() + 40;

            for (var i = 0; i <= p1.getCanvasLength() - 80; i++) { //플레이어1이 서 있는 곳은 0 으로 표시
                collisonCheckX[p1.getX() + 40 + i] = 0;
            }

            for (var i = 0; i <= p2.getCanvasLength(); i++) { //플레이어2가 서 있던 곳은 -1 으로 표시
                collisonCheckX[p2.getX() + i] = -1;
            }
        }

        else { //둘 다 죽은 경우 -> 게임 종료
            bigX = -1;
            smallX = -1;
            for (var i = 0; i <= p1.getCanvasLength(); i++) { //플레이어1이 서 있던 곳은 -1 으로 표시
                collisonCheckX[p1.getX() + i] = -1;
            }

            for (var i = 0; i <= p2.getCanvasLength(); i++) { //플레이어2가 서 있는 곳은 -1 으로 표시
                collisonCheckX[p2.getX() + i] = -1;
            }
            room.setActive(false);
        }
        log.info("GameLoop! {}, {}",bigX, smallX);

        updateBlockBox(p1, p1.getX(), p1.getY());
        updateBlockBox(p2, p2.getX(), p2.getY());

//        nz.get(1).move(bigX, smallX, collisonCheckX, currentStageNum);

//        if (nz.get(1).getVel().isAttacking()) {
//            nz.get(1).zombieAttack(p1, p2,collisonCheckX);
//        }
//
//        else if (!nz.get(1).getVel().isAttacking()) {
//            nz.get(1).move(bigX, smallX, collisonCheckX, currentStageNum);
//        }

        for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
            z.getNormalZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if (z.getNormalZombieMap().get(i).getVel().isAttacking() == true || z.getNormalZombieMap().get(i).isAttackDone() == false && z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                z.getNormalZombieMap().get(i).zombieAttack(p1, p2,room.getCheckXMap());
            }

            else if (z.getNormalZombieMap().get(i).isAttackDone() == true && z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {

                z.getNormalZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum());
            }
        }

        for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
            z.getCrawlingZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if (z.getCrawlingZombieMap().get(i).getVel().isAttacking() == true || z.getCrawlingZombieMap().get(i).isAttackDone() == false && z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                z.getCrawlingZombieMap().get(i).zombieAttack(p1, p2,room.getCheckXMap());
            }

            else if (z.getCrawlingZombieMap().get(i).isAttackDone() == true && z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {

                z.getCrawlingZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum());
            }
        }

        for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
            z.getRunningZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if (z.getRunningZombieMap().get(i).getVel().isAttacking() == true || z.getRunningZombieMap().get(i).isAttackDone() == false && z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                z.getRunningZombieMap().get(i).zombieAttack(p1, p2,room.getCheckXMap());
            }

            else if (z.getRunningZombieMap().get(i).isAttackDone() == true && z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {

                z.getRunningZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum());
            }
        }

        //플레이어 1,2 가 맵 이동을 위해 같은 방향으로 움직일때

        //둘 다 왼쪽으로 움직일 때
        if ((p1.getVel().isMovingLeft() && collisonCheckX[p1.getX() + 38] != 1) && (!p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged())) {
            if ((p2.getVel().isMovingLeft() && collisonCheckX[p2.getX() + 38] != 1) && (!p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
                if ((bigX <= 800) && bg.getBg_x() > 0) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingRight(true);
                    bg.subBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
//                    nz.get(1).addX(2);

                    //플레이어 애니메이션 변수
                    // 애니메이션 변수
                    if (p1.getFrameCount() < p1.getRefreshRate()) {
                        p1.addFrameCount(1);
                    }

                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
                        p1.setFrameCount(0);
                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                            p1.setWalkingCount(0);
                        }
                        else {
                            p1.addWalkingCount(1);
                        }
                    }

                    if (p2.getFrameCount() < p2.getRefreshRate()) {
                        p2.addFrameCount(1);
                    }

                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
                        p2.setFrameCount(0);
                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                            p2.setWalkingCount(0);
                        }
                        else {
                            p2.addWalkingCount(1);
                        }
                    }
                }
                else if (p1.getX() > 0 && p2.getX() > 0){ // 그냥 각자 움직이는 경우
                    collisonCheckX[p1.getX() + 38] = 0;
                    collisonCheckX[p1.getX() + 39] = 0;
                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 40] = -1;
                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 41] = -1;
                    p1.subX(2);
                    p1.getAttackBox().subPosition_x(2);

                    // 애니메이션 변수
                    if (p1.getFrameCount() < p1.getRefreshRate()) {
                        p1.addFrameCount(1);
                    }

                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
                        p1.setFrameCount(0);
                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                            p1.setWalkingCount(0);
                        }
                        else {
                            p1.addWalkingCount(1);
                        }
                    }

                    collisonCheckX[p2.getX() + 38] = 0;
                    collisonCheckX[p2.getX() + 39] = 0;
                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 40] = -1;
                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 41] = -1;
                    p2.subX(2);
                    p2.getAttackBox().subPosition_x(2);

                    // 애니메이션 변수
                    if (p2.getFrameCount() < p2.getRefreshRate()) {
                        p2.addFrameCount(1);
                    }

                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
                        p2.setFrameCount(0);
                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                            p2.setWalkingCount(0);
                        }
                        else {
                            p2.addWalkingCount(1);
                        }
                    }

                }
            }
        }

        //둘 다 오른쪽으로 움직일 때
        if ((p1.getVel().isMovingRight() && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (!p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged())) {
            if ((p2.getVel().isMovingRight() && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (!p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
                if ((smallX >= (canvasWidth - 700)) && bg.getBg_x() < bg.getBg_xMax()) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingLeft(true);
                    bg.addBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
//                    nz.get(1).subX(2);

                    // 플레이어 애니메이션 변수
                    if (p1.getFrameCount() < p1.getRefreshRate()) {
                        p1.addFrameCount(1);
                    }

                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
                        p1.setFrameCount(0);
                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                            p1.setWalkingCount(0);
                        }
                        else {
                            p1.addWalkingCount(1);
                        }
                    }

                    if (p2.getFrameCount() < p2.getRefreshRate()) {
                        p2.addFrameCount(1);
                    }

                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
                        p2.setFrameCount(0);
                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                            p2.setWalkingCount(0);
                        }
                        else {
                            p2.addWalkingCount(1);
                        }
                    }
                }
                else if (p1.getX() + p1.getCanvasLength() < canvasWidth && p2.getX() + p2.getCanvasLength() < canvasWidth){ // 그냥 각자 움직이는 경우
                    collisonCheckX[p1.getX() + 40] = -1;
                    collisonCheckX[p1.getX() + 41] = -1;
                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 39] = 0;
                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] = 0;
                    p1.addX(2);
                    p1.getAttackBox().addPosition_x(2);

                    // 애니메이션 변수
                    if (p1.getFrameCount() < p1.getRefreshRate()) {
                        p1.addFrameCount(1);
                    }

                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
                        p1.setFrameCount(0);
                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                            p1.setWalkingCount(0);
                        }
                        else {
                            p1.addWalkingCount(1);
                        }
                    }

                    collisonCheckX[p2.getX() + 40] = -1;
                    collisonCheckX[p2.getX() + 41] = -1;
                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 39] = 0;
                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] = 0;
                    p2.addX(2);
                    p2.getAttackBox().addPosition_x(2);

                    // 애니메이션 변수
                    if (p2.getFrameCount() < p2.getRefreshRate()) {
                        p2.addFrameCount(1);
                    }

                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
                        p2.setFrameCount(0);
                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                            p2.setWalkingCount(0);
                        }
                        else {
                            p2.addWalkingCount(1);
                        }
                    }

                }
            }
        }

        //플레이어1 이 가만히 서 있는 경우
        if (p1.getVel().isAttacking() == false && p1.getVel().isMoving() == false && p1.getVel().isBlocking() == false) {
            if (p1.getFrameCount() < p1.getRefreshRate()) {
                p1.addFrameCount(1);
            }
            else if (p1.getFrameCount() == p1.getRefreshRate()) {
                p1.setFrameCount(0);

                if (p1.getIdleCount() == p1.getIdleLoop() - 1) {
                    p1.setIdleCount(0);
                }

                else {
                    p1.addIdleCount(1);
                }
            }
        }

        //플레이어1이 잡힌 경우
        if (p1.isGrabbed() == true && p1.isDead() == false) {
            if (p1.getInteractionCount() == 20) {
                p1.setInteractionCount(0);
                p1.addInteractionCut(1);
                p1.setInteractionCut(p1.getInteractionCut() % p1.getInteractionLoop());
            }
            else {
                p1.addInteractionCount(1);
            }
        }

        //플래이어1 이 왼쪽으로 이동하는 경우
        if ((p1.getVel().isMovingLeft() == true && collisonCheckX[p1.getX() + 38] != 1) && (p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) {
            log.info("p1 go left");
            if (p1.getX() > 0 && !p2.getVel().isMovingLeft()) {
                collisonCheckX[p1.getX() + 38] = 0;
                collisonCheckX[p1.getX() + 39] = 0;
                collisonCheckX[p1.getX() + p1.getCanvasLength() - 40] = -1;
                collisonCheckX[p1.getX() + p1.getCanvasLength() - 41] = -1;
                p1.subX(2);
                p1.getAttackBox().subPosition_x(2);

                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                }

                else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    }
                    else {
                        p1.addWalkingCount(1);
                    }
                }
            }
        }

        //플래이어1이 오른쪽으로 이동하는 경우
        if ((p1.getVel().isMovingRight() == true && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) {
                int canvas_width=1920;
            log.info("p1 go right");
            if (p1.getX() < canvas_width  - p1.getCanvasLength() && !p2.getVel().isMovingRight()) {//canvas_width
                collisonCheckX[p1.getX() + 40] = -1;
                collisonCheckX[p1.getX() + 41] = -1;
                collisonCheckX[p1.getX() + p1.getCanvasLength() - 39] = 0;
                collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] = 0;
                p1.addX(2);
                p1.getAttackBox().addPosition_x(2);

                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                }

                else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    }
                    else {
                        p1.addWalkingCount(1);
                    }
                }
            }
        }

        //플레이어1이 공격 중인 경우
        if (p1.getVel().isAttacking() == true) {
            //오른쪽 공격
            if(p1.getVel().isLookingRight() == true) {
                if (p1.getAttackTimer() >= p1.getAttackBox().getWidth()) {
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getNormalZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getCrawlingZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getRunningZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    p1.getVel().setAttacking(false);
                    p1.setAttackTimer(0);
                }
                else {
                    p1.addAttackTimer(4);
                }
            }
            //왼쪽 공격
            else if(p1.getVel().isLookingRight() == false) {
                if (p1.getAttackTimer() >= p1.getAttackBox().getWidth()) {
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getNormalZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getCrawlingZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getRunningZombieMap().get(i).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                        }
                    }
                    p1.getVel().setAttacking(false);
                    p1.setAttackTimer(0);
                }
                else {
                    p1.addAttackTimer(4);
                }
            }
        }
        //플레이어1 공격 애니메이션 변수 attackFrame 이 30이 될때 마다 장면이 바뀜
        if (p1.getVel().isAttacking_motion() == true) {

            if (p1.getAttackFrame() < 30 && (p1.getAttackCount() <= 1)) {
                p1.addAttackFrame(6);
            }

            else if (p1.getAttackFrame() < 30 && (p1.getAttackCount() == 2)) {
                p1.addAttackFrame(3);
            }
            else if (p1.getAttackFrame() < 30 && (p1.getAttackCount() <= 4)) {
                p1.addAttackFrame(5);
            }
            else if (p1.getAttackFrame() < 30 && (p1.getAttackCount() == 5)) {
                p1.addAttackFrame(3);
            }

            else if(p1.getAttackFrame() == 30) {
                p1.setAttackFrame(0);
                if (p1.getAttackCount() == p1.getAttackLoop() - 1) {
                    p1.setAttackCount(0);
                    p1.getVel().setAttacking_motion(false); //공격 동작 종료
                }
                else {
                    p1.addAttackCount(1);
                }
            }

        }

        //플레이어1이 공격에 맞은 경우
        if (p1.isDamaged()) {
            log.info("p1 damage");
            p1.addDamagedCount(1);
            if (p1.getDamagedCount() == 60) {
                p1.setDamaged(false);
                p1.setDamagedCount(0);
            }
        }

        //플레이어2가 가만히 서 있는 경우
        if (p2.getVel().isAttacking() == false && p2.getVel().isMoving() == false && p2.getVel().isBlocking() == false) {
            if (p2.getFrameCount() < p2.getRefreshRate()) {
                p2.addFrameCount(1);
            }
            else if (p2.getFrameCount() == p2.getRefreshRate()) {
                p2.setFrameCount(0);

                if (p2.getIdleCount() == p2.getIdleLoop() - 1) {
                    p2.setIdleCount(0);
                }

                else {
                    p2.addIdleCount(1);
                }
            }
        }

        //플레이어2가 잡힌 경우
        if (p2.isGrabbed() == true && p2.isDead() == false) {
            if (p2.getInteractionCount() == 20) {
                p2.setInteractionCount(0);
                p2.addInteractionCut(1);
                p2.setInteractionCut(p2.getInteractionCut() % p2.getInteractionLoop());
            }
            else {
                p2.addInteractionCount(1);
            }
        }

        //플래이어2 가 왼쪽으로 이동하는 경우
        if ((p2.getVel().isMovingLeft() == true && collisonCheckX[p2.getX() + 38] != 1) && (p2.getVel().isAttacking() == false && p2.getVel().isBlocking() == false && p2.isDamaged() == false)) {
            log.info("p2 go left");
            if (p2.getX() > 0 && !p1.getVel().isMovingLeft()) {
                collisonCheckX[p2.getX() + 38] = 0;
                collisonCheckX[p2.getX() + 39] = 0;
                collisonCheckX[p2.getX() + p2.getCanvasLength() - 40] = -1;
                collisonCheckX[p2.getX() + p2.getCanvasLength() - 41] = -1;
                p2.subX(2);
                p2.getAttackBox().subPosition_x(2);

                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                }

                else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    }
                    else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }

        //플래이어2가 오른쪽으로 이동하는 경우
        if ((p2.getVel().isMovingRight() && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (!p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
            log.info("p1 go right");
            int canvas_width=1920;
            if (p2.getX() < canvas_width - p2.getCanvasLength() && !p1.getVel().isMovingRight()) {
                collisonCheckX[p2.getX() + 40] = -1;
                collisonCheckX[p2.getX() + 41] = -1;
                collisonCheckX[p2.getX() + p2.getCanvasLength() - 39] = 0;
                collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] = 0;
                p2.addX(2);
                p2.getAttackBox().addPosition_x(2);

                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                }

                else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    }
                    else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }

        //플레이어2가 공격 중인 경우
        if (p2.getVel().isAttacking()) {
            //오른쪽 공격
            if(p2.getVel().isLookingRight()) {
                if (p2.getAttackTimer() >= p2.getAttackBox().getWidth()) {
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getNormalZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getCrawlingZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getRunningZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    p2.getVel().setAttacking(false);
                    p2.setAttackTimer(0);
                }
                else {
                    p2.addAttackTimer(4);
                }
            }
            //왼쪽 공격
            else if(!p2.getVel().isLookingRight()) {
                if (p2.getAttackTimer() >= p2.getAttackBox().getWidth()) {
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getNormalZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getCrawlingZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                            z.getRunningZombieMap().get(i).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                        }
                    }
                    p2.getVel().setAttacking(false);
                    p2.setAttackTimer(0);
                }
                else {
                    p2.addAttackTimer(4);
                }
            }
        }

        //플레이어2 공격 애니메이션 변수 attackFrame 이 30이 될때 마다 장면이 바뀜
        if (p2.getVel().isAttacking_motion()) {

            if (p2.getAttackFrame() < 30 && (p2.getAttackCount() <= 1)) {
                p2.addAttackFrame(6);
            }

            else if (p2.getAttackFrame() < 30 && (p2.getAttackCount() == 2)) {
                p2.addAttackFrame(3);
            }
            else if (p2.getAttackFrame() < 30 && (p2.getAttackCount() <= 4)) {
                p2.addAttackFrame(5);
            }
            else if (p2.getAttackFrame() < 30 && (p2.getAttackCount() == 5)) {
                p2.addAttackFrame(3);
            }

            else if(p2.getAttackFrame() == 30) {
                p2.setAttackFrame(0);
                if (p2.getAttackCount() == p2.getAttackLoop() - 1) {
                    p2.setAttackCount(0);
                    p2.getVel().setAttacking_motion(false); //공격 동작 종료
                }
                else {
                    p2.addAttackCount(1);
                }
            }
        }

        //플레이어2이 공격에 맞은 경우
        if (p2.isDamaged()) {
            log.info("p2 damage {}",p2.getDamagedCount());
            p2.addDamagedCount(1);
            if (p2.getDamagedCount() == 60) {
                p2.setDamaged(false);
                p2.setDamagedCount(0);
            }
        }


        //NormalZombie 애니메이션 변수
//        if (!nz.get(1).isDead() && nz.get(1).getStageNum() == room.getCurrentStageNum()) {
//            if (!nz.get(1).getVel().isMoving()) {
//                //플레이어가 해당 몬스터의 공격을 막았을 경우
//                if (nz.get(1).isStunned()) {
//                    if (nz.get(1).getStunCount() % 40 == 39) {
//                        nz.get(1).addStunAnimaitonCount(1);
//                        nz.get(1).setStunAnimaitonCount(nz.get(1).getStunAnimaitonCount() % nz.get(1).getStunLoop());
//                    }
//                }
//                //텀이 지나고 다시 공격하는 경우
//                else if (nz.get(1).getVel().isAttacking() && nz.get(1).getWaitCount() == 30) {
//
//                    if (nz.get(1).getAttackFrame() < 10) {
//                        nz.get(1).addAttackFrame(1);
//                    }
//                    else if (nz.get(1).getAttackFrame() == 10) {
//                        nz.get(1).setAttackFrame(0);
//                        if (nz.get(1).getAttackCount() < nz.get(1).getAttackLoop() - 1) {
//                            nz.get(1).addAttackCount(1);
//                        }
//                        else {
//                            nz.get(1).setAttackCount(0);
//                        }
//                    }
//                }
//                //가만히 서 있는 경우
//                else {
//                    if(nz.get(1).getIdleCount() == 30) {
//                        nz.get(1).setIdleCount(0);
//                        nz.get(1).addIdleCut(1);
//                        nz.get(1).setIdleCut(nz.get(1).getIdleCut() % nz.get(1).getIdleLoop());
//                    }
//                    nz.get(1).addIdleCount(1);
//                }
//            }
//
//            else if (nz.get(1).getVel().isMoving()) {
//                if (nz.get(1).getWalkingCount() == 30) {
//                    nz.get(1).setWalkingCount(0);
//                    nz.get(1).addWalkingCut(1);
//                    nz.get(1).setWalkingCut(nz.get(1).getWalkingCut() % nz.get(1).getWalkingLoop());
//                }
//                nz.get(1).addWalkingCount(1);
//            }
//        }
//        else if (nz.get(1).isDead()) {
//            if (nz.get(1).getDeathFrame() < 30 && nz.get(1).getDeathCount() < 7) {
//                nz.get(1).addDeathFrame(1);
//            }
//            else if (nz.get(1).getDeathFrame() == 30 && nz.get(1).getDeathCount() < 7) {
//                nz.get(1).setDeathFrame(0);
//                nz.get(1).addDeathCount(1);
//            }
//            else {
//                nz.get(1).setDeathCount(7);
//            }
//        }
//
        //스테이지 이동 로직 -> 오른쪽으로만 이동
        log.info("bgx: {}, bgmx: {}, bigx: {}, canvaswidth: {}",bg.getBg_x(), bg.getBg_xMax(), bigX+40, canvasWidth-10);
        if ((bg.getBg_x() == bg.getBg_xMax() || bg.getBg_x()==0) && bigX + 40 == canvasWidth - 10) { //둘 중 한명이 맵 오른쪽 끝까지 가는 경우
            p1.initPlayerPoint(100,canvasWidth);
            p2.initPlayerPoint(300,canvasWidth);
            room.addCurrentStageNum(1);
            bg.setBg_x(0);
        }
    }



    public void getUpdatedVelocityDown(PlayRoomMessage dto, Player player) { // 키 눌렀을때(누르고 있을 때) 이벤트
        int keyCode=dto.getKeyCode();
        log.info("keyCode: {}",keyCode);
        switch (keyCode) {
            //a -> 왼쪽 이동
            case 65:
                log.info("65");
                if (!player.isDamaged() && !player.getVel().isAttacking() && !player.getVel().isBlocking() && !player.isDead()) {

                    // 보고 있는 방향
                    player.getVel().setLookingRight(false);

                    // 공격하고 있는지 여부
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);

                    //움직이고 있는지 여부
                    player.getVel().setMoving(true);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(true);

                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);

                    player.getVel().setInteraction(false);
                }
                break;
            //d -> 오른쪽 이동
            case 68:
                log.info("68");
                if (!player.isDamaged() && !player.getVel().isAttacking() && !player.getVel().isBlocking() && !player.isDead()) {

                    // 보고 있는 방향
                    player.getVel().setLookingRight(true);

                    // 공격하고 있는지 여부
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);

                    //움직이고 있는지 여부
                    player.getVel().setMoving(true);
                    player.getVel().setMovingRight(true);
                    player.getVel().setMovingLeft(false);

                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);

                    player.getVel().setInteraction(false);
                }
                break;
            // f -> 공격
            case 70:
                log.info("70");
                if (!player.isDamaged() && !player.isDead()) {
                    if (player.getVel().isLookingRight()) { // 오른쪽
                        // 보고 있는 방향
                        player.getVel().setLookingRight(true);
                    } else { //왼쪽
                        // 보고 있는 방향
                        player.getVel().setLookingRight(false);
                    }
                    // 공격하고 있는지 여부
                    player.getVel().setAttacking(true);
                    player.getVel().setAttacking_motion(true);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(false);
                }
                break;
            //r -> 방어
            case 82:
                log.info("82");
                if (player.getVel().isLookingRight() && !player.isDead()) { //오른쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(true);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(true);
                    player.getVel().setInteraction(false);
                } else if(!player.isDead()){  //왼쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(false);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(true);
                    player.getVel().setInteraction(false);
                }
                // 공격하고 있는지 여부

                break;
            //e
            case 69:
                if (player.getVel().isLookingRight() && !player.isDead()) { //오른쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(true);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(true);

                } else if(!player.isDead()){  //왼쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(false);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(true);
                }
                // 공격하고 있는지 여부

                break;
        }

    }

    public void getUpdatedVelocityUp(PlayRoomMessage dto, Player player) { // 키 눌렀다가 땠을 때 이벤트
        int keyCode=dto.getKeyCode();
        log.info("keyCode: {}",keyCode);
        switch (keyCode) {
            //a -> 왼쪽 이동하다가 멈춤
            case 65:
                // 보고 있는 방향
                player.getVel().setLookingRight(false);

                // 공격하고 있는지 여부
                player.getVel().setAttacking(false);
                player.getVel().setAttacking_motion(false);

                //움직이고 있는지 여부
                player.getVel().setMoving(false);
                player.getVel().setMovingRight(false);
                player.getVel().setMovingLeft(false);

                //방어하고 있는지 여부
                player.getVel().setBlocking(false);
                player.getVel().setInteraction(false);
                break;
            //d -> 오른쪽 이동하다가 멈추는 경우
            case 68:
                // 보고 있는 방향
                player.getVel().setLookingRight(true);

                // 공격하고 있는지 여부
                player.getVel().setAttacking(false);
                player.getVel().setAttacking_motion(false);

                //움직이고 있는지 여부
                player.getVel().setMoving(false);
                player.getVel().setMovingRight(false);
                player.getVel().setMovingLeft(false);

                //방어하고 있는지 여부
                player.getVel().setBlocking(false);
                player.getVel().setInteraction(false);
                break;

            //r -> 방어 했다가 푸는 경우
            case 82:
                if (player.getVel().isLookingRight()) { //오른쪽 방어였던 경우
                    // 보고 있는 방향
                    player.getVel().setLookingRight(true);

                    // 공격하고 있는지 여부
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);

                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);

                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(false);
                }

                else {
                    // 보고 있는 방향
                    player.getVel().setLookingRight(false);

                    // 공격하고 있는지 여부
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);

                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);

                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(false);
                }
                break;
            //e
            case 69:
                if (player.getVel().isLookingRight() && !player.isDead()) { //오른쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(true);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(false);

                } else if(!player.isDead()){  //왼쪽 방어
                    // 보고 있는 방향
                    player.getVel().setLookingRight(false);
                    player.getVel().setAttacking(false);
                    player.getVel().setAttacking_motion(false);
                    //움직이고 있는지 여부
                    player.getVel().setMoving(false);
                    player.getVel().setMovingRight(false);
                    player.getVel().setMovingLeft(false);
                    //방어하고 있는지 여부
                    player.getVel().setBlocking(false);
                    player.getVel().setInteraction(false);
                }
                // 공격하고 있는지 여부

                break;
        }
    }

}
