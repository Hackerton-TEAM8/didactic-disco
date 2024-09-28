package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.Alarm;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class AlarmResponse {
    private Long id;
    private String title;
    private String content;
    private boolean isConfirm;
    private Long timeCapsuleId;
    private LocalDateTime unlockDate; // 알람이 작동하는 날짜

    // 엔티티를 기반으로 생성자 작성
    public AlarmResponse(Alarm alarm) {
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.content = alarm.getContent();
        this.isConfirm = alarm.getIsConfirm();
        this.timeCapsuleId = alarm.getTimeCapsuleId();
        this.unlockDate = alarm.getUnlockDate();
    }
}
