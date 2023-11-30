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
        log.debug("create stage start");
        Map<String,Player> playerMap=new HashMap<>();
        Map<Integer,StuckedZombie> stuckedZombieMap = new HashMap<>();
        Map<Integer,NormalZombie> normalZombieMap=new HashMap<>();
        Map<Integer,RunningZombie> runningZombieMap=new HashMap<>();
        Map<Integer,CrawlingZombie> crawlingZombieMap=new HashMap<>();
        Map<Integer,BossZombie> bossZombieMap=new HashMap<>();
        BackGround bg = new BackGround();
        //constructor(x, y, width, height, CanvasLength)
        Player p1 = new Player(userId1,200, 620, 500, 500, 200, 3);
        p1.setLoops(4, 8, 6, 0);
        Player p2 = new Player(userId2, 500, 620, 500, 500, 200, 3);
        p2.setLoops(4, 8, 6, 0);

        playerMap.put(userId1,p1);
        playerMap.put(userId2,p2);
        //zombies
        //stage0
        StuckedZombie sz = new StuckedZombie(1830, 560, 500, 500, 200);


        //stage1 일반 좀비 4마리. 1500, 3000, 4500, 7000

        NormalZombie nz1 = new NormalZombie(1300, 620, 500, 500, 200,3);
        nz1.setLoops(6, 7, 4, 8);
        nz1.setFixedRange(1000, 1600);
        nz1.setStunLoop(3);
        nz1.setSfxIndex(0); //일반 좀비 중 첫 번째
        nz1.setStageNum(1);

        NormalZombie nz2 = new NormalZombie(2100, 620, 500, 500, 200, 3);
        nz2.setLoops(6, 7, 4, 8);
        nz2.setFixedRange(1800, 2400);
        nz2.setStunLoop(3);
        nz2.setSfxIndex(1);
        nz2.setStageNum(1);

        NormalZombie nz3 = new NormalZombie(3000, 620, 500, 500, 200, 3);
        nz3.setLoops(6, 7, 4, 8);
        nz3.setFixedRange(2700, 3300);
        nz3.setStunLoop(3);
        nz3.setSfxIndex(2);
        nz3.setStageNum(1);

        NormalZombie nz4 = new NormalZombie(4100, 620, 500, 500, 200, 3);
        nz4.setLoops(6, 7, 4, 8);
        nz4.setFixedRange(3800, 4400);
        nz4.setStunLoop(3);
        nz4.setSfxIndex(3);
        nz4.setStageNum(1);


        //stage2 일반 좀비 5마리

        NormalZombie nz5 = new NormalZombie(1000, 620, 500, 500, 200, 3);
        nz5.setLoops(6, 7, 4, 8);
        nz5.setFixedRange(700, 1300);
        nz5.setStunLoop(3);
        nz5.setSfxIndex(4);
        nz5.setStageNum(2);

        NormalZombie nz6 = new NormalZombie(1900, 620, 500, 500, 200, 3);
        nz6.setLoops(6, 7, 4, 8);
        nz6.setFixedRange(1600, 2200);
        nz6.setStunLoop(3);
        nz6.setSfxIndex(5);
        nz6.setStageNum(2);

        NormalZombie nz7 = new NormalZombie(2800, 620, 500, 500, 200, 3);
        nz7.setLoops(6, 7, 4, 8);
        nz7.setFixedRange(2500, 3100);
        nz7.setStunLoop(3);
        nz7.setSfxIndex(6);
        nz7.setStageNum(2);

        NormalZombie nz8 = new NormalZombie(3900, 620, 500, 500, 200, 3);
        nz8.setLoops(6, 7, 4, 8);
        nz8.setFixedRange(3600, 4200);
        nz8.setStunLoop(3);
        nz8.setSfxIndex(7);
        nz8.setStageNum(2);

        NormalZombie nz9 = new NormalZombie(4800, 620, 500, 500, 200, 3);
        nz9.setLoops(6, 7, 4, 8);
        nz9.setFixedRange(4500, 5100);
        nz9.setStunLoop(3);
        nz9.setSfxIndex(8);
        nz9.setStageNum(2);


        //stage3 원거리 좀비 3마리
        CrawlingZombie cz1 = new CrawlingZombie(10000+400, 620, 500, 500, 200, 3);
        cz1.setLoops(4, 4, 4, 7);
        cz1.setFixedRange(10000+300, 10000+500);
        cz1.setStunLoop(3);
        cz1.setSfxIndex(0);
        cz1.setStageNum(3);

        CrawlingZombie cz2 = new CrawlingZombie(10000-1900, 620, 500, 500, 200, 3);
        cz2.setLoops(4, 4, 4, 7);
        cz2.setFixedRange(10000-2000, 10000-1800);
        cz2.setStunLoop(3);
        cz2.setSfxIndex(1);
        cz2.setStageNum(3);

        CrawlingZombie cz3 = new CrawlingZombie(10000-3600, 620, 500, 500, 200, 3);
        cz3.setLoops(4, 4, 4, 7);
        cz3.setFixedRange(10000-3700, 10000-3500);
        cz3.setStunLoop(3);
        cz3.setSfxIndex(2);
        cz3.setStageNum(3);

        //stage4


        //stage5
        RunningZombie rz1 = new RunningZombie(1000, 620, 500, 500, 200, 3);
        rz1.setLoops(4, 4, 5, 6);
        rz1.setFixedRange(700, 1300);
        rz1.setStunLoop(3);
        rz1.setSfxIndex(0);
        rz1.setStageNum(5);

        RunningZombie rz2 = new RunningZombie(2500, 620, 500, 500, 200, 3);
        rz2.setLoops(4, 4, 5, 6);
        rz2.setFixedRange(2200, 2800);
        rz2.setStunLoop(3);
        rz2.setSfxIndex(1);
        rz2.setStageNum(5);

        RunningZombie rz3 = new RunningZombie(4000, 620, 500, 500, 200, 3);
        rz3.setLoops(4, 4, 5, 6);
        rz3.setFixedRange(3700, 4300);
        rz3.setStunLoop(3);
        rz3.setSfxIndex(2);
        rz3.setStageNum(5);

        //stage6 final
        BossZombie bz = new BossZombie(1700, 520, 750, 750, 300, 7);
        bz.setStageNum(6);

        stuckedZombieMap.put(0,sz);

        normalZombieMap.put(0,nz1);
        normalZombieMap.put(1,nz2);
        normalZombieMap.put(2,nz3);
        normalZombieMap.put(3,nz4);
        normalZombieMap.put(4,nz5);
        normalZombieMap.put(5,nz6);
        normalZombieMap.put(6,nz7);
        normalZombieMap.put(7,nz8);
        normalZombieMap.put(8,nz9);

        runningZombieMap.put(0,rz1);
        runningZombieMap.put(1,rz2);
        runningZombieMap.put(2,rz3);
        crawlingZombieMap.put(0,cz1);
        crawlingZombieMap.put(1,cz2);
        crawlingZombieMap.put(2,cz3);

        bossZombieMap.put(0,bz);

        Zombie zombies = new Zombie(normalZombieMap,runningZombieMap,crawlingZombieMap,stuckedZombieMap,bossZombieMap);
        int[] checkXMap=makeCollisonCheckX(12000,-1);

        Room room=new Room(playerMap,zombies,bg, checkXMap);
        room.setActive(true);
        log.debug("create stage end");
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


