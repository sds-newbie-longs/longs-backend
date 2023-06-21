-- DROP TABLE IF EXISTS statements
DROP TABLE IF EXISTS videos;
DROP TABLE IF EXISTS channel_members;
DROP TABLE IF EXISTS boards;
DROP TABLE IF EXISTS channels;
DROP TABLE IF EXISTS members;
-- CREATE TABLE statements
CREATE TABLE IF NOT EXISTS members
(
    id                   BIGINT AUTO_INCREMENT,
    username             VARCHAR(20) UNIQUE NOT NULL,
    profile_image_uuid   CHAR(32) UNIQUE,
    profile_image_type   ENUM('JPG', 'JPEG', 'PNG'),
    created_at           DATETIME NOT NULL,
    updated_at           DATETIME NOT NULL,
    PRIMARY KEY (id)
    );
ALTER TABLE members MODIFY COLUMN username VARCHAR(20) NOT NULL COMMENT '사용자 아이디';
ALTER TABLE members MODIFY COLUMN profile_image_uuid CHAR(32) COMMENT '프로필 이미지 파일 ID';
ALTER TABLE members MODIFY COLUMN profile_image_type ENUM('JPG', 'JPEG', 'PNG') COMMENT '프로필 이미지 파일 유형';
ALTER TABLE members MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '생성 일시';
ALTER TABLE members MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '수정 일시';
CREATE TABLE IF NOT EXISTS channels
(
    id                   BIGINT AUTO_INCREMENT,
    owner_id             BIGINT,
    channel_name         VARCHAR(20) UNIQUE NOT NULL,
    image_uuid           CHAR(32) UNIQUE,
    image_type           ENUM('JPG', 'JPEG', 'PNG'),
    status               ENUM('CREATED', 'DELETED') NOT NULL,
    created_at           DATETIME NOT NULL,
    updated_at           DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES members(id)
    );
ALTER TABLE channels MODIFY COLUMN owner_id BIGINT COMMENT '채널장 PK';
ALTER TABLE channels MODIFY COLUMN channel_name VARCHAR(20) NOT NULL COMMENT '채널 이름';
ALTER TABLE channels MODIFY COLUMN image_uuid CHAR(32) COMMENT '채널 이미지 파일 ID';
ALTER TABLE channels MODIFY COLUMN image_type ENUM('JPG', 'JPEG', 'PNG') COMMENT '채널 이미지 파일 유형';
ALTER TABLE channels MODIFY COLUMN status ENUM('CREATED', 'DELETED') COMMENT '상태';
ALTER TABLE channels MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '생성 일시';
ALTER TABLE channels MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '수정 일시';
CREATE TABLE IF NOT EXISTS channel_members
(
    id                   BIGINT AUTO_INCREMENT,
    member_id            BIGINT,
    channel_id           BIGINT,
    created_at           DATETIME NOT NULL,
    updated_at           DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (channel_id) REFERENCES channels(id)
    );
ALTER TABLE channel_members MODIFY COLUMN member_id BIGINT COMMENT '회원 PK';
ALTER TABLE channel_members MODIFY COLUMN channel_id BIGINT COMMENT '채널 PK';
ALTER TABLE channel_members MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '생성 일시';
ALTER TABLE channel_members MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '수정 일시';
CREATE TABLE IF NOT EXISTS boards
(
    id                   BIGINT AUTO_INCREMENT,
    title                VARCHAR(50) NOT NULL,
    description          VARCHAR(1000),
    member_id            BIGINT,
    channel_id           BIGINT,
    status               ENUM('CREATED', 'DELETED') NOT NULL,
    created_at           DATETIME NOT NULL,
    updated_at           DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (channel_id) REFERENCES channels(id)
    );
ALTER TABLE boards MODIFY COLUMN title VARCHAR(50) NOT NULL COMMENT '제목';
ALTER TABLE boards MODIFY COLUMN description VARCHAR(1000) COMMENT '설명';
ALTER TABLE boards MODIFY COLUMN member_id BIGINT COMMENT '작성자 PK';
ALTER TABLE boards MODIFY COLUMN channel_id BIGINT COMMENT '채널 PK';
ALTER TABLE boards MODIFY COLUMN status ENUM('CREATED', 'DELETED') COMMENT '상태';
ALTER TABLE boards MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '생성 일시';
ALTER TABLE boards MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '수정 일시';
CREATE TABLE IF NOT EXISTS videos
(
    id                   BIGINT AUTO_INCREMENT,
    board_id             BIGINT,
    thumbnail_image_uuid CHAR(32) UNIQUE NOT NULL,
    thumbnail_image_type ENUM('JPG', 'JPEG', 'PNG') NOT NULL,
    video_uuid           CHAR(32) UNIQUE NOT NULL,
    video_type           ENUM('MP4') NOT NULL,
    playing_time         TIME NOT NULL,
    created_at           DATETIME NOT NULL,
    updated_at           DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (board_id) REFERENCES boards(id)
    );
ALTER TABLE videos MODIFY COLUMN board_id BIGINT COMMENT '게시글 PK';
ALTER TABLE videos MODIFY COLUMN thumbnail_image_uuid CHAR (32) NOT NULL COMMENT '동영상 썸네일 이미지 파일 ID';
ALTER TABLE videos MODIFY COLUMN thumbnail_image_type ENUM('JPG', 'JPEG', 'PNG') NOT NULL COMMENT '동영상 썸네일 이미지 파일 유형';
ALTER TABLE videos MODIFY COLUMN video_uuid CHAR (32) NOT NULL COMMENT '동영상 파일 ID';
ALTER TABLE videos MODIFY COLUMN video_type ENUM('MP4') NOT NULL COMMENT '동영상 파일 유형';
ALTER TABLE videos MODIFY COLUMN playing_time TIME NOT NULL COMMENT '재생 시간';
ALTER TABLE videos MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '생성 일시';
ALTER TABLE videos MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '수정 일시';
