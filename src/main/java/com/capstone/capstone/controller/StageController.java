package com.capstone.capstone.controller;

import com.capstone.capstone.dto.StageGetRequestDTO;
import com.capstone.capstone.dto.StageSetRequestDTO;
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
    public ResponseEntity getStage(@RequestBody StageGetRequestDTO dto) {
        Map<String, Object> result=stageService.getStage(dto.getId());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("set")
    public ResponseEntity setStage(@RequestBody StageSetRequestDTO dto) {
        Map<String, Object> result=stageService.setStage(dto);
        return ResponseEntity.ok().body(result);
    }
}
