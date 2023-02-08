CREATE TABLE IF NOT EXISTS persons (
    id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) UNIQUE,
    pwd VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS questions (
    id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) UNIQUE,
    answers VARCHAR(100) ARRAY,
    correct_answer_idx INT,
    answer_type VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS answers (
    id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_value VARCHAR(255),
    person_id LONG,
    question_id LONG,
    result BOOL,

    FOREIGN KEY (person_id) REFERENCES persons(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);