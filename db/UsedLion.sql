-- UsedLion Platform Database Schema (profile 제거‧네이밍 통일)
-- Author : Team G1
-- Updated: 2025‑05‑07
-- MySQL 8.0

/* =======================================================
   1. DROP TABLES (자식 → 부모 순)
   ======================================================= */
DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS user_post_like;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS user_transaction;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS profile;          
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS user_information;

/* =======================================================
   2. CREATE TABLES
   ======================================================= */
/*
 * 컬럼 네이밍 규칙 전면 통일
      
          테이블          PK     주요 FK            
      
      user_information│ id │ city_id                    
      cities          │ id │ –                          
      post            │ id │ user_id                    
      reply           │ id │ post_id, user_id          
      report          │ id │ reporter_id, target_id    
      user_post_like  │ id │ user_id, post_id           
      chat            │ id │ post_id, sender_id         
      chat_message    │ id │ chat_id, sender_id         
      user_transaction│ id │ seller_id, buyer_id, post_id
      
 */
-- 기존 post.post_id, reply.profile_id 삭제
-- 2‑1. cities
CREATE TABLE cities (
    id   INT          NOT NULL AUTO_INCREMENT,
    city VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑2. user_information (city_id FK → cities)
CREATE TABLE user_information (
    id           INT          NOT NULL AUTO_INCREMENT,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) DEFAULT NULL,
    username     VARCHAR(255) DEFAULT NULL,
    nickname     VARCHAR(255) DEFAULT NULL,
    provider     VARCHAR(50)  DEFAULT NULL,
    provider_id  VARCHAR(255) DEFAULT NULL,
    role         VARCHAR(50)  DEFAULT 'USER',
    created_at   DATETIME(6)  DEFAULT CURRENT_TIMESTAMP(6),
    city_id      INT          DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑3. post
CREATE TABLE post (
    id          INT          NOT NULL AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    view        INT          DEFAULT 0,
    file        MEDIUMBLOB,
    title       VARCHAR(255) DEFAULT NULL,
    price       INT          DEFAULT NULL,
    content     VARCHAR(255) DEFAULT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    complete    TINYINT(1)   DEFAULT 0,
    status      TINYINT,
    PRIMARY KEY (id),
    KEY fk_post_user (user_id),
    CONSTRAINT post_chk_status CHECK (status BETWEEN 0 AND 2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑4. reply
CREATE TABLE reply (
    id         INT         NOT NULL AUTO_INCREMENT,
    post_id    INT         NOT NULL,
    user_id    INT         NOT NULL,
    content    VARCHAR(255),
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    KEY fk_reply_post (post_id),
    KEY fk_reply_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑5. user_post_like
CREATE TABLE user_post_like (
    id      INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_like (user_id, post_id),
    KEY fk_like_user (user_id),
    KEY fk_like_post (post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑6. chat
CREATE TABLE chat (
    id         INT NOT NULL AUTO_INCREMENT,
    post_id    INT NOT NULL,
    sender_id  INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY fk_chat_post   (post_id),
    KEY fk_chat_sender (sender_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑7. chat_message
CREATE TABLE chat_message (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    chat_id    INT    NOT NULL,
    sender_id  INT    NOT NULL,
    content    VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY fk_chat_message_chat   (chat_id),
    KEY fk_chat_message_sender (sender_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑8. report
CREATE TABLE report (
    id           INT         NOT NULL AUTO_INCREMENT,
    reporter_id  INT         NOT NULL,
    target_id    INT         NOT NULL,
    content      VARCHAR(255),
    PRIMARY KEY (id),
    KEY fk_report_reporter (reporter_id),
    KEY fk_report_target   (target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2‑9. user_transaction
CREATE TABLE user_transaction (
    id         INT NOT NULL AUTO_INCREMENT,
    seller_id  INT NOT NULL,
    buyer_id   INT NOT NULL,
    post_id    INT NOT NULL,
    date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY fk_transaction_seller (seller_id),
    KEY fk_transaction_buyer  (buyer_id),
    KEY fk_transaction_post   (post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2.10. image
create table image (image_id int primary key auto_increment, post_id int, file blob);



/* =======================================================
   3. FOREIGN KEY CONSTRAINTS
   ======================================================= */

-- user_information → cities
ALTER TABLE user_information
  ADD CONSTRAINT fk_user_city
  FOREIGN KEY (city_id) REFERENCES cities(id);

-- post
ALTER TABLE post
  ADD CONSTRAINT fk_post_user
  FOREIGN KEY (user_id) REFERENCES user_information(id);

-- reply
ALTER TABLE reply
  ADD CONSTRAINT fk_reply_post
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_reply_user
  FOREIGN KEY (user_id) REFERENCES user_information(id);

-- user_post_like
ALTER TABLE user_post_like
  ADD CONSTRAINT fk_like_user
  FOREIGN KEY (user_id) REFERENCES user_information(id),
  ADD CONSTRAINT fk_like_post
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE;

-- chat
ALTER TABLE chat
  ADD CONSTRAINT fk_chat_post
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_chat_sender
  FOREIGN KEY (sender_id) REFERENCES user_information(id);

-- chat_message
ALTER TABLE chat_message
  ADD CONSTRAINT fk_chat_message_chat
  FOREIGN KEY (chat_id) REFERENCES chat(id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_chat_message_sender
  FOREIGN KEY (sender_id) REFERENCES user_information(id);

-- report
ALTER TABLE report
  ADD CONSTRAINT fk_report_reporter
  FOREIGN KEY (reporter_id) REFERENCES user_information(id),
  ADD CONSTRAINT fk_report_target
  FOREIGN KEY (target_id)   REFERENCES user_information(id);

-- user_transaction
ALTER TABLE user_transaction
  ADD CONSTRAINT fk_transaction_seller
  FOREIGN KEY (seller_id) REFERENCES user_information(id),
  ADD CONSTRAINT fk_transaction_buyer
  FOREIGN KEY (buyer_id)  REFERENCES user_information(id),
  ADD CONSTRAINT fk_transaction_post
  FOREIGN KEY (post_id)   REFERENCES post(id);

-- image
alter table image add constraint fk_image_post foreign key (post_id) references post(id);
