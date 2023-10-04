package com.capstone.capstone.service;

import com.capstone.capstone.dto.JoinWaitRoomRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RoomService {
    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * JoinWaitRoomService
     * 남은 대기실 확인 혹은 새로운 대기실 생성
     * 대기실 id return
     * lastwaitroomid:waitroomid 와 waitroomid:{user, user, ... }인 데이터 두개 저장.
     *
     * **/
    public Map<String, Object> JoinWaitRoomService(JoinWaitRoomRequestDTO joinWaitRoomRequestDTO) {
        Map<String, Object> result = new HashMap<>();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String waitRoomId=valueOperations.get("lastWaitRoomId");
        log.info("{}",waitRoomId);
        if(waitRoomId==null || setOperations.size(waitRoomId)>=2){
            waitRoomId= UUID.randomUUID().toString();
            valueOperations.set("lastWaitRoomId",waitRoomId);
        }
        //setOperations.add(waitRoomId,joinWaitRoomRequestDTO.getUserId());
        result.put("waitRoomId",waitRoomId);
        return result;//waitroomid
    }
}
