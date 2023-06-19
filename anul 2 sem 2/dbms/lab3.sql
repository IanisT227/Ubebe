USE CarBusiness
GO

--- create a stored procedure that inserts data in tables that are in a m:n relationship;
--- if one insert fails, all the operations performed by the procedure must be rolled back (grade 3);

create function udf_validateParamsSites(@name varchar(100), @id INT) RETURNS INT AS
begin
	declare @rez INT =0
	IF EXISTS (SELECT * FROM Sites WHERE SiteId = @id)
		set @rez = 1
	return @rez
end
go

create or alter procedure usp_insertAds(@name varchar(30), @id INT) AS
begin
	begin tran
	begin try
		if (dbo.udf_validateParamsSites(@name, @id) <> 0) 
			raiserror('err', 14, 1);
		insert into Sites(SiteId, SiteName) values(@id, @name)
		commit tran
		select 'T committed'
	end try
	begin catch
		rollback tran
		select 'T rollbacked'
	end catch
end
go

usp_insertAds 'OLX',3;
Select * from Sites
usp_insertAds 'OLX',1;


---create a stored procedure that inserts data in tables that are in a m:n relationship;
---if an insert fails, try to recover as much as possible from the entire operation: for example, if the user wants to add a book and its authors,
---succeeds creating the authors, but fails with the book, the authors should remain in the database (grade 5);


CREATE OR ALTER PROCEDURE uspAddSiteRecover (@id INT, @name VARCHAR(30))
AS
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
		IF (dbo.udf_validateParamsSites(@name, @id) <> 0)
		BEGIN
			RAISERROR('Input is invalid',14,1)
		END
		INSERT INTO Sites VALUES (@id, @name)
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
	END CATCH
GO

CREATE OR ALTER PROCEDURE uspAddCarRecover (@brandId INT,@model VARCHAR(30),@Id INT, @ImportedFrom INT)
AS
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
		IF NOT EXISTS (SELECT * FROM CarBrands WHERE BrandId = @brandId)
		BEGIN
			RAISERROR('Invalid brand item',14,1)
		END
		IF EXISTS (SELECT * FROM Cars WHERE CarId = @Id)
		BEGIN
			RAISERROR('Invalid CarId item',14,1)
		END
		IF NOT EXISTS (SELECT * FROM AutoPark WHERE ParkId = @ImportedFrom)
		BEGIN
			RAISERROR('Invalid Import item',14,1)
		END
		INSERT INTO Cars(CarId,BId,ImportId,Model) VALUES (@Id, @brandId,@ImportedFrom,@Model)
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
	END CATCH
GO

CREATE OR ALTER PROCEDURE uspAddAdRecover (@CId INT, @SId INT, @AdId INT)
AS
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
		IF NOT EXISTS (SELECT * FROM Cars WHERE CarId = @Cid)
		BEGIN
			RAISERROR('CarId is invalid',14,1)
		END
		IF NOT EXISTS (SELECT * FROM Sites WHERE SiteId = @SId)
		BEGIN
			RAISERROR('SiteId item',14,1)
		END
		IF EXISTS (SELECT * FROM Ads WHERE AdId = @AdId)
		BEGIN
			RAISERROR('AdId already exists',14,1)
		END
		INSERT INTO Ads(CarId,SiteId,AdId) VALUES (@CId, @SId,@AdId)
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
	END CATCH
GO

CREATE OR ALTER PROCEDURE uspBadAddScenario
AS
	EXEC uspAddSiteRecover 4, 'Mobile.De';
	EXEC uspAddCarRecover 99,'LOGAN GOLAN', 2,88; --this will fail, but the item added before will still be in the database
	EXEC uspAddAdRecover 3,2,10;
GO

CREATE OR ALTER PROCEDURE uspGoodAddScenario
AS
	EXEC uspAddSiteRecover 4, 'Mobile.De';
	EXEC uspAddCarRecover 1,'LOGAN GOLAN', 200,2; --this will fail, but the item added before will still be in the database
	EXEC uspAddAdRecover 3,2,10;
GO

EXEC uspBadAddScenario
SELECT * FROM Sites
SELECT * FROM Cars
SELECT * FROM Ads

EXEC uspGoodAddScenario


---reproduce the following concurrency issues under pessimistic isolation levels:
---dirty reads, non-repeatable reads, phantom reads, and a deadlock (4 different scenarios);
---you can use stored procedures and / or stand-alone queries;
---find solutions to solve / workaround the concurrency issues (grade 9);


-- DIRTY READS
-- SET isolation level read uncommited
-- T1: begin T1, update, delay, rollback T1
-- T2: begin T2, select, delay, select, commit 
-- solution: read commited

BEGIN TRAN
UPDATE Sites
SET SiteName = 'Mobile.deu' 
WHERE SiteId = 3
WAITFOR DELAY '00:00:10'
ROLLBACK TRAN

set transaction isolation level read uncommitted
begin tran
update Sites set SiteName='----------' where SiteId = 2
waitfor delay '00:00:10'
rollback tran

/*SOLUTION*/
set transaction isolation level read committed
begin tran
Select * from Sites
update Sites set SiteName='---aaa--------' where SiteId = 2
waitfor delay '00:00:10'
Select * from Sites
rollback tran

-- NON-REPEATABLE READS
-- SET isolation level read commited
-- T1: insert, begin T1, delay, update, commit
-- T2: begin T2, select, delay, select, commit 
-- solution: repeatable read

BEGIN TRAN
UPDATE Sites
SET SiteName = 'Mobile.deu' 
WHERE SiteId = 2
WAITFOR DELAY '00:00:04'
ROLLBACK TRAN

set transaction isolation level read committed
begin tran
Select * from Sites
waitfor delay '00:00:10'
update Sites set SiteName='-----------' where SiteId = 2
Select * from Sites
commit

/*SOLUTION*/
set transaction isolation level repeatable read
begin tran
Select * from Sites
waitfor delay '00:00:10'
update Sites set SiteName='---aaa--------' where SiteId = 2
Select * from Sites
commit

select  * from Sites
select * from Cars

-- PHANTOM READS
-- SET isolation level repeatable read
-- T1: begin T1, delay, insert/delete, commit
-- T2: begin T2, select, delay, select, commit 
-- solution: serializable

set transaction isolation level repeatable read
begin tran
select * from Sites
waitfor delay '00:00:10'
insert into Sites values (250, 'ttttttttttt' )
Select * from Sites
commit

--- DEADLOCK
--- T1: begin, update on table A, delay (WAITFOR DELAY '00:00:04'), update on table B, commit
--- T2: begin, update on table B, delay (WAITFOR DELAY '00:00:04'), update on table A, commit
-- solution: set deadlock priority LOW, HIGH, NORMAL (0), -10.. 10

begin tran
update Sites SET SiteName = '**********' where SiteId = 3
waitfor delay '00:00:10'
update Cars SET Model = 'UpdateNEW' where CarId = 8
commit

Select * from Sites
Select * from Cars

alter database CarBusiness set ALLOW_SNAPSHOT_ISOLATION ON
alter database CarBusiness set READ_COMMITTED_SNAPSHOT ON


---reproduce the update conflict under an optimistic isolation level (grade 10).

-- T1: begin, select from Table a R, delay, select, update R set col1="conflict1", commit
-- T2: delay, begin, update R set col1 ="conflict2", delay, commit
set transaction isolation level snapshot
begin tran
/*select * from Cars where CarId = 1*/
waitfor delay '00:00:10'
update Cars set model = 'Update1' where CarId  =1
commit

