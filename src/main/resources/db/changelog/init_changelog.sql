-- liquibase formatted sql

-- changeset guardiankiller:1
-- Added roles table
-- Added users table
-- Added users_roles table
create table roles
(
    id   bigint                            not null auto_increment,
    name enum ('ADMIN','MODERATOR','USER') not null,
    primary key (id)
) engine = InnoDB;

create table users
(
    id        bigint      not null auto_increment,
    username  varchar(20) not null UNIQUE,
    full_name varchar(36) not null,
    email     varchar(40) not null UNIQUE,
    password  varchar(300) not null,
    primary key (id)
) engine = InnoDB;

create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    foreign key (role_id) references roles (id),
    foreign key (user_id) references users (id),
    primary key (user_id, role_id)
) engine = InnoDB;

-- changeset guardiankiller:2
-- Added i18n table
create table i18n_strings (
    id           bigint           not null auto_increment,
    placeholder  binary(16)       not null,
    lang         enum ('EN','BG') not null,
    val          varchar(300)     not null,
    primary key (id),
    unique  key unq_id (placeholder, lang)
) engine = InnoDB;
