create table TASKS (
    ID int not null auto_increment,
    TITLE varchar(100) not null,
    COMPLETED tinyint not null default false
);

INSERT INTO TASKS (TITLE, COMPLETED) VALUES ('Call John', false);
INSERT INTO TASKS (TITLE, COMPLETED) VALUES ('Read all inbox mails', false);
INSERT INTO TASKS (TITLE, COMPLETED) VALUES ('Wake up at 9:00', true);