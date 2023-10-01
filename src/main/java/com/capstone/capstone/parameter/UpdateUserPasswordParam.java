package com.capstone.capstone.parameter;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordParam {
    private Long id;
    private String userPassword;
    private LocalDateTime modifiedDate;
}
