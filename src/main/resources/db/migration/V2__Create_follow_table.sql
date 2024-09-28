CREATE TABLE follow (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        sender_id BIGINT NOT NULL,
                        receiver_id BIGINT NOT NULL,
                        state VARCHAR(255) NOT NULL,
                        CONSTRAINT fk_sender_id FOREIGN KEY (sender_id)
                            REFERENCES user_entity(id),
                        CONSTRAINT fk_receiver_id FOREIGN KEY (receiver_id)
                            REFERENCES user_entity(id)
);