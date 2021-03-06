drop table if exists EML_CROWDFUNDING_RETURN;
drop table if exists EML_CROWDFUNDING_SPONSOR;
drop table if exists EML_CROWDFUNDING;
drop table if exists EML_MY_CROWDFUNDING;

drop table if exists EML_MY_CART;
drop table if exists EML_MY_BROWSE;
drop table if exists EML_MY_ATTENTION;

drop table if exists EML_MY_BOND;
drop table if exists EML_AUCTION_RECORD;
drop table if exists EML_MY_GROUP_PURCHASE;

create table EML_CROWDFUNDING_RETURN
(
	CROWDFUNDING_RETURN_ID VARCHAR2(16) not null,
	CROWDFUNDING_ID VARCHAR2(16) not null,
	MONEY FLOAT not null,
	TITLE VARCHAR2(60) not null,
	RETURN_DETAILS VARCHAR2(400) not null,
	RETURN_NUMBER NUMBER,
	INDIVIDUAL_PURCHASE NUMBER,
	RETURN_PICTURE VARCHAR2(250),
	LOGISTICS_WAY CHAR(1),
	RELEASE_DAYS NUMBER,
	primary key (CROWDFUNDING_RETURN_ID)
);


create table EML_CROWDFUNDING_SPONSOR 
(
	CROWDFUNDING_SPONSOR_ID VARCHAR2(16) not null,
	SPONSOR_NAME VARCHAR2(20) not null,
	SPONSOR_SUMMARY VARCHAR2(200) not null,
	SPONSOR_HEAD VARCHAR2(250) not null,
	OTHER_INFORMATION VARCHAR2(250) not null,
	CONTACT_NAME VARCHAR2(20) not null,
	EMAIL VARCHAR2(30) not null,
	PHONE VARCHAR2(11) not null,
	primary key (CROWDFUNDING_SPONSOR_ID)
);


create table EML_CROWDFUNDING 
(
	CROWDFUNDING_ID VARCHAR2(16) not null,
	STORE_ID VARCHAR2(16),
	USER_ID VARCHAR2(16),
	SPONSOR_INFORMATION_ID VARCHAR2(16),
	POST_CATEGORIES CHAR(1),
	PROJECT_NAME VARCHAR(60) not null,
	TARGET_AMOUNT FLOAT not null,
	PROJECT_PICTURE VARCHAR(250),
	DEPOSIT_RATIO_OF NUMBER,
	CREATE_TIME TIMESTAMP,
	START_TIME TIMESTAMP not null,
	END_TIME TIMESTAMP not null,
	PROJECT_OUTLINE VARCHAR2(200),
	PROJECT_DESCRIPTION VARCHAR2(400) not null,
	RAISING_DAYS NUMBER,
	CURRENT_TOTAL_FUNDING FLOAT,
	SUPPORT_NUMBER NUMBER,
	ACHIEVEMENT_RATE FLOAT,
	CROWDFUNDING_STATUS CHAR(1) not null,
	primary key (CROWDFUNDING_ID)
);


create table EML_MY_CROWDFUNDING 
(
	MY_CROWDFUNDING_ID VARCHAR2(16) not null,
	CROWDFUNDING_ID VARCHAR2(16),
	CROWDFUNDING_RETURN_ID VARCHAR2(16),
	USER_ID VARCHAR2(16),
	SUPPORT_TIME TIMESTAMP,
	primary key (MY_CROWDFUNDING_ID)
);

create table EML_MY_ATTENTION 
(
	ATTENTION_ID VARCHAR2(16) not null,
	ATTENTION_TYPE VARCHAR2(1) not null,
	USER_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16),
	STORE_ID VARCHAR2(16),
	CREATE_TIME TIMESTAMP,
	CURR_LAST VARCHAR(1),
    primary key (ATTENTION_ID)
);

create table EML_MY_BROWSE 
(
	BROWSE_ID VARCHAR2(16) not null,
	USER_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16) not null,
	GOODS_CATEGORY VARCHAR(30) not null,
	CREATE_TIME TIMESTAMP,
	PER_NUM NUMBER,
	primary key (BROWSE_ID)
);

create table EML_MY_CART 
(
	CART_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16) not null,
    STORE_ID VARCHAR2(16) not null,
	USER_ID VARCHAR2(16) not null,
	BUY_NUMBER NUMBER not null,
	SKU_ID VARCHAR2(120) not null,
	CREATE_TIME TIMESTAMP,
	primary key (CART_ID)
);

create table EML_MY_BOND 
(
	BOND_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16) not null,	
	USER_ID VARCHAR2(16) not null,
	BOND_MONERY FLOAT not null,	
	SUBMIT_TIME TIMESTAMP,
    primary key (BOND_ID)
);

create table EML_AUCTION_RECORD 
(
	AUCTION_RECORD_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16) not null,	
	USER_ID VARCHAR2(16) not null,	
	AUCTION_TIME TIMESTAMP,
 	primary key (AUCTION_RECORD_ID)
);

create table EML_MY_GROUP_PURCHASE 
(
	GROUP_PURCHASE_ID VARCHAR2(16) not null,
	GOODS_ID VARCHAR2(16) not null,
	GOODS_TITLE VARCHAR2(60),
	USER_ID VARCHAR2(16) not null,
	PURCHASE_GOODS_NUMBER NUMBER,
	PURCHASE_GOODS_MONERY FLOAT,
	PURCHASE_GOODS_STATE CHAR(1),
	JOIN_TIME TIMESTAMP,
	primary key (GROUP_PURCHASE_ID)
);