CREATE TABLE IF NOT EXISTS users
(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255),
	birthday date
);