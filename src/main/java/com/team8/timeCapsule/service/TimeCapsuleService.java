package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.TimeCapsule;
import com.team8.timeCapsule.dto.TimeCapsuleRequest;
import com.team8.timeCapsule.repository.TimeCapsuleRepository;
import com.team8.timeCapsule.s3.s3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final s3Service s3Service;

    @Transactional
    public void createTimeCapsule(TimeCapsuleRequest request, MultipartFile file) {
        String imageUrl = null;

        // 사진 파일이 있는 경우 S3에 업로드
        if (file != null && !file.isEmpty()) {
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }

        // TimeCapsule 엔티티 생성 및 저장
        TimeCapsule timeCapsule = new TimeCapsule();
        timeCapsule.setContent(request.getContent());
        timeCapsule.setTitle(request.getTitle());
        timeCapsule.setImageUrl(imageUrl);
        // 추가 필드 설정

        timeCapsuleRepository.save(timeCapsule);
    }

}
