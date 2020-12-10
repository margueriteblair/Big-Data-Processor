DROP TABLE transactions IF EXISTS;
CREATE TABLE "transactions"(
step int,
"type" varchar(50),
amount float,
nameOrig varchar(100),
oldBalanceOrg float,
newBalanceOrig float,
nameDest varchar(100),
oldBalanceDest float,
newBalanceDest float,
isFraud boolean,
isFlaggedFraud boolean
)