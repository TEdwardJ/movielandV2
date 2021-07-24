INSERT INTO movie."user"(usr_id, usr_name, usr_email, usr_sole, usr_password_enc)
VALUES (220, 'testUser', '${email}', '${salt}', '${password}');

INSERT INTO USER_ROLE VALUES(220, ${role});
INSERT INTO USER_ROLE VALUES(220, 1);