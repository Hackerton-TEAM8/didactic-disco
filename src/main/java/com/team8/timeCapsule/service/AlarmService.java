package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.domain.UserToken;
import com.team8.timeCapsule.dto.AlarmRequest;
import com.team8.timeCapsule.dto.AlarmResponse;
import com.team8.timeCapsule.repository.AlarmRepository;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import com.team8.timeCapsule.repository.UserTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification; // Firebase Messaging 임포트 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return alarms.stream()
                .map(AlarmResponse::new) // Alarm 엔티티를 AlarmResponse DTO로 변환
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
    public AlarmResponse createAlarm(AlarmRequest alarmRequest) {
        // 타임캡슐 ID로 타임캡슐 객체 가져오기
        Optional<TimeCapsule> timeCapsuleOpt = timeCapsuleRepository.findById(alarmRequest.getTimeCapsuleId());

        if (!timeCapsuleOpt.isPresent()) {
            throw new RuntimeException("Time Capsule not found with id: " + alarmRequest.getTimeCapsuleId());
        }

        // 알람 객체 생성
        Alarm alarm = new Alarm();
        alarm.setTitle(alarmRequest.getTitle());
        alarm.setContent(alarmRequest.getContent());
        alarm.setIsConfirm(false);  // 기본값 false로 설정
        alarm.setTimeCapsule(timeCapsuleOpt.get()); // 타임캡슐 객체 설정
        alarm.setUnlockDate(LocalDateTime.now().plusMinutes(1)); // 예시로 1분 후에 해제되도록 설정

        // 알람 저장
        Alarm savedAlarm = alarmRepository.save(alarm);
        return new AlarmResponse(savedAlarm);  // DTO로 변환하여 반환
    }


    // 알람 트리거 메서드: unlockDate에 도달한 알람들을 트리거
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void triggerAlarms() {
        LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간
        List<Alarm> alarms = alarmRepository.findAllByUnlockDateLessThanEqualAndIsConfirmFalse(currentTime);

        for (Alarm alarm : alarms) {
            // FCM 푸시 알림 전송
            String message = "타임캡슐이 해제되었습니다: " + alarm.getTitle();
            String userId = alarm.getUser().getId();
            sendNotificationToUser(userId, message);

            // 알람을 확인된 것으로 업데이트
            alarm.setIsConfirm(true);
            alarmRepository.save(alarm);
        }
    }


    // FCM 푸시 알림 전송 메서드
    private void sendNotificationToUser(String userId, String message) {
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
}