create schema sify_schema;

create table sify_schema.ship (
    size  float(53),
    id    bigint not null,
    model varchar(255),
    name  varchar(255),
    primary key (id)
);