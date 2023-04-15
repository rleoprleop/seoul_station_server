package com.capstone.capstone.repository;

import com.capstone.capstone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId); // userId로 조회
    Optional<UserEntity> findByUserName(String userName); // userName로 조회
}
