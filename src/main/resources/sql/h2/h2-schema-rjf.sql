drop table if exists EML_ORDER;
drop table if exists EML_ORDER_GOODS;
drop table if exists EML_PAY_SERVICE_PROVIDER;
drop table if exists EML_LOGISTICS;
drop table if exists EML_PAYMENT;

create table EML_ORDER (
    ID varchar(16),
    ORDER_NO varchar(32),
    PRICE INT(16),
    PAY_CODE varchar(64),
    LOGISTICS_ID varchar(32),
    ADDRESS_ID varchar(16),
    STATUS varchar(2),
    TYPE varchar(2),
    STORE_ID varchar(16),
    CREATE_USER varchar(32),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    DEAL_TIME TIMESTAMP,
    TRADE_NO varchar(64),
    TRADE_STATUS varchar(32),
    PAYMENT_TIME TIMESTAMP,
    CONFIRM_USER varchar(32),
    STORE_CONFIRM_TIME TIMESTAMP,
    DONE_TIME TIMESTAMP,
    RATE_ID varchar(32),
    RATE_TIME TIMESTAMP,
    REMARK varchar(500), 
    primary key (ID)
);

create table EML_ORDER_GOODS (
    ID varchar(16),
    ORDER_ID varchar(32),
    GOODS_ID varchar(32),
    GOODS_TITLE varchar(100),
    GOODS_URL varchar(100),
    GOODS_IMAGE varchar(100),
    GOODS_DESC varchar(500),
    GOODS_PRICE INT(16),
    PURCHASE_PRICE INT(16),
    DISCOUNT INT(16),
    DISCOUNT_TITLE varchar(100),
    SKU_ID varchar(64),
    PURCHASE_QUANTITY number,
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    RATE_ID varchar(32),
    RATE_TIME TIMESTAMP,
    primary key (ID)
);


create table EML_PAY_SERVICE_PROVIDER (
    ID varchar(16),
    TITLE varchar(64),
    CODE varchar(32),
    URL varchar(500),
    CONFIG varchar(1000),
    DESCRIPTION varchar(5000),
    LOGO varchar(64),
    PLUGIN_VERSION varchar(16), 
    STATUS varchar(1),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    primary key (ID)
);

create table EML_PAYMENT (
    ID varchar(16),
    PAY_SERVICE_PROVIDER_CODE varchar(64),
    PAY_SERVICE_PROVIDER_TITLE varchar(128),
    PAY_SERVICE_PROVIDER_DESC varchar(500),
    STORE_ID varchar(16),
    CONFIG varchar(1000),
    STATUS varchar(1),
    CREATE_USER varchar(32),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    primary key (ID)
);


create table EML_LOGISTICS (
    ID varchar(16),
    primary key (ID)   
);