package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.TimeCapsule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeCapsuleResponse {

    private Long timeCapsuleId;
    private Long userId;
    private String imageUrl;
    private String text;
    private LocalDateTime createDate;
    private Boolean isActive;
    private LocalDateTime unlockDate;

    public TimeCapsuleResponse(TimeCapsule timeCapsule) {
        this.timeCapsuleId = timeCapsule.getTimeCapsuleId();
        this.userId = timeCapsule.getUserId();
        this.imageUrl = timeCapsule.getImageUrl();
        this.text = timeCapsule.getText();
        this.createDate = timeCapsule.getCreateDate();
        this.isActive = timeCapsule.getIsActive();
        this.unlockDate = timeCapsule.getUnlockDate();
    }
}