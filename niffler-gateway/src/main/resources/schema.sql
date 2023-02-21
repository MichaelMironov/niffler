-- create database "niffler-gateway" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists categories
(
    id          UUID unique  not null default uuid_generate_v1() primary key,
    category varchar(255) not null
);

alter table categories
    owner to postgres;

create table if not exists spends
(
    id          UUID unique  not null default uuid_generate_v1() primary key,
    username    varchar(50)  not null,
    spend_date  date         not null,
    currency    varchar(50)  not null,
    amount      float        not null,
    category varchar(255) not null,
    category_id UUID         not null,
    constraint fk_spends_categories foreign key (category_id) references categories (id)
);

alter table spends
    owner to postgres;

-- delete from categories;
-- insert into categories (category) values ('Рестораны');
-- insert into categories (category) values ('Продуктовые магазины');
-- insert into categories (category) values ('Обучение в QA.GURU ADVANCED');

-- insert into spends (id, username, spend_date, currency, amount, category, category_id)
-- values (uuid_generate_v1(), 'dima', date('2023-02-15'), 'RUB', 100.0, 'Радостная покупка', (select id from categories where category = 'Обучение в QA.GURU ADVANCED'));
-- insert into spends (id, username, spend_date, currency, amount, category, category_id)
-- values (uuid_generate_v1(), 'dima', date('2023-02-15'), 'RUB', 500.0, 'Радостная покупка', (select id from categories where category = 'Рестораны'));
-- insert into spends (id, username, spend_date, currency, amount, category, category_id)
-- values (uuid_generate_v1(), 'dima', date('2023-02-15'), 'RUB', 10000.0, 'Радостная покупка', (select id from categories where category = 'Продуктовые магазины'));