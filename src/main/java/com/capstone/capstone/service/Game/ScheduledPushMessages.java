//package com.capstone.capstone.service.Game;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ScheduledPushMessages {
//    @Autowired
//    StringRedisTemplate redisTemplate;
//    private final SimpMessagingTemplate simpMessagingTemplate;
//    public void sendMessage(String waitRoomId){
//        GameThread t = new GameThread(waitRoomId);
//
//        log.info("Scheduled----------------------{}");
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        simpMessagingTemplate.convertAndSend("/sub/waitRoom/sub/{waitRoomId}");
//    }//thread 사용.
//}
//
//
