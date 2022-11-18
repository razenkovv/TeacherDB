CREATE TABLE subjects
(
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE teachers
(
    full_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (full_name)
);

CREATE TABLE timetable
(
    audience       VARCHAR(20)  NOT NULL,
    weekday        VARCHAR(20)  NOT NULL,
    student_amount INT          NOT NULL,
    subject        VARCHAR(50)  NOT NULL,
    teacher        VARCHAR(100) NOT NULL,
    id_tt          SERIAL,
    FOREIGN KEY (subject) REFERENCES subjects,
    FOREIGN KEY (teacher) REFERENCES teachers,
    PRIMARY KEY (id_tt)
);

CREATE TABLE link_subject_teacher
(
    lesson_amount INT          NOT NULL,
    subject       VARCHAR(50)  NOT NULL,
    teacher       VARCHAR(100) NOT NULL,
    id_link       SERIAL,
    FOREIGN KEY (subject) REFERENCES subjects,
    FOREIGN KEY (teacher) REFERENCES teachers,
    PRIMARY KEY (id_link)
);