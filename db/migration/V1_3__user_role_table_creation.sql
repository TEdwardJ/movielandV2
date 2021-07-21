-- Table: movie.user_role

-- DROP TABLE movie.user_role;

CREATE TABLE IF NOT EXISTS movie.user_role
(
    usr_id numeric NOT NULL,
    role_id numeric NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (usr_id, role_id),
    CONSTRAINT "ROLE_ID_FK" FOREIGN KEY (role_id)
        REFERENCES movie.role (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "USR_ID_FK" FOREIGN KEY (usr_id)
        REFERENCES movie."user" (usr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

/*ALTER TABLE movie.user_role
    OWNER to postgres;*/

INSERT INTO movie.user_role(
	usr_id, role_id)
	VALUES (18, 2);

INSERT INTO movie.user_role(
	usr_id, role_id)
	VALUES (19, 2);

INSERT INTO movie.user_role(
	usr_id, role_id)
	VALUES (20, 1);

commit;
