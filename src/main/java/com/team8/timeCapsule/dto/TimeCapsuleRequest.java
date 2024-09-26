package com.team8.timeCapsule.dto;

import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TimeCapsuleRequest {
]
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    private String text;

    private LocalDateTime unlockDate;

    // 사진은 MultipartFile로 처리하므로 여기서는 제외합니다.
}
