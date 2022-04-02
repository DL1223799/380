CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('keith', '{noop}keithpw');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users VALUES ('mary', '{noop}marypw');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');

INSERT INTO users VALUES ('david', '{noop}davidpw');
INSERT INTO user_roles(username, role) VALUES ('david', 'ROLE_USER');

INSERT INTO users VALUES ('andy', '{noop}andypw');
INSERT INTO user_roles(username, role) VALUES ('andy', 'ROLE_USER');

INSERT INTO users VALUES ('samuel', '{noop}samuelpw');
INSERT INTO user_roles(username, role) VALUES ('samuel', 'ROLE_USER');

INSERT INTO users VALUES ('marco', '{noop}marcopw');
INSERT INTO user_roles(username, role) VALUES ('marco', 'ROLE_USER');

INSERT INTO users VALUES ('hello', '{noop}hellopw');
INSERT INTO user_roles(username, role) VALUES ('hello', 'ROLE_USER');