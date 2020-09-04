create sequence hibernate_sequence start with 1 increment by 1;

create table movie (id bigint not null, director varchar(128), title varchar(300), year_of_release integer not null, primary key (id));
create table review (id bigint not null, local_date date not null, star integer not null check (star<=5 AND star>=1), user_review varchar(4000), movie_id bigint not null, user_email varchar(255) not null, primary key (id));
create table user (email varchar(255) not null, enabled boolean not null, name varchar(128), password varchar(255), surname varchar(128), primary key (email));
create table user_roles (user_email varchar(255) not null, roles varchar(255));
alter table review add constraint FK8378dhlpvq0e9tnkn9mx0r64i foreign key (movie_id) references movie;
alter table review add constraint FK6hqpdfb0b6wduxedrygq6hv32 foreign key (user_email) references user;
alter table user_roles add constraint FKfinmcawb90mtj05cpco76b963 foreign key (user_email) references user;