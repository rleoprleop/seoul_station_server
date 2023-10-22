package com.capstone.capstone.service;

import com.capstone.capstone.VO.PlayerVO;
import com.capstone.capstone.VO.StageJoinBackgroundVO;
import com.capstone.capstone.VO.StageJoinMobVO;
import com.capstone.capstone.dto.StageSetRequestDTO;
import com.capstone.capstone.mapper.PlayerMapper;
import com.capstone.capstone.mapper.StageMapper;
import com.capstone.capstone.parameter.UpdatePlayerHealthStageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StageService {
    private final StageMapper stageMapper;
    private final PlayerMapper playerMapper;
    public Map<String,Object> getStage(Long id){
//        log.info("stageId {}",stageId);
        Map<String,Object> result = new HashMap<>();
        Optional<PlayerVO> playerVOOptional = playerMapper.getPlayerById(id);
        PlayerVO playerVO = playerVOOptional.get();
        result.put("player",setPlayerCode(playerVO));

//        List<StageJoinBackgroundVO> bg = stageMapper.getBackgroundByStageId(stageId);
//        List<StageJoinMobVO> mob = stageMapper.getMobByStageId(stageId);
//        log.info("bg: {} mob: {}",bg,mob);
//        result.put("background",bg);
//        result.put("mob",mob);
        return result;
    }

    public Map<String,Object> setStage(StageSetRequestDTO dto){
//        log.info("stageId {}",stageId);
        Map<String,Object> result = new HashMap<>();
        UpdatePlayerHealthStageParam updatePlayerHealthStageParam = UpdatePlayerHealthStageParam.builder()
                .id(dto.getId())
                .stage(String.valueOf(dto.getStage()+1))
                .health(dto.getHealth())
                .build();

        playerMapper.updatePlayer(updatePlayerHealthStageParam);

        Optional<PlayerVO> playerVOOptional = playerMapper.getPlayerById(dto.getId());
        PlayerVO playerVO = playerVOOptional.get();
        result.put("player",setPlayerCode(playerVO));
        return result;
    }


    public Map<String,Object> setPlayerCode(PlayerVO playerVO) {
        HashMap<String, Object> code = new HashMap<>();
        code.put("health",playerVO.getHealth());
        code.put("stage",playerVO.getStage());

        return code;
    }
}
