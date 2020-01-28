CREATE TABLE IF NOT EXISTS users
(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255),
	birthday date
);

CREATE TABLE IF NOT EXISTS events
(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name varchar(255),
	base_price decimal(10,2),
	rating varchar(255)
);