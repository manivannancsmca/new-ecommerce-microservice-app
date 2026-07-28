INSERT INTO purchase_orders
(user_id, product_id, total_amount, status, payment_id, created_at)
VALUES

(1, 1, 1299.99, 'APPROVED',  'PAY-100001', NOW()),

(2, 2, 499.50,  'PENDING',   NULL, NOW()),

(3, 3, 2499.00, 'APPROVED',  'PAY-100002', NOW()),

(4, 4, 799.99,  'CANCELLED', 'PAY-100003', NOW()),

(5, 5, 159.99,  'APPROVED',  'PAY-100004', NOW()),

(6, 6, 3499.90, 'PENDING',   NULL, NOW()),

(7, 7, 99.99,   'APPROVED',  'PAY-100005', NOW()),

(8, 8, 650.75,  'APPROVED',  'PAY-100006', NOW()),

(9, 9, 999.00,  'CANCELLED', 'PAY-100007', NOW()),

(10, 10, 1899.49,'PENDING',   NULL, NOW());