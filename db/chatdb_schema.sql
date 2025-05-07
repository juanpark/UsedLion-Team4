
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

-- profile 테이블
CREATE TABLE `profile` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `userInformationId` INT NOT NULL,
    `cityId` INT NOT NULL,
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

-- post 테이블
CREATE TABLE `post` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `profileId` INT NOT NULL,
    `view` INT DEFAULT '0',
    `file` TINYBLOB,
    `title` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `price` INT DEFAULT NULL,
    `content` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `complete` TINYINT(1) DEFAULT NULL,
    `profile_id` INT DEFAULT NULL,
    `status` TINYINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_post_profile` (`profileId`),
    CONSTRAINT `fk_post_profile` FOREIGN KEY (`profileId`) REFERENCES `profile` (`id`),
    CONSTRAINT `post_chk_1` CHECK ((`status` BETWEEN 0 AND 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- post_detail 테이블
CREATE TABLE `post_detail` (
    `post_id` INT NOT NULL AUTO_INCREMENT,
    `content` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `date` DATETIME(6) DEFAULT NULL,
    `file` VARBINARY(255) DEFAULT NULL,
    `price` INT DEFAULT NULL,
    `profile_id` INT DEFAULT NULL,
    `status` TINYINT DEFAULT NULL,
    `title` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `username` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `view` INT DEFAULT NULL,
    PRIMARY KEY (`post_id`),
    CONSTRAINT `post_detail_chk_1` CHECK ((`status` BETWEEN 0 AND 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- like_post 테이블
CREATE TABLE `like_post` (
    `like_id` INT NOT NULL AUTO_INCREMENT,
    `post_id` INT DEFAULT NULL,
    `profile_id` INT DEFAULT NULL,
    PRIMARY KEY (`like_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- user_post_like 테이블
CREATE TABLE `user_post_like` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `profileId` INT NOT NULL,
    `postId` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_like_profile` (`profileId`),
    KEY `fk_like_post` (`postId`),
    CONSTRAINT `fk_like_post` FOREIGN KEY (`postId`) REFERENCES `post` (`id`),
    CONSTRAINT `fk_like_profile` FOREIGN KEY (`profileId`) REFERENCES `profile` (`id`)
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

-- report 테이블
CREATE TABLE `report` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `profileId` INT NOT NULL,
    `targetId` INT NOT NULL,
    `content` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `profile_id` INT DEFAULT NULL,
    `target_id` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_report_profile` (`profileId`),
    KEY `fk_report_target` (`targetId`),
    CONSTRAINT `fk_report_profile` FOREIGN KEY (`profileId`) REFERENCES `profile` (`id`),
    CONSTRAINT `fk_report_target` FOREIGN KEY (`targetId`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
    CONSTRAINT `fk_transaction_seller` FOREIGN KEY (`sellerId`) REFERENCES `profile` (`id`),
    CONSTRAINT `fk_transaction_buyer` FOREIGN KEY (`buyerId`) REFERENCES `profile` (`id`),
    CONSTRAINT `fk_transaction_post` FOREIGN KEY (`postId`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
