CREATE TABLE course (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE attachment (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(255) DEFAULT NULL,
    content_type VARCHAR(255) DEFAULT NULL,
    content BLOB DEFAULT NULL,
    course_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);
create table Course_user_Comments(
    comment_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    course_id integer not null,
    comment varchar(255) not null,
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (username) REFERENCES users(username)
);
