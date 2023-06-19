CREATE DATABASE CarBusiness;
---AUTOPARK

CREATE TABLE AutoPark(
ParkId INT PRIMARY KEY,
ParkAddress VARCHAR(50),
ParkName VARCHAR(50),);

INSERT INTO AutoPark(ParkId, ParkAddress, ParkName) VALUES (1,'Aici', 'La Cristi');
INSERT INTO AutoPark(ParkId, ParkAddress, ParkName) VALUES (2,'Acolo', 'La Marcel');
INSERT INTO AutoPark(ParkId, ParkAddress, ParkName) VALUES (3,'Dincolo', 'La Ionel');


---CARBRANDS

CREATE TABLE CarBrands(
BrandId INT PRIMARY KEY,
BrandName VARCHAR(50),
Country VARCHAR(25),);


INSERT INTO CarBrands(BrandId, BrandName, Country) VALUES (1,'Toyota', 'Japan');
INSERT INTO CarBrands(BrandId, BrandName, Country) VALUES (2,'BMW', 'Germany');
INSERT INTO CarBrands(BrandId, BrandName, Country) VALUES(3, 'OPEL', 'Germany');
INSERT INTO CarBrands(BrandId, BrandName, Country) VALUES(4, 'Chevrolet', 'USA');


--- CARS

CREATE TABLE Cars(
CarId INT PRIMARY KEY,
BId INT FOREIGN KEY REFERENCES CarBrands(BrandId),
ImportId INT FOREIGN KEY REFERENCES AutoPark(ParkId),
Model VARCHAR(50),
);

INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (1,1,1,'Supra');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (2,1,2,'Celica');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (3,2,1,'Seria 6');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (4,2,2,'E31');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (5,3,1,'Zafira');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (6,3,2,'Insignia');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (7,4,1,'Camaro');
INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (8,4,2,'Malibu');



---CITY

CREATE TABLE City(
CityId INT PRIMARY KEY,
CityName VARCHAR(50),
isShop VARCHAR(3),
);

INSERT INTO City(CityId, CityName, isShop) VALUES( 1, 'Brasov', 'YES');
INSERT INTO City(CityId, CityName, isShop) VALUES( 2, 'Cluj-Napoca', 'NO');
INSERT INTO City(CityId, CityName, isShop) VALUES( 3, 'Bucuresti', 'YES');

--- CUSTOMER

CREATE TABLE Customer(
CustomerId INT PRIMARY KEY,
CustomerName VARCHAR(40),
CityId INT FOREIGN KEY REFERENCES City(CityId),
);

INSERT INTO Customer(CustomerId, CustomerName, CityId) values (1, 'Marius', 1);
INSERT INTO Customer(CustomerId, CustomerName, CityId) values (2, 'Giani', 1);
INSERT INTO Customer(CustomerId, CustomerName, CityId) values (3, 'Elvis', 2);

---SITES

CREATE TABLE Sites(
SiteId INT PRIMARY KEY,
SiteName VARCHAR(30),
);


INSERT INTO Sites(SiteId, SiteName) VALUES(1,'Autovit');
INSERT INTO Sites(SiteId, SiteName) VALUES(2,'Facebook');

---ADS

CREATE TABLE Ads(
CarId INT FOREIGN KEY REFERENCES Cars(CarId),
SiteId INT FOREIGN KEY REFERENCES Sites(SiteId),
AdId INT PRIMARY KEY);

INSERT INTO Ads(CarId, SiteId, AdId) VALUES(1,1,1);
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(1,2,2);
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(2,1,3);
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(2,2,4);
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(3,2,5);
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(4,1,6);


---TRANSACTIONS

CREATE TABLE Transactions(
TransactionId INT  PRIMARY KEY,
CarId INT FOREIGN KEY REFERENCES Cars(CarId),
CustomerId INT FOREIGN KEY REFERENCES Customer(CustomerId),
Price INT,
);





INSERT INTO Transactions(TransactionId, CarId, CustomerId, Price) VALUES(1,1,1,25999);
INSERT INTO Transactions(TransactionId, CarId, CustomerId, Price) VALUES(2,2,2,9998);
INSERT INTO Transactions(TransactionId, CarId, CustomerId, Price) VALUES(3,3,1,13999);





