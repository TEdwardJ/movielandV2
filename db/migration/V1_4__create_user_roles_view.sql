-- View: movie.v_user_roles

-- DROP VIEW movie.v_user_roles;

CREATE OR REPLACE VIEW movie.v_user_roles
 AS
 SELECT u.usr_id,
    u.usr_name,
    u.usr_email,
    u.usr_password,
    u.usr_sole,
    u.usr_password_enc,
    r.role_id,
    r.role_name
   FROM movie."user" u
     JOIN movie.user_role ur ON u.usr_id = ur.usr_id
     JOIN movie.role r ON r.role_id = ur.role_id;

/*ALTER TABLE movie.v_user_roles
    OWNER TO postgres;*/

