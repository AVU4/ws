create table Character(
    id bigserial PRIMARY KEY,
    name text UNIQUE,
    race text,
    rank numeric,
    home_world text
);


insert into Character (id, name, race, rank, home_world) values (1,'Sub-Zero', 'Human', 10, 'Earthrealm');
insert into Character (id, name, race, rank, home_world) values (4,'Kenshi', 'Human', 1, 'Earthrealm');
insert into Character (id, name, race, rank, home_world) values (3,'Baraka', 'Tarkatan', 2, 'Outworld');
insert into Character (id, name, race, rank, home_world) values (8,'Johnny Cage', 'Human', 3, 'Earthrealm');
insert into Character (id, name, race, rank, home_world) values (2,'Ashrah', 'Demon', 4, 'Netherrealm');
insert into Character (id, name, race, rank, home_world) values (5,'Mileena', 'Edenia', 5, 'Outworld');
insert into Character (id, name, race, rank, home_world) values (9,'Geras', 'Creation', 6, 'Out of time');
insert into Character (id, name, race, rank, home_world) values (7,'Raiden', 'Human', 7, 'Earthrealm');
insert into Character (id, name, race, rank, home_world) values (10,'Rain', 'Edenia', 8, 'Outworld');
insert into Character (id, name, race, rank, home_world) values (6,'Smoke', 'Human', 9, 'Earthrealm');
insert into Character (id, name, race, rank, home_world) values (11, 'Shan Tsung', 'Human', 11, 'Outworld')