//    public void moveObjectRight(int[] collisonCheckX, NormalZombie obj) {
//        if (obj.isDead() == false) {
//            collisonCheckX[obj.getX() + 50] = -1;
//            collisonCheckX[obj.getX() + 51] = -1;
//            collisonCheckX[obj.getX() + obj.getCanvasLength() - 49] = 1;
//            collisonCheckX[obj.getX() + obj.getCanvasLength() - 48] = 1;
//            obj.addX(2);
//        }
//
//    }
//
//    public void moveObjectLeft(int[] collisonCheckX, NormalZombie obj) {
//        if (obj.isDead() == false) {
//            collisonCheckX[obj.getX() + 48] = 1;
//            collisonCheckX[obj.getX() + 49] = 1;
//            collisonCheckX[obj.getX() + obj.getCanvasLength() - 50] = -1;
//            collisonCheckX[obj.getX() + obj.getCanvasLength() - 51] = -1;
//            obj.subX(2);
//        }
//
//    }

    public int[] makeCollisonCheckX(int len, int n){
        int[] intArr=new int[len];
        Arrays.fill(intArr,n);
        return intArr;
    }
    public void updateBlockBox(Player player) {
        player.getBlockBox().setX_right(player.getX() + player.getCanvasLength() - 10);
        player.getBlockBox().setX_left(player.getX() + 10);
        player.getBlockBox().setY(player.getY() + 60);
    }

    public void updateAttackBox(Player player){
        player.setPositionX(player.getX(), player.getCanvasLength());
    }

    public void gameLoop(Room room, String userId1, String userId2) {
        if (room == null) {
            return;
        }

        Player p1 = room.getPlayerMap().get(userId1);
        Player p2 = room.getPlayerMap().get(userId2);
        Zombie z = room.getZombie();
//        Map<Integer,StuckedZombie> stuckedZombieMap = room.getZombie().getStuckedZombieMap();
//        Map<Integer,NormalZombie> normalZombieMap=room.getZombie().getNormalZombieMap();
//        Map<Integer,RunningZombie> runningZombieMap=room.getZombie().getRunningZombieMap();
//        Map<Integer,CrawlingZombie> crawlingZombieMap=room.getZombie().getCrawlingZombieMap();
//        Map<Integer,BossZombie> bossZombieMap=room.getZombie().getBossZombieMap();
        BackGround bg = room.getBackGround();
//        int[] collisonCheckX = room.getCheckXMap();
        int[] collisonCheckX = makeCollisonCheckX(12000,-1);
        int[] arr_stageChangePoint = {0, 0, 0, 1, 0, 0, 0};
        int bigX;
        int smallX;

        if (p1.isDead() == false && p2.isDead() == false) {// 둘 다 살아 있는 경우
            bigX = biggerX(p1.getX() + p1.getCanvasLength() - 40, p2.getX() + p2.getCanvasLength() - 40);
            smallX = smallerX(p1.getX() + 40, p2.getX() + 40);

            for (int i = 0; i <= p1.getCanvasLength() - 80; i++) { //플레이어1이 서 있는 곳은 0 으로 표시
                collisonCheckX[p1.getX() + 40 + i] = 0;
            }

            for (int i = 0; i <= p2.getCanvasLength() - 80; i++) { //플레이어2가 서 있는 곳은 0 으로 표시
                collisonCheckX[p2.getX() + 40 + i] = 0;
            }
        } else if (p1.isDead() == true && p2.isDead() == false) { // p2만 살아있는 경우
            bigX = p2.getX() + p2.getCanvasLength() - 40;
            smallX = p2.getX() + 40;

//            for (int i = 0; i <= p1.getCanvasLength(); i++) { //플레이어1이 서 있던 곳은 -1 으로 표시
//                collisonCheckX[p1.getX() + i] = -1;
//            }

            for (int i = 0; i <= p2.getCanvasLength() - 80; i++) { //플레이어2가 서 있는 곳은 0 으로 표시
                collisonCheckX[p2.getX() + 40 + i] = 0;
            }
        } else if (p1.isDead() == false && p2.isDead() == true) { //p1만 살아있는 경우
            bigX = p1.getX() + p1.getCanvasLength() - 40;
            smallX = p1.getX() + 40;

            for (int i = 0; i <= p1.getCanvasLength() - 80; i++) { //플레이어1이 서 있는 곳은 0 으로 표시
                collisonCheckX[p1.getX() + 40 + i] = 0;
            }

//            for (int i = 0; i <= p2.getCanvasLength(); i++) { //플레이어2가 서 있던 곳은 -1 으로 표시
//                collisonCheckX[p2.getX() + i] = -1;
//            }
        } else { //둘 다 죽은 경우 -> 게임 종료
            bigX = -1;
            smallX = -1;
//            for (int i = 0; i <= p1.getCanvasLength(); i++) { //플레이어1이 서 있던 곳은 -1 으로 표시
//                collisonCheckX[p1.getX() + i] = -1;
//            }
//
//            for (int i = 0; i <= p2.getCanvasLength(); i++) { //플레이어2가 서 있는 곳은 -1 으로 표시
//                collisonCheckX[p2.getX() + i] = -1;
//            }
            room.setActive(false);
        }
        log.debug("GameLoop! {}, {}", bigX, smallX);

        if (room.getCurrentStageNum() == 4) {//편의점 스테이지 도달 시 회복
            if (p1.isDead() == false) {
                p1.setHealthCount(3);
            }
            if (p2.isDead() == false) {
                p2.setHealthCount(3);
            }
        }

        updateBlockBox(p1);
        updateBlockBox(p2);
        updateAttackBox(p1);
        updateAttackBox(p2);

//        nz.get(1).move(bigX, smallX, collisonCheckX, currentStageNum);

//        if (nz.get(1).getVel().isAttacking()) {
//            nz.get(1).zombieAttack(p1, p2,collisonCheckX);
//        }
//
//        else if (!nz.get(1).getVel().isAttacking()) {
//            nz.get(1).move(bigX, smallX, collisonCheckX, currentStageNum);
//        }

        // 좌표 세팅

        for (int i = 0; i<z.getNormalZombieMap().size(); i++){
            if ((!z.getNormalZombieMap().get(i).isDead()) && z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                for (int k = 0; k <= z.getNormalZombieMap().get(i).getCanvasLength() - 100; k++) { // 위치 정보 갱신
                    collisonCheckX[z.getNormalZombieMap().get(i).getX() + 50 + k] = 1;
                }
            }
        }
        for (int i = 0; i<z.getCrawlingZombieMap().size(); i++){
            if ((!z.getCrawlingZombieMap().get(i).isDead()) && z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                for (int k = 0; k <= z.getCrawlingZombieMap().get(i).getCanvasLength() - 100; k++) { // 위치 정보 갱신
                    collisonCheckX[z.getCrawlingZombieMap().get(i).getX() + 50 + k] = 1;
                }
            }
        }
        for (int i = 0; i<z.getRunningZombieMap().size(); i++){
            if ((!z.getRunningZombieMap().get(i).isDead()) && z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                for (int k = 0; k <= z.getRunningZombieMap().get(i).getCanvasLength() - 100; k++) { // 위치 정보 갱신
                    collisonCheckX[z.getRunningZombieMap().get(i).getX() + 50 + k] = 1;
                }
            }
        }
        if ((!z.getBossZombieMap().get(0).isDead()) && z.getBossZombieMap().get(0).getStageNum() == room.getCurrentStageNum() && !z.getBossZombieMap().get(0).isFlying()) {
            for (int k = 0; k <= z.getBossZombieMap().get(0).getCanvasLength() - 100; k++) { // 위치 정보 갱신
                collisonCheckX[z.getBossZombieMap().get(0).getX() + 50 + k] = 1;
            }
        }
        if ((!z.getStuckedZombieMap().get(0).isDead()) && z.getStuckedZombieMap().get(0).getStageNum() == room.getCurrentStageNum()) {
            for (int k = 0; k <= z.getStuckedZombieMap().get(0).getCanvasLength() - 100; k++) { // 위치 정보 갱신
                collisonCheckX[z.getStuckedZombieMap().get(0).getX() + 50 + k] = 1;
            }
        }


        z.getStuckedZombieMap().get(0).attack(collisonCheckX, p1, p2);

        for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
            z.getNormalZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if ((z.getNormalZombieMap().get(i).getVel().isAttacking() == true || z.getNormalZombieMap().get(i).isAttackDone() == false) && z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum()) {
                z.getNormalZombieMap().get(i).zombieAttack(p1, p2, collisonCheckX);
            } else if ((z.getNormalZombieMap().get(i).isAttackDone() == true && z.getNormalZombieMap().get(i).getStageNum() == room.getCurrentStageNum())) {
                z.getNormalZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum());
            }
        }

        for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
            z.getCrawlingZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if (((z.getCrawlingZombieMap().get(i).getVel().isAttacking() == true || z.getCrawlingZombieMap().get(i).isAttackDone() == false) && z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum())) {
                z.getCrawlingZombieMap().get(i).zombieAttack(p1, p2, collisonCheckX);
            } else if ((z.getCrawlingZombieMap().get(i).isAttackDone() == true && z.getCrawlingZombieMap().get(i).getStageNum() == room.getCurrentStageNum())) {
                z.getCrawlingZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum(),p1 ,p2);//공격모션 중 죽으면 dead처리 안됨.
            }
        }

        for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
            z.getRunningZombieMap().get(i).updateAnimation(room.getCurrentStageNum());

            if (((z.getRunningZombieMap().get(i).getVel().isAttacking() == true || z.getRunningZombieMap().get(i).isAttackDone() == false) && z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum())) {
                z.getRunningZombieMap().get(i).zombieAttack(p1, p2, collisonCheckX);
            } else if ((z.getRunningZombieMap().get(i).isAttackDone() == true && z.getRunningZombieMap().get(i).getStageNum() == room.getCurrentStageNum())) {
                z.getRunningZombieMap().get(i).move(bigX, smallX, collisonCheckX, room.getCurrentStageNum());
            }
        }

        z.getBossZombieMap().get(0).updateAnimation(room.getCurrentStageNum());
        if (((z.getBossZombieMap().get(0).getVel().isAttacking() == true || z.getBossZombieMap().get(0).isAttackDone() == false) && z.getBossZombieMap().get(0).getStageNum() == room.getCurrentStageNum())) {
            z.getBossZombieMap().get(0).zombieAttack(p1, p2, collisonCheckX);
        } else if ((z.getBossZombieMap().get(0).isAttackDone() == true && z.getBossZombieMap().get(0).getStageNum() == room.getCurrentStageNum())) {
            z.getBossZombieMap().get(0).move(p1,p2, bigX, smallX, collisonCheckX, room.getCurrentStageNum());
        }

