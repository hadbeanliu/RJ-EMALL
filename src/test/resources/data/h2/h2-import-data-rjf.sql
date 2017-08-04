delete from EML_PAY_SERVICE_PROVIDER;
delete from EML_ORDER;
delete from EML_ORDER_GOODS;
delete from EML_PAYMENT;

insert into EML_PAYMENT (id, PAY_SERVICE_PROVIDER_CODE, PAY_SERVICE_PROVIDER_TITLE, PAY_SERVICE_PROVIDER_DESC, STORE_ID, CONFIG, CREATE_USER, CREATE_TIME)
  values('00000001', 'alipay_create_partner_trade_by_buyer', '支付宝[纯担保交易]', '淘宝买家最熟悉的付款方式：买家先将交易资金存入支付宝并通知卖家发货，买家确认收货后资金自动进入卖家支付宝账户，完成交易。', '00000002', '{"partner":"2088102001908537","sellerEmail":"ruanjf123@163.com","key":"xgconjidehs87kvf0edxp25cxequnwes"}', '001287', CURRENT_TIMESTAMP());

insert into EML_PAY_SERVICE_PROVIDER (id, title) values('00000001', '支付宝');
insert into EML_PAY_SERVICE_PROVIDER (id, title) values('00000002', '微信支付');


insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000001', 'RD00002015051300001', 1000510, 'PA938167653505251', '00000007', '00001739', CURRENT_TIMESTAMP());

insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000002', 'RD00002015051300002', 20510, 'PA938167653505252', '00000007', '00001739', PARSEDATETIME('2015-04-04 08:52:05', 'yyyy-MM-dd HH:mm:ss'));

insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000003', 'RD00002015051300003', 16530, 'PA938167653505253', '00000007', '00001739', PARSEDATETIME('2015-05-04 12:12:55', 'yyyy-MM-dd HH:mm:ss'));

insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000004', 'RD00002015051300004', 110540, 'PA938167653505254', '00000007', '00010157', PARSEDATETIME('2015-06-04 18:42:25', 'yyyy-MM-dd HH:mm:ss'));

insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000005', 'RD00002015051300005', 4550, 'PA938167653505255', '00000007', '00010157', PARSEDATETIME('2015-07-15 16:32:15', 'yyyy-MM-dd HH:mm:ss'));

insert into EML_ORDER (id, order_no, price, pay_code, store_id, create_user, create_time)
  values('00000006', 'RD00002015051300006', 10560, 'PA938167653505256', '00000005', '00010157', PARSEDATETIME('2015-07-08 19:42:35', 'yyyy-MM-dd HH:mm:ss'));



insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000001', '00000001', '2015070716000004', 'aaaa香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '15', PARSEDATETIME('2015-04-04 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000002', '00000001', '2015070716000004', 'bbbb香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '6', PARSEDATETIME('2015-03-04 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000003', '00000001', '2015070716000004', 'cccc香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 3000, '18', PARSEDATETIME('2015-07-04 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000004', '00000001', '2015070716000004', 'dddd香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 3000, '15', PARSEDATETIME('2015-08-04 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000005', '00000001', '2015070716000004', 'eeee香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 50.00, 2470, '77', PARSEDATETIME('2015-09-04 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000006', '00000001', '2015070716000004', 'eeee香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '88', PARSEDATETIME('2015-04-05 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000007', '00000001', '2015070716000004', 'eeee香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '5', PARSEDATETIME('2015-04-16 18:32:45', 'yyyy-MM-dd HH:mm:ss'), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000008', '00000002', '2015070716000004', 'eeee香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '5', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000009', '00000002', '2015070716000004', 'ffff香港进口 屈臣氏牙线 牙线棒 圆线 牙签 6盒300支 清洁牙缝 包邮', 5000, 2470, '5', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000010', '00000002', '2015070716000004', 'vvvvvvvvvvvvvvv', 5000, 2470, '3', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');

insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000011', '00000003', '2015070716000004', 'dddddddddddddddddddddddddddddd', 5000, 2470, '25', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000012', '00000004', '2015070716000004', 'ggggggggggggggggggggggggggggggggggggg', 5000, 2470, '65', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000013', '00000005', '2015070716000004', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', 5000, 2470, '8', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  
insert into EML_ORDER_GOODS (id, order_id, goods_id, goods_title, goods_price, purchase_price, purchase_quantity, create_time, sku_id)
  values('00000014', '00000006', '2015070716000004', 'pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp', 5000, 2470, '5', CURRENT_TIMESTAMP(), '2015070716000004_1400000055_1500000149_1500000165_1500000146');
  