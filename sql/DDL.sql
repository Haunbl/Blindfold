CREATE TABLE member {
        id              BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_name       VARCHAR(100)                 NULL,
        steam_id        VARCHAR(100)                 NULL,
        member_type     ENUM('GUEST','MASTER')   NOT NULL,

    } ENGINE = InnoDB DEFAULT CHARSET= utf8mb4;