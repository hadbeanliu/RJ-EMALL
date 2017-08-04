drop table if exists eml_examine;

create table eml_examine (
	id varchar(16),
    user_id varchar(16),
    company_id varchar(16),
    status varchar(1),
    results varchar(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    authentications varchar(1),
    error_field varchar(255),
    description varchar(100),
    errorReason varchar(255),
    primary key (id)   
);