//        //둘 중 한명이 죽고 맵 이동 할 경우
//        if (p1.isDead() == false && p2.isDead() == true) { //p1만 남은 경우
//            if ((p1.getVel().isMovingLeft() == true && collisonCheckX[p1.getX() + 38] != 1) && (p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) { //왼쪽으로 가는 경우
//                if ((bigX <= 800) && bg.getBg_x() > 0) { //배경화면 오른쪽으로 이동
//                    bg.setBgMovingRight(true);
//                    bg.subBgX(bg.getRatio() * 2);
//
//                    p1.moveObjectRight(collisonCheckX);
//                    // 플레이어 이외의 물체나 몬스터들
//                    if (!z.getStuckedZombieMap().get(0).isDead()) {
//                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        if (!z.getNormalZombieMap().get(i).isDead()) {
//                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        if (!z.getRunningZombieMap().get(i).isDead()) {
//                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
//                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//                    if (!z.getBossZombieMap().get(0).isDead()) {
//                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    //플레이어 애니메이션 변수
//                    // 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    } else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        } else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//                }
//            } else if ((p1.getVel().isMovingRight() == true && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) {// 오른쪽으로 가는 경우
//                if ((smallX >= (canvasWidth - 700)) && bg.getBg_x() < bg.getBg_xMax()) { //배경화면 왼쪽으로 이동
//                    bg.setBgMovingLeft(true);
//                    bg.addBgX(bg.getRatio() * 2);
//
//                    p1.moveObjectLeft(collisonCheckX);
//                    // 플레이어 이외의 물체나 몬스터들
//                    // 플레이어 이외의 물체나 몬스터들
//                    if (!z.getStuckedZombieMap().get(0).isDead()) {
//                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        if (!z.getNormalZombieMap().get(i).isDead()) {
//                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        if (!z.getRunningZombieMap().get(i).isDead()) {
//                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
//                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//                    if (!z.getBossZombieMap().get(0).isDead()) {
//                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    //플레이어 애니메이션 변수
//                    // 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    } else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        } else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//                }
//            }
//        }

