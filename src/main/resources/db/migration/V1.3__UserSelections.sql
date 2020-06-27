create table user_week
(
    id         text primary key,
    swami_user text,
    week       text,
    submitted  bool default false
);

create table user_selection
(
    id         text primary key,
    swami_user text,
    selection  text
)
