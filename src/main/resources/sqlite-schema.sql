create table IF NOT EXISTS users
(
  "discord-id" int not null
    constraint users_pk primary key,
  language     varchar(2) default 'EN'
);

create unique index if not exists "users_discord-id_uindex" on users ("discord-id");