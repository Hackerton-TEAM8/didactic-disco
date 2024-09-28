package com.team8.timeCapsule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlarmRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long timeCapsuleId;
}