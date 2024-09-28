package com.team8.timeCapsule.dto;

import lombok.Data;

@Data
public class AlarmResponse {
    private Long id;
    private String title;
    private String content;
    private boolean isConfirm;
    private Long timeCapsuleId;
    private Long unlockDate; // 알람이 작동하는 날짜
}
