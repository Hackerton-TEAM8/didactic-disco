package com.team8.timeCapsule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimeCapsuleRequest {

    @NotNull(message = "userId는 필수입니다.")
    private String userId;

    private String title;

    private String text;

    private LocalDateTime unlockDate;

    private Boolean isActive;  // 활성화 여부 필드 추가



    // 사진은 MultipartFile로 처리하므로 여기서는 제외합니다.
}
