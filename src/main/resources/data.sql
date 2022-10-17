
DROP TABLE IF EXISTS fix_discount_policy;
DROP TABLE IF EXISTS rate_discount_policy;

CREATE TABLE `fix_discount_policy`
(
    `fix_discount_policy_id` bigint       NOT NULL,
    `created_date`           datetime     NOT NULL,
    `last_modified_date`     datetime     NOT NULL,
    `end_date`               datetime     NOT NULL,
    `min_price`              int          NOT NULL,
    `name`                   varchar(255) NOT NULL,
    `price`                  int          NOT NULL,
    `product_id`             bigint       NOT NULL,
    `reg_date`               datetime     NOT NULL,
    `start_date`             datetime     NOT NULL,
    `seller_id`              bigint     NOT NULL
);

CREATE TABLE `rate_discount_policy`
(
    `rate_discount_policy_id` bigint       NOT NULL,
    `created_date`            datetime     NOT NULL,
    `last_modified_date`      datetime     NOT NULL,
    `end_date`                datetime     NOT NULL,
    `min_price`               int          NOT NULL,
    `name`                    varchar(255) NOT NULL,
    `product_id`              bigint       NOT NULL,
    `rate`                    int          NOT NULL,
    `reg_date`                datetime     NOT NULL,
    `start_date`              datetime     NOT NULL,
    `seller_id`               bigint     NOT NULL
);

INSERT INTO fix_discount_policy(fix_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, price, product_id, reg_date, start_date, seller_id) VALUES(1, NOW(), NOW(), '2022-10-30 00:00:00', 1000, '고정할인정책1', 3000, 1, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);
INSERT INTO fix_discount_policy(fix_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, price, product_id, reg_date, start_date, seller_id)VALUES(2, NOW(), NOW(), '2022-10-30 00:00:00', 3000, '고정할인정책2', 3000, 2, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);
INSERT INTO fix_discount_policy(fix_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, price, product_id, reg_date, start_date, seller_id) VALUES(3, NOW(), NOW(), '2022-10-30 00:00:00', 5000, '고정할인정책3', 3000, 3, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);
INSERT INTO fix_discount_policy(fix_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, price, product_id, reg_date, start_date, seller_id) VALUES(4, NOW(), NOW(), '2022-10-30 00:00:00', 4000, '고정할인정책4', 3000, 4, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 2);
INSERT INTO rate_discount_policy(rate_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, product_id, rate, reg_date, start_date, seller_id) VALUES(1, NOW(), NOW(), '2022-10-17 00:00:00', 1000, '비율할인정책1', 1, 10, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);
INSERT INTO rate_discount_policy(rate_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, product_id, rate, reg_date, start_date, seller_id) VALUES(2, NOW(), NOW(), '2022-10-17 00:00:00', 2000, '비율할인정책2', 2, 5, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);
INSERT INTO rate_discount_policy(rate_discount_policy_id, created_date, last_modified_date, end_date, min_price, name, product_id, rate, reg_date, start_date, seller_id) VALUES(3, NOW(), NOW(), '2022-10-17 00:00:00', 3000, '비율할인정책1', 3, 3, '2022-10-17 00:00:00', '2022-10-17 00:00:00', 1);

insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "10월 닭가슴살 페스티벌", "1964-06-07 16:21:24", 5, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "닭가슴살의 왕 ", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "허닭 브랜드 위크", "1964-06-07 16:21:24", 9, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);
insert into rate_coupon (name,regdate, rate, min_price, start_date, end_date, product_id,seller_id) values ( "BE SPOKE 큐커 할인", "1964-06-07 16:21:24", 20, 10000, "2022-06-10 16:21:24", "2023-06-29 16:21:24",3,1);

insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",1,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",2,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",3,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",4,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",5,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",6,1,true);
insert into fix_coupon ( name, regdate, price, min_price, start_date, end_date, product_id,seller_id,free_delivery) values ( "무료배송", "1964-06-10 16:21:24", 3000, 10000, "2022-06-09 16:21:24", "2023-09-28 16:21:24",7,1,true);

insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,1,null);insert into rate_discount_policy(rate_discount_policy_id,end_date, min_price, name, product_id, rate, reg_date, start_date) values(1,"2023-10-10 00:00:00", 10000,"rateDiscpintPolicy",1,19,"2022-06-10 16:21:24","2022-06-10 16:21:24");

insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,2,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,3,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,4,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,5,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,6,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,1,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,2,null);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,null,1);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,null,2);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,null,3);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,null,4);
insert into user_coupon(usage_status, user_id,fix_coupon_id, rate_coupon_id) values(0,1,null,5);