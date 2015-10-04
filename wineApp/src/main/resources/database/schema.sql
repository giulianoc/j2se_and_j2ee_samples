
DROP TABLE CUSTOMER_ORDER;
DROP TABLE CUSTOMER;
DROP TABLE ADDRESS;

CREATE TABLE ADDRESS (
ID INTEGER PRIMARY KEY,
VERSION INTEGER,
STREET1 VARCHAR(4000),
STREET2 VARCHAR(4000),
CITY VARCHAR(4000),
STATE CHAR(2),
ZIP_CODE INTEGER);

CREATE TABLE CUSTOMER (
ID INTEGER PRIMARY KEY,
VERSION INTEGER,
SHIPPING_ADDRESS INTEGER REFERENCES ADDRESS(ID),
EMAIL VARCHAR(4000),
BILLING_ADDRESS INTEGER REFERENCES ADDRESS(ID));

CREATE TABLE CUSTOMER_ORDER (
ID INTEGER PRIMARY KEY,
VERSION INTEGER,
CUSTOMER_ID INTEGER NOT NULL REFERENCES CUSTOMER(ID),
CREATION_DATE DATE,
STATUS VARCHAR(30));