drop table if exists eml_refund;

drop table if exists eml_inventory;


create table eml_refund (
    refund_id varchar(16),
	order_id varchar(16),
	handling_suggestion varchar(200),
	buyer_refund_reason varchar(200),
	creat_time TIMESTAMP,
	refund_sum  INT(8),
	end_time TIMESTAMP,
	status varchar(1),	
    primary key (refund_id)  
);
create table eml_inventory (
    inventory_id varchar(16), 
    sku_id varchar(100), --由产品id+规格1id+规格2id--
	inventory INT(8),--库存数量--
	goods_id varchar(16),--产品id--
	goods_price DOUBLE(8),--价格--
	ext_json varchar(200),---冗余json用来存放规格-
	c_name varchar(200),
    primary key (inventory_id)  
   
);
drop table if exists eml_store;


create table eml_store (
  store_id          varchar(16) ,
  store_name        varchar(40),
  description       varchar(100),
  logo              varchar(100),
  business_type     char(1),
  address           varchar(100),
  store_description varchar(200),
  supply            char(1),
  isentity          char(1),
  isfactory         char(1),
  sort              int,
  star              char(1),
  creat_time        timestamp,
  USER_ID           varchar(16),
  status            char(1),
  cms_site_id       varchar(16) ,
  primary key (store_id)
);


