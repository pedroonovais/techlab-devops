create table iot (
    id           bigserial primary key,
    ativo        boolean not null,
    bateria      integer,
    coordenada_x varchar(255) not null,
    coordenada_y varchar(255) not null
);