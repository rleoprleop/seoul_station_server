package com.capstone.capstone.controller;

import com.capstone.capstone.dto.JoinWaitRoomRequestDTO;
import com.capstone.capstone.dto.StageRequestDTO;
import com.capstone.capstone.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stage")
public class StageController {

    private final StageService stageService;
    @PostMapping("get")
    public ResponseEntity JoinWaitRoom(@RequestBody StageRequestDTO dto) {//UserId
        Map<String, Object> result=stageService.getStage(dto.getStageId());
        return ResponseEntity.ok().body(result);//UserId, waitRoomId
    }
}
