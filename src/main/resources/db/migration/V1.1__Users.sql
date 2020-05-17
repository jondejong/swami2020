create table swami_user
(
    id         text primary key,
    first_name text,
    last_name  text,
    email      text,
    password   text,
    salt       text,
    token      text
);

create table role
(
    id          text primary key,
    name        text,
    description text
);

create table user_role
(
    swami_user text,
    role       text
);

insert into swami_user(id, first_name, last_name, email, password, salt)
values ('49c4db64-acd1-431f-b013-7f35895ec85b',
        'Test',
        'User',
        'test.user@testemail.com',
        'MoStxIlMY9189VNt+/qB/8kZKyuF+rjCqZDrAeyFOBw=',
        'f91768a8-64e4-4e71-b4dc-b188b83faf26');

insert into swami_user(id, first_name, last_name, email, password, salt)
values ('f438f2ee-28ae-482f-b6a6-8c7474510092',
        'Test',
        'Admin',
        'test.admin@testemail.com',
        'q7dC9u2gJxUCZde/bo8h0C/jUeJ6C2imJBjt9lfGTzM=',
        '91ee8e61-581d-4eb3-98fc-61748fdf8864');

insert into role(id, name, description)
values ('d3574af2-2c30-415e-8c35-4c7ab01f4e40', 'USER', 'Basic Swami User');

insert into role(id, name, description)
values ('93a27a86-8106-403e-85b7-6ac0ef72b5d8', 'ADMIN', 'Superuser');

insert into user_role(swami_user, role)
values ('49c4db64-acd1-431f-b013-7f35895ec85b', 'd3574af2-2c30-415e-8c35-4c7ab01f4e40');

insert into user_role(swami_user, role)
values ('f438f2ee-28ae-482f-b6a6-8c7474510092', 'd3574af2-2c30-415e-8c35-4c7ab01f4e40');

insert into user_role(swami_user, role)
values ('f438f2ee-28ae-482f-b6a6-8c7474510092', '93a27a86-8106-403e-85b7-6ac0ef72b5d8');

