CREATE TABLE users (
    username varchar(50) not null,
    password VARCHAR(50) NOT NULL,
    full_name varchar(50) not null,
    phone_number varchar(8) not null,
    delivery_address varchar(255) not null,
    primary key (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('keith', '{noop}keithpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users VALUES ('mary', '{noop}marypw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');

INSERT INTO users VALUES ('kelivin', '{noop}kelivinpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('kelivin', 'ROLE_USER');

INSERT INTO users VALUES ('andy', '{noop}andynpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('andy', 'ROLE_USER');

INSERT INTO users VALUES ('marco', '{noop}marconpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('marco', 'ROLE_USER');

INSERT INTO users VALUES ('samuel', '{noop}samuelnpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('samuel', 'ROLE_USER');

INSERT INTO users VALUES ('david', '{noop}davidnpw','ouhk', '38026567', 'ouhk');
INSERT INTO user_roles(username, role) VALUES ('david', 'ROLE_USER');