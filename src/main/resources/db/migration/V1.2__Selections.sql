create table week
(
    id       text primary key,
    ready    bool,
    complete bool,
    locked   bool,
    number   int
);

insert into week(id, number, ready, locked, complete)
values ('7cbdd6bf-7545-4091-a227-4f29450632d7', 1, false, false, false);

insert into week(id, number, ready, locked, complete)
values ('3d581ea2-b3c8-4576-87df-683eb4abd8ee', 2, false, false, false);

insert into week(id, number, ready, locked, complete)
values ('232a5c59-8a23-4917-a4cf-7f0d367cb4ad', 3, false, false, false);

insert into week(id, number, ready, locked, complete)
values ('39cdeea0-9e2b-4f61-90ad-e968cc25a799', 4, false, false, false);

insert into week(id, number, ready, locked, complete)
values ('a74adaa6-3098-4fcf-b4d9-9b04c64d9bd0', 5, false, false, false);

create table selection
(
    id       text primary key,
    game     text,
    team     text,
    home     bool,
    favorite bool,
    score    int
);

create table game
(
    id        text primary key,
    week      text,
    cancelled boolean,
    complete  boolean,
    spread    numeric
);

-- TODO: Temp testing data to go away
-- insert into game(id, week, cancelled, complete)
-- values('7f596c51-de27-41de-9f1c-998ad0d10ad0', '7cbdd6bf-7545-4091-a227-4f29450632d7', false, false);
--
-- insert into selection
-- (id, game, team, spread, home, favorite)
-- values
-- ('3cde4e04-5bae-48cb-8269-0e1daacb4d20', '7f596c51-de27-41de-9f1c-998ad0d10ad0', '9579145e-1946-4f42-9c47-42fefb4eb8e6', -7.5, true, true);
--
-- insert into selection
-- (id, game, team, spread, home, favorite)
-- values
-- ('e8b43549-11e9-48c3-98c8-2be88135cb41', '7f596c51-de27-41de-9f1c-998ad0d10ad0', 'fc623823-a492-4264-a960-8d5053d16f08', 7.5, false, false);