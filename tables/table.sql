CREATE TABLE APP.CUSTOMER_COUPON(
CUSTOMER_ID INT,
COUPON_ID INT
)

select * from APP.CUSTOMER
select * from app.company
SELECT * FROM APP.COUPON
SELECT * FROM APP.CUSTOMER_COUPON

SELECT CPN.* FROM APP.COUPON CPN
JOIN APP.CUSTOMER_COUPON CC
ON CPN.ID = CC.COUPON_ID WHERE CC.CUSTOMER_ID = 2

delete  from APP.CUSTOMER_COUPON