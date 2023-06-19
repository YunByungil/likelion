insert into users (email, nickname, password, role, username, created_At, updated_At, deleted_At) values ('test@naver.com', '테스트1', '1234', 'USER', '테스트닉넹미1', NOW(), NOW(), null)
insert into post (title, content, author, created_At, updated_At, deleted_At, user_id) values ('제목1', '내용1', 'user1', NOW(), NOW(), null, 1)
insert into post (title, content, author, created_At, updated_At, deleted_At, user_id) values ('제목2', '내용2', 'user2', NOW(), NOW(), null, 1)
insert into post (title, content, author, created_At, updated_At, deleted_At, user_id) values ('제목3', '내용3', 'user3', NOW(), NOW(), null, 1)
