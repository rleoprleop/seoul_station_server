package com.capstone.capstone.service.Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class GameThread {//게임 시작.
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final GameUtil gameUtil;

    private Map<String,Thread> threadMap=new HashMap<>();

    public Thread getThread(String roomId){
        return threadMap.get(roomId);
    }

    private long roomTime(Room room,String roomId){
        if(System.currentTimeMillis() - room.getTime() >= 10*1000*60){
            simpMessagingTemplate.convertAndSend("/sub/play/sub/"+roomId,"game over");
            getThread(roomId).interrupt();
        }
        return System.currentTimeMillis() - room.getTime();
    }

    private void checkActive(Room room, String roomId){
        if(!room.isActive()){
            getThread(roomId).interrupt();
            simpMessagingTemplate.convertAndSend("/sub/play/sub/"+roomId,"game over");
        }
    }
    public Thread gameRoomThread(String roomId, Room room, String userId1, String userId2){
        ObjectMapper mapper = new ObjectMapper();
        Thread t = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
//                    log.debug("{}", roomMap.get(roomId));
                    try {
                        log.debug("0--------------------------------------");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        mapper.writeValue(baos, room);
                        gameUtil.gameLoop(room,userId1,userId2);
                        checkActive(room,roomId);
                        log.info("Room: {}", baos.toString());
                        log.info("roomId {}",roomId);
//                        room.setTime(roomTime(room,roomId));
                        simpMessagingTemplate.convertAndSend("/sub/play/sub/"+roomId, room);
                    } catch (Exception e) {
                        log.debug("1--------------------------------------");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    log.debug("2--------------------------------------");
                    Thread.sleep(14);
                    log.debug("3--------------------------------------");
                }
            }
            catch (InterruptedException e) {
                log.error("", e);
            }
            catch (Throwable e) {
                log.error("", e);
            }
        });
        t.start();
        threadMap.put(roomId,t);
        return t;
    }
}
