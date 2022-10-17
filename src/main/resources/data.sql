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
