CREATE TABLE friend_request_status (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       status ENUM('RECEIVE', 'ACCEPT', 'DELETE') NOT NULL
);