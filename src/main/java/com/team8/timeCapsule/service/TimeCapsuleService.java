package com.team8.timeCapsule.service;


import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.dto.TimeCapsuleRequest;
import com.team8.timeCapsule.dto.TimeCapsuleResponse;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import com.team8.timeCapsule.s3.s3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final s3Service s3Service;


    @Transactional
    public void createTimeCapsule(TimeCapsuleRequest request, MultipartFile file) {
        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }

        TimeCapsule timeCapsule = new TimeCapsule();
        timeCapsule.setUserId(request.getUserId());
        timeCapsule.setText(request.getText());
        timeCapsule.setImageUrl(imageUrl);
        timeCapsule.setCreateDate(LocalDateTime.now());
        timeCapsule.setIsActive(false);
        timeCapsule.setUnlockDate(request.getUnlockDate());

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

        timeCapsule.setText(request.getText());
        timeCapsule.setUnlockDate(request.getUnlockDate());
        // 변경된 내용 저장은 @Transactional에 의해 자동으로 처리됩니다.
    }

    @Transactional
    public void deleteTimeCapsule(Long id) {
        TimeCapsule timeCapsule = timeCapsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("타임캡슐을 찾을 수 없습니다."));

        timeCapsule.setIsActive(false);
    }

    public List<TimeCapsuleResponse> getTimeCapsulesByUserId(String userId) {
        List<TimeCapsule> timeCapsules = timeCapsuleRepository.findByUserId(userId);

        return timeCapsules.stream()
                .map(timeCapsule -> new TimeCapsuleResponse(
                        timeCapsule.getTimeCapsuleId(),
                        timeCapsule.getUserId(),  // 이 부분은 String으로 반환되어야 합니다
                        timeCapsule.getImageUrl(),
                        timeCapsule.getText(),
                        timeCapsule.getCreateDate(),
                        timeCapsule.getIsActive(),
                        timeCapsule.getUnlockDate()))
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
}