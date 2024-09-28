CREATE TABLE user_entity (
    id VARCHAR(36) PRIMARY KEY, -- UUID 형태로 저장
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE, -- 이메일은 유니크
    password VARCHAR(255),
    CONSTRAINT uc_email UNIQUE (email)
);