//        if (p2.isDead() == false && p1.isDead() == true) { //p2만 남은 경우
//            if ((p2.getVel().isMovingLeft() == true && collisonCheckX[p2.getX() + 38] != 1) && (p2.getVel().isAttacking() == false && p2.getVel().isBlocking() == false && p2.isDamaged() == false)) { //왼쪽으로 가는 경우
//                if ((bigX <= 800) && bg.getBg_x() > 0) { //배경화면 오른쪽으로 이동
//                    bg.setBgMovingRight(true);
//                    bg.subBgX(bg.getRatio() * 2);
//
//                    p2.moveObjectRight(collisonCheckX);
//                    // 플레이어 이외의 물체나 몬스터들
//                    // 플레이어 이외의 물체나 몬스터들
//                    if (!z.getStuckedZombieMap().get(0).isDead()) {
//                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        if (!z.getNormalZombieMap().get(i).isDead()) {
//                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        if (!z.getRunningZombieMap().get(i).isDead()) {
//                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
//                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//                    if (!z.getBossZombieMap().get(0).isDead()) {
//                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    //플레이어 애니메이션 변수
//                    // 애니메이션 변수
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    } else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        } else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//                }
//            } else if ((p2.getVel().isMovingRight() == true && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (p2.getVel().isAttacking() == false && p2.getVel().isBlocking() == false && p2.isDamaged() == false)) {// 오른쪽으로 가는 경우
//                if ((smallX >= (canvasWidth - 700)) && bg.getBg_x() < bg.getBg_xMax()) { //배경화면 왼쪽으로 이동
//                    bg.setBgMovingLeft(true);
//                    bg.addBgX(bg.getRatio() * 2);
//
//                    p2.moveObjectLeft(collisonCheckX);
//                    // 플레이어 이외의 물체나 몬스터들
//                    // 플레이어 이외의 물체나 몬스터들
//                    if (!z.getStuckedZombieMap().get(0).isDead()) {
//                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        if (!z.getNormalZombieMap().get(i).isDead()) {
//                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        if (!z.getRunningZombieMap().get(i).isDead()) {
//                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
//                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                        }
//                    }
//                    if (!z.getBossZombieMap().get(0).isDead()) {
//                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    }
//                    //플레이어 애니메이션 변수
//                    // 애니메이션 변수
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    } else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        } else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//                }
//            }
//        }
        //플레이어 1,2 가 맵 이동을 위해 같은 방향으로 움직일때

        //둘 다 왼쪽으로 움직일 때
