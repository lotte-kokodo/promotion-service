insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",1,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "10월 무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",2,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "타임 무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",3,1,true);

insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",2,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",970,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",5,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",6,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "닭가슴살의 왕 ", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",970,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "닭가슴살의 왕 ", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",4,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "닭가슴살의 왕 ", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",717,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "닭가슴살의 왕 ", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",10,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "허닭 브랜드 위크", "1964-06-07 16:21:24", 9, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",1,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "허닭 브랜드 위크", "1964-06-07 16:21:24", 9, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",1,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "BE SPOKE 큐커 할인", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",1,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "BE SPOKE 큐커 할인", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "단백질 타임세일", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",967,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "단백질 타임세일", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);


insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,1);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,2);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,3);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,4);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,5);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,6);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,7);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,8);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,9);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,10);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,11);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,12);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,13);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,14);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,null,15);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values("NOT_USED",1,1,null);