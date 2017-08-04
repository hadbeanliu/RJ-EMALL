delete from eml_user;
delete from eml_company;
delete from eml_address;
delete from eml_message_model;
delete from eml_message_personmodel;
delete from eml_social;


insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000001', 'a', '1','管理员','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','0','0','管理员');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000002', 'b', '1','卖家1','0','1990-03-14','18259194500','11@qq.com','1','2015-05-10','1','0','卖家1');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000003', 'c', '1','卖家2','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','1','0','卖家2');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000004', 'd', '1','卖家3','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','1','0','卖家3');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000005', 'e', '1','卖家4','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','1','0','卖家4');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000006', 'f', '1','卖家5','0','1990-03-14','18259194500','11@qq.com','1','2015-05-10','1','0','卖家5');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000007', 'g', '1','买家1','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0','买家1');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000008', 'h', '1','买家2','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0','买家2');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000009', 'i', '1','买家3','0','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0','买家3');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00000010', 'j', '1','买家4','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0','买家4');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del) values('00010157', 'lxy', '889364200','买家4','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del,real_name) values('00010167', 'zzz', 'zzz','我是买家','1','1990-03-14','18259194500','11@qq.com','1','2015-05-10','2','0','我是买家');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del) values('00001739', 'rjf', '889364200','卖家rjf','0','1990-03-14','18259194500','runjf@qq.com','1','2015-05-10','2','0');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,interest_list,register_time,user_type,is_del) values('00002299', '001717', '123456','卖家rjf','0','1990-03-14','18259194500','546120342@qq.com','1','0,1,2','2015-05-10','2','0');
insert into eml_user (user_id, login_name, password,nick_name,sex,birthday,mobiletel,email,is_activeemail,register_time,user_type,is_del) values('00002307', '001725', '123456','卖家rjf','0','1990-03-14','18259194500','546120342@qq.com','1','2015-05-10','2','0');

insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000001', '00000001', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','a','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000002', '00000001', '福建省福州市鼓楼区','福州市鼓楼区铜盘路10号','a','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000003', '00000001', '福建省福州市鼓楼区', '福建省鼓楼区铜盘路10号','a','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000004', '00000001', '福建省福州市鼓楼区','福建省鼓楼区软件园B区','a','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000005', '00000001', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','a','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000006', '00000002', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','b','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000007', '00000002', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','b','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000008', '00000002', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','b','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000009', '00000003', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','c','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000010', '00000003', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','c','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000011', '00000003', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','c','18259194305','350003','0591-1234567',null,'0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000012', '00000004', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','d','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000013', '00000005', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','e','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000014', '00000006', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','f','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000015', '00000007', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','g','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000016', '00000008', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','h','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000017', '00000009', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','i','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,is_default,is_del) values('00000018', '00000010', '福建省福州市鼓楼区','福建省福州市鼓楼区铜盘路10号','j','18259194305','350003','0591-1234567',null,'1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000019', '00002299', '福建省福州市鼓楼区','铜盘路10号','lxy','18259194308','350003','0591-1234567',null,'0','1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000020', '00002299', '福建省宁德市蕉城区','铜盘路10号','zfh','18259194305','350003','0591-1234567',null,'0','0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000021', '00002299', '福建省泉州市洛江区','铜盘路10号','sl','18259194309','350003','0591-1234567',null,'0','0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000022', '00002299', '福建省福州市台江区','铜盘路10号','zzz','18259194308','350003','0591-1234567',null,'1','1','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000023', '00002299', '福建省宁德市市辖区','铜盘路10号','zxxx','18259194305','350003','0591-1234567',null,'1','0','0');
insert into eml_address (address_id, user_id, areas, address,receiver,mobiletel,zipcode,phone,remark,address_type,is_default,is_del) values('00000024', '00002299', '福建省泉州市洛江区','铜盘路10号','sl','18259194309','350003','0591-1234567',null,'1','0','0');

insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000001', '0', '用户修改密码的通知', '尊敬的{loginName}:您好,您刚才 申请了重置密码，请点击下面的链接进行重置。',CURRENT_TIMESTAMP(),'00000001','0','0','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000002', 'sellerNewOrder', '新订单通知', '尊敬的{order.sellerName}:您好,您有一个新的订单需要处理，订单号{order.orderNo}。',CURRENT_TIMESTAMP(),'00000001','0','1','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000003', 'sellerOrderCancel', '买家取消订单后的通知', '尊敬的{order.sellerName}:买家{order.buyerName}已经取消了与您交易的订单{order.orderNo}。',CURRENT_TIMESTAMP(),'00000001','0','1','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000004', 'sellerOrderPaid', '买家在线支付成功后的通知', '尊敬的{order.sellerName}:买家{order.buyerName}已通过线上支付完成了订单{order.orderNo}的付款，请核实。',CURRENT_TIMESTAMP(),'00000001','0','1','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000005', 'sellerOrderConfirm', '买家已确认收货的通知', '尊敬的{order.sellerName}:买家{order.buyerName}已对订单号{order.orderNo}进行了确认收货的操作，请核实。',CURRENT_TIMESTAMP(),'00000001','0','1','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000006', 'buyerOrderCancel', '卖家取消订单后的通知', '尊敬的{order.buyerName}:您好,与您交易的店铺{order.storeName}已经取消了您的订单{order.orderNo}。',CURRENT_TIMESTAMP(),'00000001','0','2','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000007', 'buyerNewOrder', '新订单通知', '尊敬的{order.buyerName}:您好,您有一个新的订单需要处理，订单号{order.orderNo}。',CURRENT_TIMESTAMP(),'00000001','0','2','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000008', 'buyerOrderSend', '卖家发货后后的通知', '尊敬的{order.buyerName}:您好,与您交易的店铺{order.storeName}已经给您的订单{order.orderNo}发货了，请注意查收。',CURRENT_TIMESTAMP(),'00000001','0','2','0');
insert into eml_message_model (model_id,model_sign,title,content,create_time,create_user,model_type,reciver_type,is_del) values('00000009', 'buyerOrderModPrice', '卖家调整订单费用后的通知', '尊敬的{order.buyerName}:您好,与您交易的店铺{order.storeName}调整了您订单号为{order.orderNo}的订单的费用，请您及时付款',CURRENT_TIMESTAMP(),'00000001','0','2','0');

insert into eml_message_personmodel (personmodel_id,model_id,user_id,status) values('00000001','00000006','00002299','0');
insert into eml_message_personmodel (personmodel_id,model_id,user_id,status) values('00000002','00000007','00002299','0');
insert into eml_message_personmodel (personmodel_id,model_id,user_id,status) values('00000003','00000008','00002299','1');
insert into eml_message_personmodel (personmodel_id,model_id,user_id,status) values('00000004','00000009','00002299','1');


--insert into eml_company(company_id,user_id,company_name,licence_id,legal_person,licence_address,create_time,company_phone,company_contacts,company_contacts_phone,authentication_service,status)
-- values('00000001','00000002','测试企业1','abcd123456789','张三','福建省福州市鼓楼区',CURRENT_TIMESTAMP(),'400-xxx-xxxx','李四','15905910001','担保支付/7天包换/即刻到账/其他认证服务','0');
 
insert into eml_social(social_user_id,user_id) values('46AE353D1744C86F0F5607AD831A3129','00010157');