//        if (((p1.getVel().isMovingLeft() && collisonCheckX[p1.getX() + 38] != 1) && (!p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged())))) {
//            if (p2.getVel().isMovingLeft() && collisonCheckX[p2.getX() + 38] != 1) && (!p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged()))) {
//                if ((bigX <= 800) && bg.getBg_x() > 0) { //배경화면 오른쪽으로 이동
//                    bg.setBgMovingRight(true);
//                    bg.subBgX(bg.getRatio() * 2);
//
//
//                    // 플레이어 이외의 물체나 몬스터들
//                    if(!p1.getVel().isMovingLeft()){
//                        p1.moveObjectRight(collisonCheckX);
//                    }
//                    if(!p2.getVel().isMovingLeft()){
//                        p2.moveObjectRight(collisonCheckX);
//                    }
//                    z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(),room.getCurrentStageNum());
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//                    z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//                    //플레이어 애니메이션 변수
//                    // 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    }
//
//                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        }
//                        else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    }
//
//                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        }
//                        else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//                }
//                else if (p1.getX() > 0 && p2.getX() > 0){ // 그냥 각자 움직이는 경우
//                    collisonCheckX[p1.getX() + 38] = 0;
//                    collisonCheckX[p1.getX() + 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 41] = -1;
//                    p1.subX(2);
//                    p1.getAttackBox().subPosition_x(2);
//
//                    // 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    }
//
//                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        }
//                        else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//
//                    collisonCheckX[p2.getX() + 38] = 0;
//                    collisonCheckX[p2.getX() + 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 41] = -1;
//                    p2.subX(2);
//                    p2.getAttackBox().subPosition_x(2);
//
//                    // 애니메이션 변수
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    }
//
//                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        }
//                        else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//
//                }
//            }
//        }

        //둘 다 오른쪽으로 움직일 때
