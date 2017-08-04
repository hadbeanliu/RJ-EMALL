drop table if exists eml_user;
drop table if exists eml_company;
drop table if exists eml_address;
drop table if exists eml_message_model;
drop table if exists eml_message_personmodel;
drop table if exists eml_social;

create table eml_user (
    user_id varchar(16),
	login_name varchar(40) not null,
	password varchar(20),
	nick_name varchar(255),
	sex varchar(1) ,
    birthday TIMESTAMP ,
    mobiletel varchar(11),
    expert_mobile varchar(16),
    email varchar(255),
    is_activeemail varchar(1) ,
    register_time TIMESTAMP ,
    user_type varchar(1),
    is_personauthentication varchar(1),
    is_expertauthentication varchar(1),
    qq varchar(100),
    field varchar(40),
    rank varchar(40),
    work varchar(255),
    user_credentials varchar(255),
    is_del varchar(1),
    real_name varchar(40),
    head varchar(255),
	front_card varchar(255),
	back_card varchar(255),
	card_id varchar(18),
	interest_list varchar(100),
    primary key (user_id)   
);

create table eml_company (
    company_id varchar(16),
    user_id varchar(16) not null,
	company_name varchar(100) not null,
	company_property varchar(1),
	company_create_time varchar(20),
    company_register_address varchar(255) ,
    company_contact_address varchar(255) ,
    company_zipcode varchar(6) ,
    company_register_fund varchar(50),
	licence_id varchar(15),
	legal_person varchar(40),
	legal_phone varchar(18) ,
    licence_frontcard varchar(255) ,
    licence_backcard varchar(255) ,
    licence_field varchar(5000), 
    licence_start_period varchar(20),
    licence_end_period varchar(20),
    is_companyauthentication varchar(1),
    is_serviceauthentication varchar(1),
    authentication_service varchar(1000),
    status varchar(1),
    informance varchar(1),
    primary key (company_id)   
);

create table eml_address (
    address_id varchar(16),
	user_id varchar(16) not null,
	areas varchar(100),
	address varchar(200) not null,
	receiver varchar(20),
	mobiletel varchar(11),
    zipcode varchar(8),
    district varchar(8),
    phone varchar(18),
    remark varchar(255),
    address_type varchar(1),
    is_default varchar(1),
    is_del varchar(1), 
    primary key (address_id)  
);

create table eml_message_model (
    model_id varchar(16),
    model_sign varchar(20) not null,
    title varchar(200) not null,
    content varchar(4000) not null,
    create_time TIMESTAMP,
    modify_time TIMESTAMP,
    create_user varchar(16) not null,
    model_type varchar(1) not null,
    reciver_type varchar(1),
    is_del varchar(1),
    primary key (model_id)  
);

create table eml_message_personmodel (
    personmodel_id varchar(16),
    model_id varchar(16) not null,
    user_id varchar(16) not null,
    status varchar(1),
    primary key (personmodel_id)  
);

create table eml_social (
    social_user_id varchar(100),
 	user_id varchar(16),
	primary key (social_user_id)
);
