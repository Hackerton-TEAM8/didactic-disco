package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.Alarm;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmResponse {
    private Long id;
    private String title;
    private String content;
    private boolean isConfirm;
    private Long timeCapsuleId;
    private LocalDateTime unlockDate;

    public AlarmResponse(Alarm alarm) {
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.content = alarm.getContent();
        this.isConfirm = alarm.getIsConfirm();
        this.timeCapsuleId = alarm.getTimeCapsule().getTimeCapsuleId();
        this.unlockDate = alarm.getUnlockDate();
    }
}