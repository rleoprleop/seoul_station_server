package com.capstone.capstone.mapper;

import com.capstone.capstone.VO.PlayerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface StageMapper {
    Optional<PlayerVO> getPlayerById(Long id);

}
