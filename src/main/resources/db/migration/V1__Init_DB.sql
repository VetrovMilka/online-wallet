CREATE TABLE authorities
(
    user_id   int8 NOT NULL,
    authority varchar(255) NOT NULL,
    PRIMARY KEY (user_id, authority)
);

create table profiles
(
    id              int8 generated by default as identity,
    user_id         int8           not null,
    first_name      varchar(50)    not null,
    last_name       varchar(50)    not null,
    email           varchar(255)   not null,
    balance         numeric(19, 2) not null,
    activation_code varchar(255),
    created_date    timestamp      not null,
    primary key (id)
);

create table transactions
(
    id               int8 generated by default as identity,
    profile_id       int8           not null,
    category_id      int8           not null,
    is_income        boolean        not null,
    amount           numeric(19, 2) not null,
    message          varchar(255),
    transaction_date date           not null,
    primary key (id)
);

create table transactions_categories
(
    id        int8 generated by default as identity,
    category  varchar(50) not null,
    is_income boolean,
    primary key (id)
);

create table users
(
    id       int8 generated by default as identity,
    username varchar(50)  not null,
    password varchar(500) not null,
    enabled  boolean      not null,
    primary key (id)
);

alter table if exists profiles
    add constraint profiles_user_id_key unique (user_id);

alter table if exists authorities
    add constraint authorities_user_id_fk foreign key (user_id) references users on
        delete
        cascade on
        update cascade;

alter table if exists profiles
    add constraint profiles_user_id_fk foreign key (user_id) references users on
        delete
        cascade on
        update cascade;

alter table if exists transactions
    add constraint transactions_category_id_fk foreign key (category_id) references transactions_categories on
        delete
        cascade on
        update cascade;

alter table if exists transactions
    add constraint transactions_profile_id_fk foreign key (profile_id) references profiles on
        delete
        cascade on
        update cascade;