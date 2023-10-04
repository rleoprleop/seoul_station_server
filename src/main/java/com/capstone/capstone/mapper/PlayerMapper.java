package com.capstone.capstone.mapper;

import com.capstone.capstone.VO.PlayerVO;
import com.capstone.capstone.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PlayerMapper {
    Optional<PlayerVO> getPlayerById(Long id);
    Long createPlayer(UserVO userVO);
    Long deletePlayer(Long id);
}
