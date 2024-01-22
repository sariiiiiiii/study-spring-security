-- 사용자 정보 등록
INSERT INTO member
(`id`, `user_id`, `name`, `password`)
VALUES(1, 'system', 'system', '$2a$10$6iJv7gmGXdP2Sf0ojKBfwuUgxaHqv0c3f50SfDNdxNxq.dSymAAlW');

INSERT INTO member
(`id`, `user_id`, `name`, `password`)
VALUES(2, 'user', 'user', '$2a$10$6iJv7gmGXdP2Sf0ojKBfwuUgxaHqv0c3f50SfDNdxNxq.dSymAAlW');

INSERT INTO member
(`id`, `user_id`, `name`, `password`)
VALUES(3, 'playground', 'playground', '$2a$10$6iJv7gmGXdP2Sf0ojKBfwuUgxaHqv0c3f50SfDNdxNxq.dSymAAlW');

INSERT INTO member
(`id`, `user_id`, `name`, `password`)
VALUES(4, 'A', 'A', '$2a$10$6iJv7gmGXdP2Sf0ojKBfwuUgxaHqv0c3f50SfDNdxNxq.dSymAAlW');

INSERT INTO member
(`id`, `user_id`, `name`, `password`)
VALUES(5, 'B', 'B', '$2a$10$6iJv7gmGXdP2Sf0ojKBfwuUgxaHqv0c3f50SfDNdxNxq.dSymAAlW');

-- 역할 등록
INSERT INTO role
(`id`, `name`)
VALUES(1, 'ROLE_SYSTEM');

INSERT INTO role
(`id`, `name`)
VALUES(2, 'ROLE_USER');

INSERT INTO role
(`id`, `name`)
VALUES(3, 'ROLE_BOARD');

-- 데이터를 생성할 수 있는 권한 등록
INSERT INTO role
(`id`, `name`)
VALUES(4, 'OP_CREATE_DATA');

-- 데이터를 삭제할 수 있는 권한 등록
INSERT INTO role
(`id`, `name`)
VALUES(5, 'OP_DELETE_DATA');

-- 사용자 별 역할 등록
INSERT INTO member_roles
(`member_id`, `role_id`)
VALUES(1, 1);

-- A 관리자에게 SYSTEM 역할과 OP_CREATE_DATA 권한 등록
INSERT INTO member_roles
(`member_id`, `role_id`)
VALUES(4, 1);
INSERT INTO member_roles
(`member_id`, `role_id`)
VALUES(4, 4);

-- B 관리자에게 SYSTEM 역할과 OP_DELETE_DATA 권한 등록
INSERT INTO member_roles
(`member_id`, `role_id`)
VALUES(5, 1);

INSERT INTO member_roles
(`member_id`, `role_id`)
VALUES(5, 5);
