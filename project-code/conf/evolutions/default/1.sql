# --- !Ups
create table "User2" (
    "username" varchar(128) not null,
    "id" bigint primary key not null
  );
create sequence "s_User2_id";
create table "Identity" (
    "email" varchar(128) not null,
    "id" bigint primary key not null
  );
create sequence "s_Identity_id";


# --- !Downs
drop table Identity;
drop table User2;
