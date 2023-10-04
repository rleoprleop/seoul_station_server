package com.capstone.capstone.service;

import com.capstone.capstone.VO.StageJoinBackgroundVO;
import com.capstone.capstone.VO.StageJoinMobVO;
import com.capstone.capstone.mapper.StageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StageService {
    private final StageMapper stageMapper;
    public Map<String,Object> getStage(Long stageId){
        log.info("stageId {}",stageId);
        Map<String,Object> result = new HashMap<>();
        List<StageJoinBackgroundVO> bg = stageMapper.getBackgroundByStageId(stageId);
        List<StageJoinMobVO> mob = stageMapper.getMobByStageId(stageId);
        log.info("bg: {} mob: {}",bg,mob);
        result.put("background",bg);
        result.put("mob",mob);
        return result;
    }
}
