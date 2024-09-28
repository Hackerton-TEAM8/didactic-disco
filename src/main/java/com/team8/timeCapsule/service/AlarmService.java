package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.dto.AlarmResponse;
import com.team8.timeCapsule.repository.AlarmRepository;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private TimeCapsuleRepository timeCapsuleRepository;

    // 알람 목록 조회 메소드
    public List<AlarmResponse> getAllAlarms() {
        List<Alarm> alarms = alarmRepository.findAll();
        // Alarm 엔티티를 AlarmResponseDTO로 변환
        return alarms.stream()
                .map(alarm -> new AlarmResponse(alarm))
                .toList();
    }

    // 알람 확인 여부 수정 메소드
    public AlarmResponse confirmAlarm(Long alarmId) {
        Optional<Alarm> optionalAlarm = alarmRepository.findById(alarmId);

        if (optionalAlarm.isPresent()) {
            Alarm alarm = optionalAlarm.get();
            alarm.setIsConfirm(true);  // isConfirm을 true로 설정
            Alarm updatedAlarm = alarmRepository.save(alarm);  // 변경사항 저장
            return new AlarmResponse(updatedAlarm);  // DTO로 변환하여 반환
        } else {
            throw new RuntimeException("Alarm not found with id: " + alarmId);
        }
    }

    // 알람 생성 메소드
    public AlarmResponse createAlarm(String title, String content, Long timeCapsuleId) {
        TimeCapsule timeCapsule = timeCapsuleRepository.findById(timeCapsuleId)
                .orElseThrow(() -> new RuntimeException("TimeCapsule not found with id: " + timeCapsuleId));

        Alarm alarm = new Alarm();
        alarm.setTitle(title);
        alarm.setContent(content);
        alarm.setIsConfirm(false); // 기본적으로 확인되지 않은 상태로 설정
        alarm.setTimeCapsuleId(timeCapsuleId);
        alarm.setUnlockDate(timeCapsule.getUnlockDate()); // 타임캡슐의 unlockDate를 알람에 설정

        Alarm savedAlarm = alarmRepository.save(alarm);
        return new AlarmResponse(savedAlarm); // DTO로 변환하여 반환
    }

    // 알람 트리거 메서드: unlockDate에 도달한 알람들을 트리거
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void triggerAlarms() {
        Long currentTime = Instant.now().toEpochMilli();
        List<Alarm> alarms = alarmRepository.findAllByUnlockDateLessThanEqualAndIsConfirmFalse(currentTime);

        for (Alarm alarm : alarms) {
            // 알람 트리거 로직 (예: 이메일, 푸시 알람 등)
            sendNotification(alarm);

            // 알람을 확인된 것으로 업데이트
            alarm.setIsConfirm(true);
            alarmRepository.save(alarm);
        }
    }

    // 알람 전송 예시 메서드 (예: 이메일, 푸시 알람 등)
    private void sendNotification(Alarm alarm) {
        // 실제 알람 전송 로직을 작성
        System.out.println("알림 전송: " + alarm.getTitle());
    }
}
