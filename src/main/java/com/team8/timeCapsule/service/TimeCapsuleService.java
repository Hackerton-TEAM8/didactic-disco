package com.team8.timeCapsule.service;


import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.domain.UserEntity;
import com.team8.timeCapsule.dto.TimeCapsuleRequest;
import com.team8.timeCapsule.dto.TimeCapsuleResponse;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import com.team8.timeCapsule.s3.s3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final s3Service s3Service;

    @Autowired
    private UserService userService;

    @Autowired
    private TimeCapsuleRepository timeCapsuleRepository;

    @Transactional
    public void createTimeCapsule(TimeCapsuleRequest request, MultipartFile file) {
        UserEntity user = userService.findById(request.getUserId());

        String timeCapsuleImageUrl = null;

        if (file != null && !file.isEmpty()) {
            try {
                timeCapsuleImageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }

        // TimeCapsule 객체 생성
        TimeCapsule timeCapsule = new TimeCapsule();

        timeCapsule.setUser(user);

        // 소유자의 이름과 프로필 이미지 URL 가져오기
        String ownerName = user.getUsername(); // 소유자 이름
        String profileImageUrl = user.getProfileImageUrl(); // 소유자 프로필 이미지 URL

        timeCapsule.setText(request.getText());
        timeCapsule.setImageUrl(timeCapsuleImageUrl);
        timeCapsule.setTitle(request.getTitle());
        timeCapsule.setCreateDate(LocalDateTime.now());
        timeCapsule.setIsActive(false);
        timeCapsule.setUnlockDate(request.getUnlockDate());

        // 사용자 이름과 프로필 이미지 설정 (원하는 경우)
        timeCapsule.setUsername(ownerName); // 소유자 이름 설정
        timeCapsule.setProfileImageUrl(profileImageUrl); // 소유자 프로필 이미지 URL 설정

        timeCapsuleRepository.save(timeCapsule);
    }

    @Transactional
    public List<TimeCapsuleResponse> getAllTimeCapsules() {
        List<TimeCapsule> timeCapsules = timeCapsuleRepository.findAll();

        return timeCapsules.stream()
                .map(TimeCapsuleResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TimeCapsuleResponse getTimeCapsule(Long id) {
        TimeCapsule timeCapsule = timeCapsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("타임캡슐을 찾을 수 없습니다."));

        if (LocalDateTime.now().isBefore(timeCapsule.getUnlockDate())) {
            throw new RuntimeException("아직 열 수 없는 타임캡슐입니다.");
        }

        return new TimeCapsuleResponse(timeCapsule);
    }

    @Transactional
    public void updateTimeCapsule(Long id, TimeCapsuleRequest request, MultipartFile file) {
        TimeCapsule timeCapsule = timeCapsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("타임캡슐을 찾을 수 없습니다."));

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadFile(file);
                timeCapsule.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }
        timeCapsule.setTitle(request.getTitle());
        timeCapsule.setText(request.getText());
        timeCapsule.setUnlockDate(request.getUnlockDate());
        timeCapsule.setIsActive(request.getIsActive());
        timeCapsule.setTitle(request.getTitle());
        // 변경된 내용 저장은 @Transactional에 의해 자동으로 처리됩니다.
    }

    @Transactional
    public void deleteTimeCapsule(Long id) {
        TimeCapsule timeCapsule = timeCapsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("타임캡슐을 찾을 수 없습니다."));

        timeCapsule.setIsActive(false);
    }

    public List<TimeCapsuleResponse> getTimeCapsulesByUserId(String userId) {
        UserEntity user = userService.findById(userId); // 사용자 객체 가져오기
        List<TimeCapsule> timeCapsules = timeCapsuleRepository.findByUser(user); // 사용자 객체를 사용하여 타임캡슐 조회

        return timeCapsules.stream()
                .map(timeCapsule -> new TimeCapsuleResponse(
                        timeCapsule.getTimeCapsuleId(),
                        timeCapsule.getUser().getId(),
                        timeCapsule.getImageUrl(),
                        timeCapsule.getTitle(),
                        timeCapsule.getText(),
                        timeCapsule.getCreateDate(),
                        timeCapsule.getIsActive(),
                        timeCapsule.getUnlockDate()
                        ))
                .collect(Collectors.toList());
    }

    // 특정 유저의 열린 타임캡슐 조회
    public List<TimeCapsuleResponse> getOpenedTimeCapsulesByUserId(String userId) {
        LocalDateTime currentDate = LocalDateTime.now();
        List<TimeCapsule> timeCapsules = timeCapsuleRepository.findOpenedTimeCapsulesByUserId(userId, currentDate);

        return timeCapsules.stream()
                .map(TimeCapsuleResponse::new)
                .collect(Collectors.toList());
    }

    // 특정 유저의 닫힌 타임캡슐 조회
    public List<TimeCapsuleResponse> getClosedTimeCapsulesByUserId(String userId) {
        LocalDateTime currentDate = LocalDateTime.now();
        List<TimeCapsule> timeCapsules = timeCapsuleRepository.findClosedTimeCapsulesByUserId(userId, currentDate);

        return timeCapsules.stream()
                .map(TimeCapsuleResponse::new)
                .collect(Collectors.toList());
    }
}