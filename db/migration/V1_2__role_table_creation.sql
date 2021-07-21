-- Table: movie.role

-- DROP TABLE movie.role;

CREATE TABLE IF NOT EXISTS movie.role
(
    role_id numeric NOT NULL,
    role_name character varying(64) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_id)
)

TABLESPACE pg_default;

/*ALTER TABLE movie.role
    OWNER to postgres;*/

INSERT INTO movie.role(
	role_id, role_name)
	VALUES (1, 'ADMIN');

INSERT INTO movie.role(
	role_id, role_name)
	VALUES (2, 'USER');

commit;