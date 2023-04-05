package com.capstone.capstone.Test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/t1")
public class TestApiController {
    private  final TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> getTestInfo(@RequestParam Long id) throws Exception{
        TestResponse testResponse = testService.getTestInfo(id);
        TestCommonCode testCommonCode = TestCommonCode.builder()
                .code(BizExceptionCode.NOT_FOUND_DATA.getCode())
                .status(BizExceptionCode.NOT_FOUND_DATA.getStatus())
                .message(BizExceptionCode.NOT_FOUND_DATA.getMessage()).build();
        Map<String, Object> result = new HashMap<>();
        result.put("data", testResponse);
        result.put("code", testCommonCode);
        return  ResponseEntity.ok().body(result);
    }

}