//        if (((p1.getVel().isMovingRight() && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (!p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged()))||((p2.getVel().isMovingRight() && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (!p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged()))) {
//            if (true) {
//                if ((smallX >= (canvasWidth - 700)) && bg.getBg_x() < bg.getBg_xMax()) { //배경화면 왼쪽으로 이동
//                    bg.setBgMovingLeft(true);
//                    bg.addBgX(bg.getRatio() * 2);
//
//                    // 플레이어 이외의 물체나 몬스터들
//                    if(!p1.getVel().isMovingRight()){
//                        p1.moveObjectLeft(collisonCheckX);
//                    }
//                    if(!p2.getVel().isMovingRight()){
//                        p2.moveObjectLeft(collisonCheckX);
//                    }
//                    z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(),room.getCurrentStageNum());
//                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
//                        z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
//                        z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//
//                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
//                        z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
//                    }
//                    z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
//
//
//
//                    // 플레이어 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    }
//
//                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        }
//                        else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    }
//
//                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        }
//                        else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//                }
//                else if (p1.getX() + p1.getCanvasLength() < canvasWidth && p2.getX() + p2.getCanvasLength() < canvasWidth){ // 그냥 각자 움직이는 경우
//                    collisonCheckX[p1.getX() + 40] = -1;
//                    collisonCheckX[p1.getX() + 41] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] = 0;
//                    p1.addX(2);
//                    p1.getAttackBox().addPosition_x(2);
//
//                    // 애니메이션 변수
//                    if (p1.getFrameCount() < p1.getRefreshRate()) {
//                        p1.addFrameCount(1);
//                    }
//
//                    else if (p1.getFrameCount() == p1.getRefreshRate()) {
//                        p1.setFrameCount(0);
//                        if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
//                            p1.setWalkingCount(0);
//                        }
//                        else {
//                            p1.addWalkingCount(1);
//                        }
//                    }
//
//                    collisonCheckX[p2.getX() + 40] = -1;
//                    collisonCheckX[p2.getX() + 41] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] = 0;
//                    p2.addX(2);
//                    p2.getAttackBox().addPosition_x(2);
//
//                    // 애니메이션 변수
//                    if (p2.getFrameCount() < p2.getRefreshRate()) {
//                        p2.addFrameCount(1);
//                    }
//
//                    else if (p2.getFrameCount() == p2.getRefreshRate()) {
//                        p2.setFrameCount(0);
//                        if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
//                            p2.setWalkingCount(0);
//                        }
//                        else {
//                            p2.addWalkingCount(1);
//                        }
//                    }
//
//                }
//            }
//        }

        //플레이어1 이 가만히 서 있는 경우
        if (p1.getVel().isAttacking() == false && p1.getVel().isMoving() == false && p1.getVel().isBlocking() == false) {
            if (p1.getFrameCount() < p1.getRefreshRate()) {
                p1.addFrameCount(1);
            } else if (p1.getFrameCount() == p1.getRefreshRate()) {
                p1.setFrameCount(0);

                if (p1.getIdleCount() == p1.getIdleLoop() - 1) {
                    p1.setIdleCount(0);
                } else {
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
            } else {
                p1.addInteractionCount(1);
            }
        }

        //플래이어1 이 왼쪽으로 이동하는 경우
        if (arr_stageChangePoint[room.getCurrentStageNum()] == 1) {//오른쪽 시작
            if ((p1.getVel().isMovingLeft() && collisonCheckX[p1.getX() + 38] != 1) && (!p1.isGrabbed() && !p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged())) {
                log.debug("p1 go left");
                if ((bigX <= 10800) && bg.getBg_x() > 0 && !bg.isBgMovingRight()) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingRight(true);
                    bg.subBgX(bg.getRatio() * 2);


                    //                 플레이어 이외의 물체나 몬스터들
                    //                if(!p1.getVel().isMovingLeft()){
                    p1.moveObjectRight(collisonCheckX);
                    //                }
                    if (!p2.getVel().isMovingLeft() && !p2.isDead()) {
                        p2.moveObjectRight(collisonCheckX);
                    }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p1.getX() > 10000) {
//                    collisonCheckX[p1.getX() + 38] = 0;
//                    collisonCheckX[p1.getX() + 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 41] = -1;
                    p1.subX(2);
                    //                p1.getAttackBox().subPosition_x(2);
                }
                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                } else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    } else {
                        p1.addWalkingCount(1);
                    }
                }
            }
        }
        else{
            if ((p1.getVel().isMovingLeft() && collisonCheckX[p1.getX() + 38] != 1) && (!p1.isGrabbed() && !p1.getVel().isAttacking() && !p1.getVel().isBlocking() && !p1.isDamaged())) {
                log.debug("p1 go left");
                if ((bigX <= 800) && bg.getBg_x() > 0 && !bg.isBgMovingRight()) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingRight(true);
                    bg.subBgX(bg.getRatio() * 2);


    //                 플레이어 이외의 물체나 몬스터들
    //                if(!p1.getVel().isMovingLeft()){
                    p1.moveObjectRight(collisonCheckX);
    //                }
                    if (!p2.getVel().isMovingLeft() && !p2.isDead()) {
                        p2.moveObjectRight(collisonCheckX);
                    }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p1.getX() > 0) {
//                    collisonCheckX[p1.getX() + 38] = 0;
//                    collisonCheckX[p1.getX() + 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 41] = -1;
                    p1.subX(2);
    //                p1.getAttackBox().subPosition_x(2);
                }
                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                } else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    } else {
                        p1.addWalkingCount(1);
                    }
                }
            }
        }

        //플래이어1이 오른쪽으로 이동하는 경우
        if (arr_stageChangePoint[room.getCurrentStageNum()] == 1) {//오른쪽 시작
            if ((p1.getVel().isMovingRight() == true && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (!p1.isGrabbed() && p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) {
                log.debug("p1 go right");
                if ((smallX >= (canvasWidth - 700)+10000) && bg.getBg_x() < bg.getBg_xMax() && !bg.isBgMovingLeft()) { //배경화면 왼쪽으로 이동
                    bg.setBgMovingLeft(true);
                    bg.addBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
//                if(!p1.getVel().isMovingRight()){
                    p1.moveObjectLeft(collisonCheckX);
//                }
                    if (!p2.getVel().isMovingRight() && !p2.isDead()) {
                        p2.moveObjectLeft(collisonCheckX);
                    }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p1.getX()-10000 < canvasWidth - p1.getCanvasLength()) {//canvas_width
//                    collisonCheckX[p1.getX() + 40] = -1;
//                    collisonCheckX[p1.getX() + 41] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] = 0;
                    p1.addX(2);
//                p1.getAttackBox().addPosition_x(2);
                }
                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                } else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    } else {
                        p1.addWalkingCount(1);
                    }
                }
            }
        }
        else{
            if ((p1.getVel().isMovingRight() == true && collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] != 1) && (!p1.isGrabbed() && p1.getVel().isAttacking() == false && p1.getVel().isBlocking() == false && p1.isDamaged() == false)) {
                log.debug("p1 go right");
                if ((smallX >= (canvasWidth - 700)) && bg.getBg_x() < bg.getBg_xMax() && !bg.isBgMovingLeft()) { //배경화면 왼쪽으로 이동
                    bg.setBgMovingLeft(true);
                    bg.addBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
//                if(!p1.getVel().isMovingRight()){
                    p1.moveObjectLeft(collisonCheckX);
//                }
                    if (!p2.getVel().isMovingRight() && !p2.isDead()) {
                        p2.moveObjectLeft(collisonCheckX);
                    }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p1.getX() < canvasWidth - p1.getCanvasLength()) {//canvas_width
//                    collisonCheckX[p1.getX() + 40] = -1;
//                    collisonCheckX[p1.getX() + 41] = -1;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p1.getX() + p1.getCanvasLength() - 38] = 0;
                    p1.addX(2);
//                p1.getAttackBox().addPosition_x(2);
                }
                // 애니메이션 변수
                if (p1.getFrameCount() < p1.getRefreshRate()) {
                    p1.addFrameCount(1);
                } else if (p1.getFrameCount() == p1.getRefreshRate()) {
                    p1.setFrameCount(0);
                    if (p1.getWalkingCount() == p1.getWalkingLoop() - 1) {
                        p1.setWalkingCount(0);
                    } else {
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
                    if(z.getStuckedZombieMap().get(0).getStageNum()==room.getCurrentStageNum()){
                        z.getStuckedZombieMap().get(0).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                    }
                    if(z.getBossZombieMap().get(0).getStageNum()==room.getCurrentStageNum()){
                        z.getBossZombieMap().get(0).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
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
                if (Math.abs(p1.getAttackTimer()) >= p1.getAttackBox().getWidth()) {
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
                    if(z.getStuckedZombieMap().get(0).getStageNum()==room.getCurrentStageNum()){
                        z.getStuckedZombieMap().get(0).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                    }
                    if(z.getBossZombieMap().get(0).getStageNum()==room.getCurrentStageNum()){
                        z.getBossZombieMap().get(0).checkAttacked(p1.getAttackBox().getPosition_x() + p1.getAttackTimer(), collisonCheckX);
                    }
                    p1.getVel().setAttacking(false);
                    p1.setAttackTimer(0);
                }
                else {
                    p1.subAttackTimer(4);
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
            log.debug("p1 damage");
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
        if (arr_stageChangePoint[room.getCurrentStageNum()] == 1) {//오른쪽 시작
            if ((p2.getVel().isMovingLeft() == true && collisonCheckX[p2.getX() + 38] != 1) && (!p2.isGrabbed() && p2.getVel().isAttacking() == false && p2.getVel().isBlocking() == false && p2.isDamaged() == false)) {
                log.debug("p2 go left");
                if ((bigX-10000 <= 800) && bg.getBg_x() > 0 && !bg.isBgMovingRight()) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingRight(true);
                    bg.subBgX(bg.getRatio() * 2);


                    // 플레이어 이외의 물체나 몬스터들
                    if (!p1.getVel().isMovingLeft() && !p1.isDead()) {
                        p1.moveObjectRight(collisonCheckX);
                    }
//                if(!p2.getVel().isMovingLeft()){
                    p2.moveObjectRight(collisonCheckX);
//                }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p2.getX()-10000 > 0) {
//                    collisonCheckX[p2.getX() + 38] = 0;
//                    collisonCheckX[p2.getX() + 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 41] = -1;
                    p2.subX(2);
//                p2.getAttackBox().subPosition_x(2);
                }
                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                } else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    } else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }
        else {
            if ((p2.getVel().isMovingLeft() && collisonCheckX[p2.getX() + 38] != 1) && (!p2.isGrabbed() && !p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
                log.debug("p2 go left");
                if ((bigX <= 800) && bg.getBg_x() > 0 && !bg.isBgMovingRight()) { //배경화면 오른쪽으로 이동
                    bg.setBgMovingRight(true);
                    bg.subBgX(bg.getRatio() * 2);


                    // 플레이어 이외의 물체나 몬스터들
                    if (!p1.getVel().isMovingLeft() && !p1.isDead()) {
                        p1.moveObjectRight(collisonCheckX);
                    }
//                if(!p2.getVel().isMovingLeft()){
                    p2.moveObjectRight(collisonCheckX);
//                }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectRight(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectRight(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectRight(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectRight(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectRight(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p2.getX() > 0) {
//                    collisonCheckX[p2.getX() + 38] = 0;
//                    collisonCheckX[p2.getX() + 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 40] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 41] = -1;
                    p2.subX(2);
//                p2.getAttackBox().subPosition_x(2);
                }
                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                } else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    } else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }
        //플래이어2가 오른쪽으로 이동하는 경우
        if (arr_stageChangePoint[room.getCurrentStageNum()] == 1) {//오른쪽 시작
            if ((p2.getVel().isMovingRight() && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (!p2.isGrabbed() && !p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
                log.debug("p1 go right");
                if ((smallX >= (canvasWidth - 700)+10000) && bg.getBg_x() < bg.getBg_xMax() && !bg.isBgMovingLeft()) { //배경화면 왼쪽으로 이동
                    bg.setBgMovingLeft(true);
                    bg.addBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
                    if (!p1.getVel().isMovingRight() && !p1.isDead()) {
                        p1.moveObjectLeft(collisonCheckX);
                    }
//                if(!p2.getVel().isMovingRight()){
                    p2.moveObjectLeft(collisonCheckX);
//                }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p2.getX() -10000< canvasWidth - p2.getCanvasLength()) {
//                    collisonCheckX[p2.getX() + 40] = -1;
//                    collisonCheckX[p2.getX() + 41] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] = 0;
                    p2.addX(2);
//                p2.getAttackBox().addPosition_x(2);
                }
                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                } else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    } else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }
        else {
            if ((p2.getVel().isMovingRight() && collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] != 1) && (!p2.isGrabbed() && !p2.getVel().isAttacking() && !p2.getVel().isBlocking() && !p2.isDamaged())) {
                log.debug("p1 go right");
                if ((smallX >= (canvasWidth - 700)) && (bg.getBg_x() < bg.getBg_xMax()) && !bg.isBgMovingLeft()) { //배경화면 왼쪽으로 이동
                    bg.setBgMovingLeft(true);
                    bg.addBgX(bg.getRatio() * 2);

                    // 플레이어 이외의 물체나 몬스터들
                    if (!p1.getVel().isMovingRight() && !p1.isDead()) {
                        p1.moveObjectLeft(collisonCheckX);
                    }
//                if(!p2.getVel().isMovingRight()){
                    p2.moveObjectLeft(collisonCheckX);
//                }
                    // 플레이어 이외의 물체나 몬스터들
                    if (!z.getStuckedZombieMap().get(0).isDead()) {
                        z.getStuckedZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getStuckedZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                    for (int i = 0; i < z.getNormalZombieMap().size(); i++) {
                        if (!z.getNormalZombieMap().get(i).isDead()) {
                            z.getNormalZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getNormalZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getRunningZombieMap().size(); i++) {
                        if (!z.getRunningZombieMap().get(i).isDead()) {
                            z.getRunningZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getRunningZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }

                    for (int i = 0; i < z.getCrawlingZombieMap().size(); i++) {
                        if (!z.getCrawlingZombieMap().get(i).isDead()) {
                            z.getCrawlingZombieMap().get(i).moveObjectLeft(collisonCheckX, z.getCrawlingZombieMap().get(i).getStageNum(), room.getCurrentStageNum());
                        }
                    }
                    if (!z.getBossZombieMap().get(0).isDead()) {
                        z.getBossZombieMap().get(0).moveObjectLeft(collisonCheckX, z.getBossZombieMap().get(0).getStageNum(), room.getCurrentStageNum());
                    }
                }
                if (p2.getX() < canvasWidth - p2.getCanvasLength()) {
//                    collisonCheckX[p2.getX() + 40] = -1;
//                    collisonCheckX[p2.getX() + 41] = -1;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 39] = 0;
//                    collisonCheckX[p2.getX() + p2.getCanvasLength() - 38] = 0;
                    p2.addX(2);
//                p2.getAttackBox().addPosition_x(2);
                }
                // 애니메이션 변수
                if (p2.getFrameCount() < p2.getRefreshRate()) {
                    p2.addFrameCount(1);
                } else if (p2.getFrameCount() == p2.getRefreshRate()) {
                    p2.setFrameCount(0);
                    if (p2.getWalkingCount() == p2.getWalkingLoop() - 1) {
                        p2.setWalkingCount(0);
                    } else {
                        p2.addWalkingCount(1);
                    }
                }
            }
        }

        //플레이어2가 공격 중인 경우
        if (p2.getVel().isAttacking()) {
            //오른쪽 공격
            if (p2.getVel().isLookingRight()) {
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
                    if (z.getStuckedZombieMap().get(0).getStageNum() == room.getCurrentStageNum()) {
                        z.getStuckedZombieMap().get(0).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                    }
                    if (z.getBossZombieMap().get(0).getStageNum() == room.getCurrentStageNum()) {
                        z.getBossZombieMap().get(0).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                    }
                    p2.getVel().setAttacking(false);
                    p2.setAttackTimer(0);
                } else {
                    p2.addAttackTimer(4);
                }
            }
            //왼쪽 공격
            else if (!p2.getVel().isLookingRight()) {
                if (Math.abs(p2.getAttackTimer()) >= p2.getAttackBox().getWidth()) {
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
                    if (z.getStuckedZombieMap().get(0).getStageNum() == room.getCurrentStageNum()) {
                        z.getStuckedZombieMap().get(0).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);
                    }
                    if (z.getBossZombieMap().get(0).getStageNum() == room.getCurrentStageNum()) {
                        z.getBossZombieMap().get(0).checkAttacked(p2.getAttackBox().getPosition_x() + p2.getAttackTimer(), collisonCheckX);

                    }
                    p2.getVel().setAttacking(false);
                    p2.setAttackTimer(0);
                }
                else {
                    p2.subAttackTimer(4);
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
            log.debug("p2 damage {}",p2.getDamagedCount());
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
//        log.info("bgx: {}, bgmx: {}, bigx: {}, canvaswidth: {}",bg.getBg_x(), bg.getBg_xMax(), bigX+40, canvasWidth-10);
//        if ((bg.getBg_x() == bg.getBg_xMax() || bg.getBg_x()==0) && bigX + 40 == canvasWidth - 10) { //둘 중 한명이 맵 오른쪽 끝까지 가는 경우
////            p1.initPlayerPoint(100,200);
////            p2.initPlayerPoint(300,200);
//            p1.setX(100);
//            p2.setX(300);
//            room.addCurrentStageNum(1);
////            bg.setBg_x(0);
////            Arrays.fill(collisonCheckX,-1);
//        }
        if (arr_stageChangePoint[room.getCurrentStageNum()] == 0) { // 현재 맵이 오른쪽에서 끝나는 맵인 경우
            if (bg.getBg_x() == bg.getBg_xMax() && bigX + 40 == canvasWidth - 10) { //둘 중 한명이 맵 오른쪽 끝까지 가는 경우
                room.addCurrentStageNum(1);

                if (arr_stageChangePoint[room.getCurrentStageNum()] == 0) { //왼쪽 시작인 경우
                    p1.setX(100);
                    p2.setX(300);
                    bg.updateBgMax(room.getCurrentStageNum());

                    bg.setBg_x(0);

//                    p1.setPositionX(p1.getX(),p1.getCanvasLength());
//                    p2.setPositionX(p2.getX(),p2.getCanvasLength());
//                    p1.getAttackBox().setPosition_x(p1.getX()+p1.getCanvasLength()/2);
//                    p2.getAttackBox().setPosition_x(p2.getX()+p2.getCanvasLength()/2);
                    Arrays.fill(collisonCheckX,-1);
                    p1.getVel().setLookingRight(true);
                    p2.getVel().setLookingRight(true);
                }
                else {//오른쪽 시작인 경우
                    p1.setX(10000+1500);
                    p2.setX(10000+1800);
                    bg.updateBgMax(room.getCurrentStageNum());

                    bg.setBg_x(bg.getBg_xMax());

//                    p1.setPositionX(p1.getX(),p1.getCanvasLength());
//                    p2.setPositionX(p2.getX(),p2.getCanvasLength());
//                    p1.getAttackBox().setPosition_x(p1.getX()+p1.getCanvasLength()/2);
//                    p2.getAttackBox().setPosition_x(p2.getX()+p2.getCanvasLength()/2);
                    Arrays.fill(collisonCheckX,-1);
                    p1.getVel().setLookingRight(false);
                    p2.getVel().setLookingRight(false);
                }
            }
        }
        else if (arr_stageChangePoint[room.getCurrentStageNum()] == 1) {// 현재 맵이 왼쪽에서 끝나는 맵인 경우
            if (bg.getBg_x() == 0 && smallX-10000 == 50) { //둘 중 한명이 맵 왼쪽 끝까지 가는 경우
                room.addCurrentStageNum(1);

                if (arr_stageChangePoint[room.getCurrentStageNum()] == 0) { //왼쪽 시작인 경우
                    p1.setX(100);
                    p2.setX(300);
                    bg.updateBgMax(room.getCurrentStageNum());

                    bg.setBg_x(0);

//                    p1.setPositionX(p1.getX(),p1.getCanvasLength());
//                    p2.setPositionX(p2.getX(),p2.getCanvasLength());
//                    p1.getAttackBox().setPosition_x(p1.getX()+p1.getCanvasLength()/2);
//                    p2.getAttackBox().setPosition_x(p2.getX()+p2.getCanvasLength()/2);
                    Arrays.fill(collisonCheckX,-1);
                    p1.getVel().setLookingRight(true);
                    p2.getVel().setLookingRight(true);
                }
                else {//오른쪽 시작인 경우
                    p1.setX(10000+1500);
                    p2.setX(10000+1800);
                    bg.updateBgMax(room.getCurrentStageNum());

                    bg.setBg_x(bg.getBg_xMax());
//                    p1.setPositionX(p1.getX(),p1.getCanvasLength());
//                    p2.setPositionX(p2.getX(),p2.getCanvasLength());
//                    p1.getAttackBox().setPosition_x(p1.getX()+p1.getCanvasLength()/2);
//                    p2.getAttackBox().setPosition_x(p2.getX()+p2.getCanvasLength()/2);
                    Arrays.fill(collisonCheckX,-1);
                    p1.getVel().setLookingRight(false);
                    p2.getVel().setLookingRight(false);
                }

            }
        }
        bg.setBgMovingLeft(false);
        bg.setBgMovingRight(false);
//        log.info("crawling: {}, {}, {}",z.getCrawlingZombieMap().get(0).getX(),z.getCrawlingZombieMap().get(1).getX(),z.getCrawlingZombieMap().get(2).getX());
    }



    public void getUpdatedVelocityDown(PlayRoomMessage dto, Player player) { // 키 눌렀을때(누르고 있을 때) 이벤트
        int keyCode=dto.getKeyCode();
        log.debug("keyCode: {}",keyCode);
        switch (keyCode) {
            //a -> 왼쪽 이동
            case 65:
                log.debug("65");
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
                log.debug("68");
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
                log.debug("70");
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
                log.debug("82");
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
        log.debug("keyCode: {}",keyCode);
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
