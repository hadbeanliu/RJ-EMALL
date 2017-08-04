drop table if exists EML_ORDER;
drop table if exists EML_ORDER_GOODS;
drop table if exists EML_PAY_SERVICE_PROVIDER;
drop table if exists EML_LOGISTICS;
drop table if exists eml_user;
drop table if exists eml_address;

create table EML_ORDER (
    ID varchar(16),
    ORDER_NO varchar(32),
    PRICE varchar(32),
    PAY_SP_ID varchar(16),
    PAY_CODE varchar(64),
    LOGISTICS_ID varchar(32),
    STATUS varchar(1),
    STORE_ID varchar(16),
    CREATE_USER varchar(32),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    DEAL_TIME TIMESTAMP,
    PAYMENT_TIME TIMESTAMP,
    CONFIRM_USER varchar(32),
    STORE_CONFIRM_TIME TIMESTAMP,
    DONE_TIME TIMESTAMP,
    primary key (ID)
);

create table EML_ORDER_GOODS (
    ID varchar(16),
    ORDER_ID varchar(32),
    GOODS_ID varchar(32),
    GOODS_TITLE varchar(100),
    GOODS_PRICE varchar(64),
    PURCHASE_PRICE varchar(64),
    PURCHASE_QUANTITY number,
    CREATE_USER varchar(32),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    primary key (ID)
);


create table EML_PAY_SERVICE_PROVIDER (
    ID varchar(16),
    TITLE varchar(64),
    CODE varchar(32),
    URL varchar(500),
    DESCRIPTION varchar(5000),
    LOGO varchar(64),
    STATUS varchar(1),
    CREATE_TIME TIMESTAMP,
    MODIFY_TIME TIMESTAMP,
    primary key (ID)
);


create table EML_LOGISTICS (
    ID varchar(16),
    primary key (ID)   
);

create table eml_user (
    user_id varchar(16),
	login_name varchar(40) not null,
	password varchar(20) not null,
	nick_name varchar(255),
	sex varchar(1) ,
    birthday date ,
    mobiletel varchar(11),
    email varchar(255),
    is_activeemail varchar(1) ,
    register_time date ,
    is_del varchar(1),
    primary key (user_id)   
);

create table eml_address (
    address_id varchar(16),
	user_id varchar(16) not null,
	address varchar(200) not null,
	receiver varchar(20),
	mobiletel varchar(11),
    zipcode varchar(8),
    phone varchar(18),
    remark varchar(255),
    is_default varchar(1),
    is_del varchar(1), 
    primary key (address_id)  
);

