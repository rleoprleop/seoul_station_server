package com.capstone.capstone.Test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public TestResponse getTestInfo(Long id) throws Exception{
        TestEntity testEntity =testRepository.findById(id)
                .orElseThrow(() -> new BizException(BizExceptionCode.NOT_FOUND_DATA));

        TestResponse response=TestResponse.builder()
                .name(testEntity.getName())
                .num(testEntity.getNum())
                .build();
        return response;
    }
}
