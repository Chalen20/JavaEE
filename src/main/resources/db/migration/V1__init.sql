create table users
(
    id       int primary key auto_increment,
    login    varchar(30)  not null,
    password varchar(255) not null,
    unique uniq_login (login)
);

create table permissions
(
    id         int primary key auto_increment,
    permission varchar(30) not null,
    unique uniq_permission (permission)
);

create table user_to_permissions
(
    user_id       int not null,
    permission_id int not null,
    constraint fk_user_to_permission_user foreign key (user_id) references users (id),
    constraint fk_user_to_permission_permission foreign key (permission_id) references permissions (id)
);

create table books
(
    isbn   varchar(255) not null,
    author varchar(255),
    title  varchar(255),
    primary key (isbn)
);

create table user_to_book
(
    user_id integer      not null,
    book_id varchar(255) not null,
    constraint fk_user_to_book_user foreign key (user_id) references users (id),
    constraint fk_user_to_book_book foreign key (book_id) references books (isbn)
);

insert into users (login, password)
values ('admin', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484'),//password
       ('user', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');//password

insert into permissions (permission)
values ('VIEW_ADMIN');

insert into user_to_permissions (user_id, permission_id)
values ((select id from users where login = 'admin'), (select id from permissions where permission = 'VIEW_ADMIN'));

insert into books (isbn, title, author)
values ('9783161484100', 'title1', 'author1'),
       ('9781411686915', 'title2', 'author2'),
       ('9781782808084', 'title3', 'author3'),
       ('9780123456786', 'title4', 'author4'),
       ('9791234567896', 'title5', 'author5'),
       ('9781888799972', 'title6', 'author6'),
       ('9780936385402', 'title7', 'author7'),
       ('9780976773665', 'title8', 'author8'),
       ('9780140266900', 'title9', 'author9'),
       ('9782123456803', 'title10', 'author10'),
       ('9780075752127', 'title11', 'author11');