CREATE TABLE alarm (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT,
                       is_confirm BOOLEAN DEFAULT FALSE,
                       time_capsule_id BIGINT,
                       unlock_date DATETIME,
                       user_id BIGINT,
                       CONSTRAINT fk_time_capsule_id FOREIGN KEY (time_capsule_id)
                           REFERENCES time_capsule(timeCapsuleId),
                       CONSTRAINT fk_user_id FOREIGN KEY (user_id)
                           REFERENCES user_entity(id)
);