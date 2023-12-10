package com.capstone.capstone.service;

import com.capstone.capstone.dto.*;
//import com.capstone.capstone.redis.WaitRoom;
import com.capstone.capstone.info.SessionInfo;
import com.capstone.capstone.service.Game.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@ServerEndpoint(value = "/ws")
@Service
@RequiredArgsConstructor
public class WebSocketService {

    @Autowired
    StringRedisTemplate redisTemplate;

    private final GameUtil gameUtil;
    private final GameRoom gameRoom;
    private final GameThread gameThread;
    private final SimpMessagingTemplate simpMessagingTemplate;
    Map<String, SessionInfo> sessionInfoMap = new HashMap<>();
    Map<String, String> userSessionMap = new HashMap<>();
    Map<String, ArrayList<String>> active = new HashMap<>();

    Map<String, String> lastRoomId = new HashMap<>();
    Map<String, ArrayList<String>> roomUser = new HashMap<>();

    /**
     * waitRoomService
     * 대기실 플레이어들의 상태, 명수 확인.
     * 대기실에서 나가는것(leave) 확인.
     */
//    public WaitRoomResponseDTO waitRoomService(CommonWaitRoomRequestDTO dto, StompHeaderAccessor stompHeaderAccessor){//
//        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
//        log.info("{}",dto.getState());
//        if(dto.getState().equals("join")){
//            setOperations.add(dto.getWaitRoomId(),dto.getUserId());//redis 저장.
//            sessions.put(stompHeaderAccessor.getSessionId(),dto.getUserId());
//        }
//        else if(StompCommand.DISCONNECT.equals(stompHeaderAccessor.getCommand())){
//            setOperations.remove(dto.getWaitRoomId(),dto.getUserId());
//            sessions.remove(stompHeaderAccessor.getSessionId());
//        }
//        WaitRoomResponseDTO waitRoomResponseDTO=WaitRoomResponseDTO.builder()
//                .waiting(setOperations.size(dto.getWaitRoomId()))
//                .userId(dto.getUserId())
//                .build();
//        return waitRoomResponseDTO;
//    }

    public void createGame(String roomId, String userId, String sessionId){
        SessionInfo sessionInfo = SessionInfo.builder()
                .roomId(roomId)
                .userId(userId)
                .sessionId(sessionId).build();
        sessionInfoMap.put(sessionId,sessionInfo);
        userSessionMap.put(userId,sessionId);
        log.info("userId: {}, roomId",sessionInfoMap.get(sessionId).getUserId(),sessionInfoMap.get(sessionId).getRoomId());
        active.computeIfAbsent(roomId, k -> new ArrayList<>());
        if(active.get(roomId).size()==1 && active.get(roomId).contains(userId)){
            return;
        }
        active.get(roomId).add(userId);
        log.info("size: {}",active.get(roomId).size());
        if(active.get(roomId).size()==2 && !gameRoom.hasRoom(roomId)){
            log.info("CreateGame-------------------");
            Room room = gameUtil.createGameState(active.get(roomId).get(0), active.get(roomId).get(1));
            room.setActive(true);
            gameRoom.addRoom(roomId,room);
            gameThread.gameRoomThread(roomId,room,active.get(roomId).get(0),active.get(roomId).get(1));
        }
        WaitRoomResponseDTO waitRoomResponseDTO = WaitRoomResponseDTO.builder().waiting((long) active.get(roomId).size()).userId(userId).build();
        simpMessagingTemplate.convertAndSend("/sub/play/sub/"+roomId,waitRoomResponseDTO);
    }

    public void endGame(String sessionId){
        String roomId = sessionInfoMap.get(sessionId).getRoomId();
        String userId = sessionInfoMap.get(sessionId).getUserId();
        log.info("map: {}, {}",sessionInfoMap.get(sessionId),userId);
        sessionInfoMap.remove(sessionId);
        userSessionMap.remove(userId);
        active.get(roomId).remove(userId);
        roomUser.get(roomId).remove(userId);
        if(active.get(roomId).isEmpty()){
            log.info("remove!!!");
            active.remove(roomId);
            gameRoom.removeRoom(roomId);
            if(gameThread.getThread(roomId)!=null){
                gameThread.getThread(roomId).interrupt();
            }
            simpMessagingTemplate.convertAndSend("/sub/play/sub/"+roomId,"game over");
        }


    }
    public void playGame(PlayRoomMessage dto, String waitRoomId, String userId){
        if(!gameRoom.hasRoom(waitRoomId)){
            return;
        }
        Room room = gameRoom.getRoom(waitRoomId);
        Player player = room.getPlayerMap().get(userId);
        if(dto.isKeydown()){
            log.debug("keydown, {}",dto.getKeyCode());
            gameUtil.getUpdatedVelocityDown(dto,player);
        }
        else {
            log.debug("keyup, {}",dto.getKeyCode());
            gameUtil.getUpdatedVelocityUp(dto,player);
        }
    }

    public void playClear(String waitRoomId){
        redisTemplate.delete(waitRoomId);
    }

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
}
