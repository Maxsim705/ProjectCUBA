-- begin SHOES_SALE
alter table shoes_sale add constraint FK_SHOES_SALE_ON_PRODUCT foreign key (PRODUCT_ID) references shoes_product(ID)^
create index IDX_SHOES_SALE_ON_PRODUCT on shoes_sale (PRODUCT_ID)^
-- end SHOES_SALE
