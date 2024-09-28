package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.TimeCapsule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeCapsuleResponse {

    private Long timeCapsuleId;
    private String userId;
    private String imageUrl;
    private String text;
    private LocalDateTime createDate;
    private Boolean isActive;
    private LocalDateTime unlockDate;

    // 생성자
    public TimeCapsuleResponse(Long timeCapsuleId, String userId, String imageUrl, String text, LocalDateTime createDate, Boolean isActive, LocalDateTime unlockDate) {
        this.timeCapsuleId = timeCapsuleId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.text = text;
        this.createDate = createDate;
        this.isActive = isActive;
        this.unlockDate = unlockDate;
    }

    // TimeCapsule 엔티티에서 데이터를 받아오는 생성자
    public TimeCapsuleResponse(TimeCapsule timeCapsule) {
        this.timeCapsuleId = timeCapsule.getTimeCapsuleId();
        this.userId = timeCapsule.getUserId();  // userId는 String으로
        this.imageUrl = timeCapsule.getImageUrl();
        this.text = timeCapsule.getText();
        this.createDate = timeCapsule.getCreateDate();
        this.isActive = timeCapsule.getIsActive();  // Boolean으로 맞춰야 함
        this.unlockDate = timeCapsule.getUnlockDate();
    }
}