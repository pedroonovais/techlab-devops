create table moto (
    id           bigserial primary key,
    modelo       varchar(255) not null,
    placa        varchar(255) not null unique,
    data_entrada timestamp not null,
    data_saida   timestamp
);