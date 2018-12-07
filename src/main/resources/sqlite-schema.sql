create table IF NOT EXISTS users
(
  "discord-id" int not null
    constraint users_pk primary key,
  language     text default 'EN'
);
create unique index if not exists "users_discord-id_uindex" on users ("discord-id");

create table IF NOT EXISTS permissions
(
  "discord-id" int not null,
  permission   text
);