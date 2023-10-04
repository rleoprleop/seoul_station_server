package com.capstone.capstone.controller;

import com.capstone.capstone.dto.JoinWaitRoomRequestDTO;
import com.capstone.capstone.service.RoomService;
import com.capstone.capstone.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wait-service")
@RequiredArgsConstructor
public class WaitRoomController {

    private final RoomService roomService;
    /**
     * JoinWaitRoom
     * 참여 가능한 방 있으면 그 방 참여, 없으면 방 생성. 그 방의 id를 return.
     * @param dto
     * @return
     */
    @PostMapping("/waitroom/create")
    public ResponseEntity JoinWaitRoom(@RequestBody JoinWaitRoomRequestDTO dto) {//UserId
        Map<String, Object> result=roomService.JoinWaitRoomService(dto);
        return ResponseEntity.ok().body(result);//UserId, waitRoomId
    }
}