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
    username  varchar(14) not null UNIQUE,
    full_name varchar(36) not null,
    email     varchar(40) not null UNIQUE,
    password  varchar(200) not null,
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
