package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.TimeCapsule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeCapsuleResponse {

    private Long timeCapsuleId;
    private String userId;
    private String imageUrl;
    private String title;
    private String text;
    private LocalDateTime createDate;
    private Boolean isActive;
    private LocalDateTime unlockDate;


    // 생성자
    public TimeCapsuleResponse(Long timeCapsuleId, String userId, String imageUrl,String title, String text, LocalDateTime createDate, Boolean isActive, LocalDateTime unlockDate) {
        this.timeCapsuleId = timeCapsuleId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.text = text;
        this.createDate = createDate;
        this.isActive = isActive;
        this.unlockDate = unlockDate;
    }

    // TimeCapsule 엔티티에서 데이터를 받아오는 생성자
    public TimeCapsuleResponse(TimeCapsule timeCapsule) {
        this.timeCapsuleId = timeCapsule.getTimeCapsuleId();
        this.userId = timeCapsule.getUser().getId();
        this.imageUrl = timeCapsule.getImageUrl();
        this.title = timeCapsule.getTitle();
        this.text = timeCapsule.getText();
        this.createDate = timeCapsule.getCreateDate();
        this.isActive = timeCapsule.getIsActive();
        this.unlockDate = timeCapsule.getUnlockDate();
    }
}