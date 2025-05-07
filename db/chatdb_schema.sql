
-- 중고거래 플랫폼 DB 스키마
-- 작성자: G1 팀
-- 생성일: 2025-05-07
-- MySQL 8.0 기반

-- user_information 테이블
CREATE TABLE `user_information` (
    `id`           INT NOT NULL AUTO_INCREMENT,
    `email`        VARCHAR(255) NOT NULL,
    `password`     VARCHAR(255) DEFAULT NULL,
    `username`     VARCHAR(255) DEFAULT NULL,
    `nickname`     VARCHAR(255) DEFAULT NULL,
    `provider`     VARCHAR(50) DEFAULT NULL,
    `provider_id`  VARCHAR(255) DEFAULT NULL,
    `role`         VARCHAR(50) DEFAULT 'USER',
    `created_at`   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- cities 테이블
CREATE TABLE `cities` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `city` VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- member 테이블
CREATE TABLE `member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `provider` VARCHAR(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `provider_id` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `role` VARCHAR(50) COLLATE utf8mb4_unicode_ci DEFAULT 'USER',
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- chat 테이블
CREATE TABLE `chat` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `post_id` INT NOT NULL,
    `sender_id` INT NOT NULL,
    `content` VARCHAR(512) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- chat_message 테이블
CREATE TABLE `chat_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `chat_id` INT NOT NULL,
    `sender_id` INT NOT NULL,
    `content` VARCHAR(255) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `sender` VARCHAR(255) DEFAULT NULL,
    `timestamp` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- user_transaction 테이블
CREATE TABLE `user_transaction` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `sellerId` INT NOT NULL,
    `buyerId` INT NOT NULL,
    `postId` INT NOT NULL,
    `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `fk_transaction_seller` (`sellerId`),
    KEY `fk_transaction_buyer` (`buyerId`),
    KEY `fk_transaction_post` (`postId`),
    CONSTRAINT `fk_transaction_seller` FOREIGN KEY (`sellerId`) REFERENCES `user_information` (`id`),
    CONSTRAINT `fk_transaction_buyer` FOREIGN KEY (`buyerId`) REFERENCES `user_information` (`id`),
    CONSTRAINT `fk_transaction_post` FOREIGN KEY (`postId`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

create table post (post_id int primary key auto_increment, profile_id int, view int,file blob, title varchar(30), price int, content varchar(512), date timestamp, status enum('ONSALE','RESERVED','SOLDOUT'));
create table reply (reply_id int primary key auto_increment, profile_id int, ref int, level int, post_id int,content varchar(100), date timestamp);



create table report (report_id int primary key auto_increment,profile_id int, target_id int, content varchar(100));



ALTER TABLE post
ADD CONSTRAINT fk_post_profile FOREIGN KEY (profile_id) REFERENCES user_information(profile_id);

ALTER TABLE reply
ADD CONSTRAINT fk_reply_profile FOREIGN KEY (profile_id) REFERENCES user_information(profile_id),
ADD CONSTRAINT fk_reply_post FOREIGN KEY (post_id) REFERENCES post(post_id);

ALTER TABLE report
ADD CONSTRAINT fk_report_profile FOREIGN KEY (profile_id) REFERENCES user_information(profile_id),
ADD CONSTRAINT fk_report_target FOREIGN KEY (target_id) REFERENCES user_information(profile_id);
