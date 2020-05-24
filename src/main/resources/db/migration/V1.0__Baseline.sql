create table conference
(
    id   text primary key,
    name text
);

create table team
(
    id         text primary key,
    name       text,
    nick_name  text,
    conference text
);

INSERT INTO conference(id, name)
VALUES ('82855b7b-f127-4f03-b95b-ec346a6a37b1', 'Big Ten');
INSERT INTO conference(id, name)
VALUES ('6b5daddb-e5e8-45ef-b916-a6752963bc56', 'SEC');
INSERT INTO conference(id, name)
VALUES ('7cebf93f-f7db-499a-b591-72ce4a52264c', 'Pac 12');
INSERT INTO conference(id, name)
VALUES ('8aabcde0-f9a7-429c-8043-0e07294c4a9c', 'Big 12');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('9579145e-1946-4f42-9c47-42fefb4eb8e6', 'Iowa', 'Hawkeyes', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('c7f2fdf8-b71e-48fe-b5fa-ac4c8c2cf993', 'Nebraska', 'Cornhuskers', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('f56f5dd1-9169-44c6-a4df-fed0e83b10a7', 'Illinois', 'Illini', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('6a69dd3c-3612-46dd-8e86-35a9d366dc1e', 'Indiana', 'Hoosiers', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('5e4c2d5c-74cb-4593-863c-f8426d5d1186', 'Maryland', 'Terrapins', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('2ff27413-db27-4bc3-9d33-c0877de7914e', 'Michigan', 'Wolverines', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('fc623823-a492-4264-a960-8d5053d16f08', 'Minnesota', 'Golden Gophers', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('1467146a-4d6d-46da-b37c-d0e1d4c84d94', 'Michigan State', 'Spartans', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('1f5fecf0-d0af-4b17-8a42-85da214873d5', 'Ohio State', 'Buckeyes', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('1be4e52a-4b33-49cd-92ef-79d866f5080a', 'Penn State', 'Nittany Lions', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('ddf87cbe-3f54-4eb6-87dc-3538dd8a7b9a', 'Northwestern', 'Wildcats', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('8561e3ae-4f27-4d2b-8e00-459ff8affaf2', 'Purdue', 'Boilermakers', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('316ed76c-a571-4a63-b1fb-880836160b30', 'Rutgers', 'Scarlet Knights', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('e4a93a91-a8ee-433d-a300-5180929d9387', 'Wisconsin', 'Badgers', '82855b7b-f127-4f03-b95b-ec346a6a37b1');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('764feb7e-8d4f-40c8-a9eb-f5a041c8bda7', 'Alabama', 'Crimson Tide', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('7b942e68-8917-4a06-a53a-6fd6aa404692', 'Arkansas', 'Razorbacks', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('14062b04-c484-4c05-bb9c-22c4313b5a56', 'Auburn', 'Tigers', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('90d65d55-bfa5-4139-942a-48c1b24a6e29', 'Florida', 'Gators', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('ffc75815-5ef8-4966-9aaa-be81f46c619d', 'Georgia', 'Bulldogs', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('7f4dad78-89b5-4978-818b-ca700a3fe8f8', 'Kentucky', 'Wildcats', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('9d13d193-6437-4912-a1a7-782a2dda70c8', 'LSU', 'Tigers', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('8ba32f71-416e-471a-bd30-ef0e97914697', 'Mississippi', 'Bulldogs', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('6fc99a73-15ea-4fcb-bef6-b37e37f74d17', 'Missouri', 'Tigers', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('82381e08-f705-4609-9638-9e17d469cd15', 'Ole Miss', 'Rebels', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('a662807d-6da9-4d24-b69c-91dc4dbf0343', 'South Carolina', 'Gamecocks', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('0064012f-8912-49e3-a7cf-27438c9ee92a', 'Tennessee', 'Volunteers', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('08d9103a-513c-497e-9a82-15702e7ec01b', 'Texas A&M', 'Aggies', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('e6a145cb-8b3c-40e7-bc5e-1a1e45b53474', 'Vanderbilt', 'Commodores', '6b5daddb-e5e8-45ef-b916-a6752963bc56');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('8773d7fd-3091-4555-be6c-e7eddeecab37', 'Arizona', 'Wildcats', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('44219a50-683c-4d02-936b-b85756dc6077', 'Arizona State', 'Sun Devils', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('a48c9385-a0ac-4915-9535-b83337ad6958', 'Cal', 'Golden Bears', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('270e5bf0-451b-469c-bc7e-ad3063ea052d', 'Colorado', 'Buffaloes', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('6c2917ce-8fa6-4929-840a-f19d2532daa8', 'Oregon', 'Ducks', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('3da84ac8-18fc-4d23-80b3-b5724383e182', 'Oregon State', 'Beavers', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('79016ef7-7aef-4b8a-b3d8-e649c28f5d58', 'Stanford', 'Cardinal', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('9c45e172-295f-474e-8043-efc29a5b8824', 'UCLA', 'Bruins', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('2f400522-9e71-4cbf-964b-85d0b2899bc1', 'USC', 'Trojans', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('5a0d8412-beef-4689-a20b-f7982921c0e4', 'Utah', 'Utes', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('69bfd71c-7b70-49fc-977e-5d889c1f0a98', 'Washington', 'Huskies', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('7d483a14-0e48-4b01-8c23-61a39fb4a5c5', 'Washington State', 'Cougars', '7cebf93f-f7db-499a-b591-72ce4a52264c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('10663162-3a6e-4797-bfd7-307e6620b6f4', 'Baylor', 'Bears', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('7bb0fcf1-6df5-44e7-a091-4fcd477ff4d2', 'Iowa State', 'Cyclones', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('e007ffb2-0123-40db-bf35-deb729e4e12b', 'Kansas', 'Jayhawks', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('b340f879-ac14-42f0-836f-7b175441b5dc', 'Kansas State', 'Wildcats', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('21ad7258-85aa-4666-adce-f8dc73fc4a2e', 'Oklahoma', 'Sooners', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('9b1f1098-fda4-4dc9-8545-ce07108a870d', 'Oklahoma State', 'Cowboys', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('9a886c7f-ee96-4e7e-9bd1-13921c02f505', 'TCU', 'Horned Frogs', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('8cec7f25-1b03-4ae5-b90f-1caa8721c98b', 'Texas', 'Longhorns', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('f84e7440-d3e1-4a8c-8fee-c43720bdf15b', 'Texas Tech', 'Red Raiders', '8aabcde0-f9a7-429c-8043-0e07294c4a9c');
INSERT INTO team(id, name, nick_name, conference)
VALUES ('ab1551e1-1c2e-4f38-a68c-e051195d76d1', 'West Virginia', 'Mountaineers',
        '8aabcde0-f9a7-429c-8043-0e07294c4a9c');

