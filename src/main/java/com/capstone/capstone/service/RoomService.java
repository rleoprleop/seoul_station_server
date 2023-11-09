package com.capstone.capstone.service;

import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.capstone.capstone.dto.JoinWaitRoomRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RoomService {
//    @Autowired
//    StringRedisTemplate redisTemplate;
    /**
     * JoinWaitRoomService
     * 남은 대기실 확인 혹은 새로운 대기실 생성
     * 대기실 id return
     * lastwaitroomid:waitroomid 와 waitroomid:{user, user, ... }인 데이터 두개 저장.
     *
     * **/
    Map<String, String> lastRoomId = new HashMap<>();
    Map<String, ArrayList<String>> roomUser = new HashMap<>();
    public Map<String, Object> getRoomIdService(JoinWaitRoomRequestDTO joinWaitRoomRequestDTO) {
        Map<String, Object> result = new HashMap<>();
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String roomId=lastRoomId.get("lastWaitRoomId");
        log.info("{}",roomId);
//        if(roomId==null){//처음 시작할때
//            roomId= UUID.randomUUID().toString();
//            lastRoomId.put("lastWaitRoomId",roomId);
//        }
        if(roomId==null || roomUser.get(roomId).size() == 0){//새로운 roomId만들었을 때
            log.info("new roomId");
            roomId= UUID.randomUUID().toString();
            roomUser.computeIfAbsent(roomId, k -> new ArrayList<>());
            lastRoomId.put("lastWaitRoomId",roomId);
            roomUser.get(roomId).add(joinWaitRoomRequestDTO.getUserId());
        }
        if(roomUser.get(roomId).size()==2){
            log.info("remove");
            roomUser.remove(roomId);
            roomId= UUID.randomUUID().toString();
            roomUser.computeIfAbsent(roomId, k -> new ArrayList<>());
            lastRoomId.put("lastWaitRoomId",roomId);
            roomUser.get(roomId).add(joinWaitRoomRequestDTO.getUserId());
        }
        result.put("waitRoomId",roomId);
        if(roomUser.get(roomId).get(0).equals(joinWaitRoomRequestDTO.getUserId())){
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.CONNECT_MULTI);
            result.put("code",commonCodeDTO);
            return result;
        }
        roomUser.get(roomId).add(joinWaitRoomRequestDTO.getUserId());
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.CONNECT_MULTI);
        result.put("code",commonCodeDTO);
        return result;//waitroomid
    }

    public Map<String, Object> getCodeService(JoinWaitRoomRequestDTO joinWaitRoomRequestDTO){
        Map<String, Object> result = new HashMap<>();
        StringBuffer strPwd = new StringBuffer();
        char str[] = new char[1];
        for (int i = 0; i < 5; i++) {
            str[0] = (char) ((Math.random() * 26) + 65);
            strPwd.append(str);
        }
        result.put("code",strPwd.toString());
        return result;
    }
}
