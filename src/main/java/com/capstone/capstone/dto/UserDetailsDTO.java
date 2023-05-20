package com.capstone.capstone.dto;

import com.capstone.capstone.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Slf4j
@Getter
@AllArgsConstructor
public class UserDetailsDTO implements UserDetails {
    @Delegate
    private UserEntity userEntity;
}
