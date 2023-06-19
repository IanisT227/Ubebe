USE CarBusiness
GO

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
	EXEC uspAddSiteRecover 101, 'Mobile.De';
	EXEC uspAddCarRecover 99,'LOGAN GOLAN', 42,88; --this will fail, but the item added before will still be in the database
	EXEC uspAddAdRecover 42,101,101;
GO

CREATE OR ALTER PROCEDURE uspGoodAddScenario
AS
	EXEC uspAddSiteRecover 1111, 'Mobile.De';
	EXEC uspAddCarRecover 1,'LOGAN GOLAN', 2222,2; --
	EXEC uspAddAdRecover 2222,1111,1111;
GO

EXEC uspBadAddScenario
SELECT * FROM Sites
SELECT * FROM Cars
SELECT * FROM Ads

EXEC uspGoodAddScenario
