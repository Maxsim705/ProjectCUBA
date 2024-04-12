-- begin SHOES_PRODUCT
create table shoes_product (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    name varchar(255) not null,
    type varchar(50),
    brand varchar(255),
    size integer not null,
    gender_age varchar(50),
    color varchar(255),
    season varchar(50),
    product_year integer,
    guarantee double precision,
    code varchar(255) not null,
    price decimal(19, 2) not null,
    amount integer not null,
    --
    primary key (ID)
);
-- end SHOES_PRODUCT
-- begin SHOES_SALE
create table shoes_sale (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    product_id uuid not null,
    date timestamp not null,
    price decimal(19, 2) not null,
    amount integer not null,
    --
    primary key (ID)
)^
-- end SHOES_SALE