---REPAIRSHOP

CREATE TABLE RepairShop(
ShopId INT PRIMARY KEY,
ShopName VARCHAR(50),
Rating INT,);

INSERT INTO RepairShop(ShopId, ShopName, Rating) VALUES(1, 'La Cipri', 5);
INSERT INTO RepairShop(ShopId, ShopName, Rating) VALUES(2, 'Automobile Bavaria', 4);
INSERT INTO RepairShop(ShopId, ShopName, Rating) VALUES(3, 'Custom Tuning', 5);




--OPERATIONS


CREATE TABLE Operations(
OperationId INT PRIMARY KEY,
ShopId INT FOREIGN KEY REFERENCES RepairShop(ShopId),
CarId INT FOREIGN KEY REFERENCES Cars(CarId),
OperationName VARCHAR(50),
Cost INT,
);

INSERT INTO Operations(OperationId, ShopId, CarId, OperationName, Cost) VALUES(1,1,1,'Indreptare aripa',600);
INSERT INTO Operations(OperationId, ShopId, CarId, OperationName, Cost) VALUES(2,2,1,'Reconditionare interior',900);
INSERT INTO Operations(OperationId, ShopId, CarId, OperationName, Cost) VALUES(3,3,2,'Revopsire',1600);


SELECT * FROM Cars;
SELECT * FROM CarBrands;
SELECT * FROM AutoPark;
SELECT * FROM City;
SELECT * FROM Customer;
SELECT * FROM Sites;
SELECT * FROM Ads;
SELECT * FROM Transactions;
SELECT * FROM RepairShop;
SELECT * FROM Operations;



---HW2

---INSERTING DATA
INSERT INTO CarBrands(BrandId, BrandName, Country) VALUES(5, 'Mazda', 'Japan');
INSERT INTO Ads(CarId, SiteId, AdId) VALUES(5,1,1);
INSERT INTO City(CityId, CityName, isShop) VALUES( 4, 'Los Angeles', 'YES');
INSERT INTO Operations(OperationId, ShopId, CarId, OperationName, Cost) VALUES(4,3,6,'Inlocuire filtru de particule',3000);

UPDATE Operations
SET ShopId=1
WHERE OperationId=4 Or OperationId=2;

UPDATE AutoPark
SET ParkName='Auto Distrib'
WHERE ParkAddress LIKE 'D%' ;

UPDATE CarBrands
SET BrandName='Chevy'
WHERE BrandId IN(
SELECT CarBrands.BrandId Where CarBrands.Country='USA' );



DELETE FROM City WHERE CityName='Craiova';
DELETE FROM Operations WHERE OperationId IS NULL; 

INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (9,5,1,'MX-5');

--- a)

SELECT CarId FROM Operations
UNION
SELECT BId FROM Cars;

SELECT BrandName FROM CarBrands
WHERE Country='Japan'
UNION
SELECT Model FROM Cars
WHERE BId=1 OR ImportId=2;


--- b)

--- only cars that are part of a transaction
SELECT CarId FROM Cars 
INTERSECT
SELECT CarId FROM Transactions;


---take only german and japanese cars from Cars
SELECT Model FROM CARS
WHERE BId IN(1,2,3);


--- c)

---take all cars that are not BMW's
SELECT Model FROM Cars
EXCEPT
SELECT Model FROM Cars WHERE BId=2;

---take all cities that are not Cluj-Napoca

SELECT CityName FROM City 
WHERE NOT CityName='Cluj-Napoca';


--- d) 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator);
---one query will join at least 3 tables, while another one will join at least two many-to-many relationships;


---joins 3 tables and shows the model of the car and price for each Ad
SELECT Cars.Model, Ads.AdId, Transactions.Price
FROM((Cars
INNER JOIN Ads ON Ads.CarId=Cars.CarId)
INNER JOIN Transactions ON Transactions.CarId=Cars.CarId);


--- shows the operations performed on each car, if there is any.
SELECT Cars.Model, Operations.OperationName
FROM Cars
RIGHT JOIN Operations ON Cars.CarId= Operations.CarId
ORDER BY Cars.Model;

