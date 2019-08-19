create table surveys (
    id bigint not null auto_increment,
    name varchar(255) not null,
    response_count integer not null default 0,
    PRIMARY KEY (id)
) engine=InnoDB;

create table questions (
    id bigint not null auto_increment,
    text varchar(255) not null,
    type integer not null,
    survey_id bigint not null,
    PRIMARY KEY (id),
    FOREIGN KEY (survey_id) REFERENCES surveys(id)
) engine=InnoDB;

create table answer_options (
    id bigint not null auto_increment,
    text varchar(255) not null,
    question_id bigint not null,
    PRIMARY KEY (id)
) engine=InnoDB;

create table response_surveys (
    id bigint not null auto_increment,
    survey_id bigint not null,
    PRIMARY KEY (id),
    FOREIGN KEY (survey_id) REFERENCES surveys(id)
) engine=InnoDB;

create table selected_answers (
    id bigint not null auto_increment,
    question_id bigint not null,
    response_survey_id bigint not null,
    answer_id bigint not null,
    primary key (id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (response_survey_id) REFERENCES response_surveys(id),
    FOREIGN KEY (answer_id) REFERENCES answer_options(id)
) engine=InnoDB;

create table string_answers (
    id bigint not null auto_increment,
    question_id bigint not null,
    response_survey_id bigint not null,
    answer varchar(255) not null,
    primary key (id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (response_survey_id) REFERENCES response_surveys(id)
) engine=InnoDB;

# For hibernate DAO Implementation
#
# create table answer_seq (
#     next_val bigint
# ) engine=InnoDB;
#
# insert into answer_seq values (1);
#
# create table selected_answers (
#     id bigint not null,
#     question_id bigint not null,
#     response_survey_id bigint not null,
#     answer_id bigint not null,
#     primary key (id),
#     FOREIGN KEY (question_id) REFERENCES questions(id),
#     FOREIGN KEY (response_survey_id) REFERENCES response_surveys(id),
#     FOREIGN KEY (answer_id) REFERENCES answer_options(id)
# ) engine=InnoDB;
#
# create table string_answers (
#     id bigint not null,
#     question_id bigint not null,
#     response_survey_id bigint not null,
#     answer varchar(255) not null,
#     primary key (id),
#     FOREIGN KEY (question_id) REFERENCES questions(id),
#     FOREIGN KEY (response_survey_id) REFERENCES response_surveys(id)
# ) engine=InnoDB;
