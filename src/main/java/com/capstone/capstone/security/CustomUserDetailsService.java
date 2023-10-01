package com.capstone.capstone.security;

//import com.capstone.capstone.entity.UserEntity;
//import com.capstone.capstone.repository.UserRepository;
import com.capstone.capstone.VO.UserJoinRolesVO;
import com.capstone.capstone.VO.UserVO;
import com.capstone.capstone.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDetails userDetails = userMapper.getUserJoinRoles(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("not found user."));
        return userDetails;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserJoinRolesVO user) {
        System.out.println(user.getUserId()+" "+user.getRoles());

        UserDetails userDetails = User.builder()
                .username(user.getUserId())
                .password(user.getUserPassword())
                .roles(user.getRoles())
                .build();
        return userDetails;
    }
}