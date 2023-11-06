create table Character(
    id bigserial PRIMARY KEY,
    name text UNIQUE,
    race text,
    rank numeric,
    home_world text
);


insert into Character ( name, race, rank, home_world) values ('Sub-Zero', 'Human', 10, 'Earthrealm');
insert into Character ( name, race, rank, home_world) values ('Kenshi', 'Human', 1, 'Earthrealm');
insert into Character ( name, race, rank, home_world) values ('Baraka', 'Tarkatan', 2, 'Outworld');
insert into Character ( name, race, rank, home_world) values ('Johnny Cage', 'Human', 3, 'Earthrealm');
insert into Character ( name, race, rank, home_world) values ('Ashrah', 'Demon', 4, 'Netherrealm');
insert into Character ( name, race, rank, home_world) values ('Mileena', 'Edenia', 5, 'Outworld');
insert into Character ( name, race, rank, home_world) values ('Geras', 'Creation', 6, 'Out of time');
insert into Character ( name, race, rank, home_world) values ('Raiden', 'Human', 7, 'Earthrealm');
insert into Character ( name, race, rank, home_world) values ('Rain', 'Edenia', 8, 'Outworld');
insert into Character ( name, race, rank, home_world) values ('Smoke', 'Human', 9, 'Earthrealm');
insert into Character ( name, race, rank, home_world) values ( 'Shan Tsung', 'Human', 11, 'Outworld')