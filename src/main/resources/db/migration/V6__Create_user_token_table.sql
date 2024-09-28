CREATE TABLE user_token (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT NOT NULL,
                            token VARCHAR(255) NOT NULL,
                            CONSTRAINT fk_user_id FOREIGN KEY (user_id)
                                REFERENCES user_entity(id) ON DELETE CASCADE
);