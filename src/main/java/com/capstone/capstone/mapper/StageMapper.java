package com.capstone.capstone.mapper;

import com.capstone.capstone.VO.StageJoinBackgroundVO;
import com.capstone.capstone.VO.StageJoinMobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StageMapper {
    List<StageJoinBackgroundVO> getBackgroundByStageId(Long stageId);
    List<StageJoinMobVO> getMobByStageId(Long stageId);
}
