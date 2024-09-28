package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.domain.UserToken;
import com.team8.timeCapsule.dto.AlarmResponse;
import com.team8.timeCapsule.repository.AlarmRepository;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import com.team8.timeCapsule.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private TimeCapsuleRepository timeCapsuleRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

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

    // 알람 트리거 메서드: unlockDate에 도달한 알람들을 트리거
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void triggerAlarms() {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 원하는 타임존 설정
        List<Alarm> alarms = alarmRepository.findAllByUnlockDateLessThanEqualAndIsConfirmFalse(currentTime);

        for (Alarm alarm : alarms) {
            // FCM 푸시 알림 전송
            String message = "타임캡슐이 해제되었습니다: " + alarm.getTitle();
            sendNotificationToUser(alarm.getUserId(), message);

            // 알람을 확인된 것으로 업데이트
            alarm.setIsConfirm(true);
            alarmRepository.save(alarm);
        }
    }

    // FCM 푸시 알림 전송 메서드
    private void sendNotificationToUser(Long userId, String message) {
        // 유저의 FCM 토큰 가져오기
        Optional<UserToken> userToken = userTokenRepository.findByUserId(userId);

        if (userToken.isPresent()) {
            String fcmToken = userToken.get().getToken();
            // FCM을 통해 알림 전송
            sendNotification(fcmToken, "알람", message);
        }
    }

    // FCM 알림 전송 로직
    private void sendNotification(String targetToken, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
}

