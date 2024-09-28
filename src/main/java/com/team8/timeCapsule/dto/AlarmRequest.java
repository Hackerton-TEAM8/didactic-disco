package com.team8.timeCapsule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AlarmRequest {
    @NotBlank
    @Size(max = 100) // 제목 최대 길이 100자로 제한
    private String title;

    @NotBlank
    @Size(max = 500) // 내용 최대 길이 500자로 제한
    private String content;

    @NotNull
    private Long timeCapsuleId;
}