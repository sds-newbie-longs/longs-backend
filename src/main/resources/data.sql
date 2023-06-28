INSERT INTO members (username, profile_image_uuid, profile_image_type, created_at, updated_at)
VALUES ('Ari', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Harry', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Null', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Sean', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Jin', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Soy', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Silence', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('Din', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('hahaha', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('ssss', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('die', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('DI__ ', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       ('dIIII', NULL, NULL, '2023-06-09 17:46:10', '2023-06-09 17:46:10');

INSERT INTO channels (owner_id, channel_name, image_uuid, image_type, status, created_at, updated_at)
VALUES (2, 'Knox SRE', NULL, NULL, 'CREATED', '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 'Knox Common', NULL, NULL, 'CREATED', '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 'Knox Portal', NULL, NULL, 'CREATED', '2023-06-09 17:46:10', '2023-06-09 17:46:10');

INSERT INTO channel_members (member_id, channel_id, created_at, updated_at)
VALUES (2, 1, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 2, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 3, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (1, 1, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (8, 1, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (3, 2, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (4, 2, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (5, 3, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (6, 3, '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (7, 3, '2023-06-09 17:46:10', '2023-06-09 17:46:10');

INSERT INTO boards (member_id, channel_id, title, description, status, created_at, updated_at)
VALUES (2, 1, 'title1', 'description1', 'COMPLETED', '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 2, 'title2', 'description2', 'COMPLETED', '2023-06-09 17:46:10', '2023-06-09 17:46:10');

INSERT INTO videos (board_id, thumbnail_image_uuid, thumbnail_image_type, video_uuid, video_type, playing_time,
                    created_at, updated_at)
VALUES (1, 'c0c5afcaaad24d91bfb777440ef3bc12', 'PNG', 'c0c5afcaaad24d91bfb777440ef3bc12', 'MP4', '00:00:26',
        '2023-06-09 17:46:10', '2023-06-09 17:46:10'),
       (2, 'c0c5afcaaad24d91bfb777440ef3bc08', 'PNG', 'c0c5afcaaad24d91bfb777440ef3bc08', 'MP4', '00:00:26',
        '2023-06-09 17:46:10', '2023-06-09 17:46:10');