--- shows the autopark that the car was imported from alongside the model name
SELECT AutoPark.ParkName, Cars.Model
FROM AutoPark
LEFT JOIN Cars ON AutoPark.ParkId= Cars.ImportId
ORDER BY Cars.Model;

--- shows all cars and their manufacturer and its country
SELECT Cars.Model, CarBrands.BrandName, CarBrands.Country
FROM Cars
FULL JOIN CarBrands ON Cars.BId= CarBrands.BrandId;


--- e)

--- shows all Toyota and Opel Cars
SELECT DISTINCT TOP 3 * FROM Cars
WHERE Model in(
Select Model FROM Cars
WHERE BId=1 or BId=3);

--- Shows Cars and their Brands, Imports and Models
SELECT DISTINCT  TOP 6 * FROM Cars
WHERE Model in(
Select Model FROM Cars
WHERE BId in(
SELECT CarBrands.BrandId FROM CarBrands));


--- f)
---f. 2 queries with the EXISTS operator and a subquery in the WHERE clause;

--- takes all cars that are part of a transaction and have a price >10000
SELECT Model From Cars
WHERE EXISTS(SELECT Transactions.Price FROM Transactions WHERE Transactions.CarId=Cars.CarId AND Price >10000);


--- takes all cars that have had any operations that had a cost >1000
SELECT Model, BId From Cars
WHERE EXISTS(SELECT Operations.Cost FROM Operations WHERE Operations.CarId=Cars.CarId AND Cost >1000);


--- g)
---2 queries with a subquery in the FROM clause; 


--- takes all RepairShops with a rating of 5 or above
Select subq.ShopName
FROM (SELECT * FROM RepairShop as r where r.Rating>4) as subq;


---takes all Cities that have a Shop in them
Select subb.CityName
FROM (SELECT * FROM City as c where c.isShop='YES') as subb;

---h)
--- 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause;
--- 2 of the latter will also have a subquery in the HAVING clause;
--- use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;

--- Counts the cars from each brand
SELECT COUNT(Cars.CarId), Cars.BId
FROM Cars
GROUP BY BId;


---counts the brands that have mroe than 1 car
SELECT COUNT(Cars.CarId), Cars.BId
FROM Cars
GROUP BY BId
HAVING COUNT(Cars.CarId)>1;

---Counts all opel cars
SELECT COUNT(Cars.CarId), Cars.BId
FROM Cars
GROUP BY BId
HAVING Cars.BId IN (
SELECT Cars.CarId FROM Cars WHERE Cars.BId=3);

--- Shows total price for customer 1 and customer 2
SELECT SUM(Transactions.Price) AS Total
FROM Transactions
GROUP BY Transactions.CustomerId
HAVING CustomerId IN (
SELECT CustomerId FROM Customer WHERE CustomerId=1 or CustomerId=2);


---i)
---i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator);
--- rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.

--- Shows cars that are from germany and japan
SELECT Model FROM
Cars
WHERE BId= ANY(
SELECT BrandId
FROM CarBrands
Where
Country='Japan' OR Country='Germany');

---rewrite
SELECT Model FROM
CARS WHERE BId IN (
SELECT CarBrands.BrandId
FROM CarBrands
WHERE
Country='Japan' OR Country='Germany');


--- shows cars that cost more than 10000
SELECT Model FROM
Cars
WHERE CarId= ANY(
SELECT CarId
FROM Transactions
Where
Price>10000);

---rewrite
SELECT Model FROM
Cars
WHERE CarId IN(
SELECT CarId
FROM Transactions
Where
Price>10000);


---shows cars that are not from Japan or Germany
SELECT Model FROM
Cars
WHERE BId!= ALL(
SELECT BrandId
FROM CarBrands
Where
Country='Japan' OR Country='Germany');

---rewrite 

SELECT Model FROM
Cars
WHERE BId NOT IN(
SELECT BrandId
FROM CarBrands
Where
Country='Japan' OR Country='Germany');



---shows care that are less than 10000 or have no price at all
SELECT Model FROM
Cars
WHERE CarId != ALL(
SELECT CarId
FROM Transactions
Where
Price>10000);


---Rewrite
SELECT Model FROM
Cars
WHERE CarId NOT IN(
SELECT CarId
FROM Transactions
Where
Price>10000);