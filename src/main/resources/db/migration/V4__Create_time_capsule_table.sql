CREATE TABLE time_capsule (
                              time_capsule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id VARCHAR(255) NOT NULL,
                              image_url VARCHAR(512),
                              text TEXT,
                              create_date DATETIME,
                              is_active BOOLEAN DEFAULT TRUE,
                              unlock_date DATETIME
);