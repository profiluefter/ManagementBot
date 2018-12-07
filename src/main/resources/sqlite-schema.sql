create table IF NOT EXISTS users
(
  discordID int not null
    constraint users_pk primary key,
  language     text default 'EN'
);
create unique index if not exists users_discordID_uindex on users (discordID);

create table IF NOT EXISTS permissions
(
  discordID int not null,
  permission   text
);

create unique index if not exists permissions_discordID_permission on permissions (discordID,permission);