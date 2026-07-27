INSERT INTO notification_logs
(user_id, type, recipient, payload, sent_at)
VALUES

(1,
'EMAIL',
'john.smith@gmail.com',
'Your order #1001 has been confirmed successfully.',
NOW()),

(2,
'SMS',
'+919876543210',
'Your OTP for login is 845621.',
NOW()),

(3,
'PUSH',
'device-token-1001',
'Flash Sale! Get 30% off on Electronics.',
NOW()),

(4,
'EMAIL',
'alice.williams@gmail.com',
'Your payment of ₹2,499.00 was received successfully.',
NOW()),

(5,
'SMS',
'+919812345678',
'Order #1005 has been shipped.',
NOW()),

(6,
'EMAIL',
'robert.brown@gmail.com',
'Welcome to E-Shop. Your account has been created.',
NOW()),

(7,
'PUSH',
'device-token-1002',
'Your order is out for delivery.',
NOW()),

(8,
'EMAIL',
'emma.johnson@gmail.com',
'Password changed successfully.',
NOW()),

(9,
'SMS',
'+919900112233',
'Your refund has been initiated.',
NOW()),

(10,
'EMAIL',
'david.miller@gmail.com',
'Order #1010 has been delivered.',
NOW());