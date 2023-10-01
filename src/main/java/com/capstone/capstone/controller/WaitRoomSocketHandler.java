package com.capstone.capstone.controller;

import com.capstone.capstone.dto.WaitRoomResponseDTO;
import com.capstone.capstone.dto.CommonWaitRoomRequestDTO;
import com.capstone.capstone.dto.PlayRoomMessage;
import com.capstone.capstone.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class WaitRoomSocketHandler {

    private final WebSocketService webSocketService;

//    @MessageMapping("/waitRoom/pub/{waitRoomId}")//클라이언트에서 받는 url
//    @SendTo("/sub/waitRoom/sub/{waitRoomId}")//클라이언트에 보내는 url
//    public WaitRoomResponseDTO waitRoom(@RequestBody CommonWaitRoomRequestDTO dto,//대기실. 플레이어의 대기 명수 확인. 풀방시 게임 시작 확인. url 변경 요청.
//                                        @DestinationVariable String waitRoomId,
//                                        @Header("UserId") String userId,
//                                        StompHeaderAccessor stompHeaderAccessor) throws Exception{//플레이어 입장, 퇴장 확인
//        log.info("--------------------------------------");
//        log.info("waitRoom info: waitRoomId: {}, userId: {}, session: {}, Command: {}",waitRoomId, userId, stompHeaderAccessor.getSessionId(), stompHeaderAccessor.getCommand());
//        WaitRoomResponseDTO waitRoomResponseDTO=webSocketService.waitRoomService(dto, stompHeaderAccessor);
//
//        return waitRoomResponseDTO;//현재 인원 확인,
//    }

    @MessageMapping("/play/pub/{waitRoomId}")//클라이언트에서 받는 url
//    @SendTo("/play/sub/{waitRoomId}")//클라이언트에 보내는 url
    public void playRoom(@RequestBody PlayRoomMessage dto,
                         @DestinationVariable String waitRoomId,
                         @Header("UserId") String userId,
                         StompHeaderAccessor stompHeaderAccessor) throws Exception {//게임 플레이할때 사용할것. 플레이어 이름, 위치, 뱡향, 체력, 플레이가 끝나는지 확인.
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = (JSONObject)parser.parse(dto.());
//        if(jsonObject.get("clear")=="true")//클리어 하면 reword 주고, 연결 끊기.
//        {
//            webSocketService.playClear(waitRoomId);
//        }
        log.info("COMMEND: {}",stompHeaderAccessor.getCommand());
        log.info("dto: {}",dto);
//        if(dto.isActive()){
//            webSocketService.createGame(waitRoomId,userId);
//        }
        webSocketService.playGame(dto,waitRoomId,userId);
    }
   /*


    @MessageMapping("/waitroom/pub/{waitRoomId}/delete")
    @SendTo("/wait-service/waitroom/sub/{waitRoomId}/delete")
    public ChatMessageResponse deleteWaitRoom(@Valid @RequestBody CommonWaitRoomRequestDTO dto,
                                              @DestinationVariable String waitRoomId,
                                              @Header("UserId") String userId,
                                              StompHeaderAccessor stompHeaderAccessor) {

        if (!userId.equals(dto.getUserId())) throw new WebsocketSecurityException();

        boolean deleteStatus = webSocketService.deleteWaitRoomByHost(dto);
        return ChatMessageResponse.of(dto.getUserId(), LEAVE, deleteStatus);
    }

    @MessageMapping("/waitroom/pub/{waitRoomId}/leave")
    @SendTo("/wait-service/waitroom/sub/{waitRoomId}/leave")
    public ChatMessageResponse leaveMember(@Valid @RequestBody CommonWaitRoomRequestDTO dto,
                                           @DestinationVariable String waitRoomId,
                                           @Header("UserId") String userId,
                                           StompHeaderAccessor stompHeaderAccessor) {

        if (!userId.equals(dto.getUserId())) throw new WebsocketSecurityException();

        boolean leaveStatus = webSocketService.leaveMember(dto);
        return ChatMessageResponse.of(dto.getUserId(), LEAVE, leaveStatus);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception, StompHeaderAccessor stompHeaderAccessor) throws IOException {

        if (exception instanceof WebsocketSecurityException ||
                exception instanceof NotFoundWaitRoomException ||
                exception instanceof NotFoundUserException) {
            String sessionId = stompHeaderAccessor.getSessionId();
            log.info("session = {}, connection remove", sessionId);
            decorator.closeSession(sessionId);
        }
        else if (exception instanceof CommonException) {
            return "server exception: " + exception.getMessage();
        }
        else {
            String sessionId = stompHeaderAccessor.getSessionId();
            decorator.closeSession(sessionId);
        }

        return "server exception: " + exception.getMessage() + "server session clear";
    }*/
}