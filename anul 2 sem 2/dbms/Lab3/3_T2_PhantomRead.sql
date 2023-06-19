USE CarBusiness	
GO

SET TRAN ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
--inserted value does not exist yet
SELECT * FROM Sites
WAITFOR DELAY '00:00:10'
--we can see the inserted value during the second read
SELECT * FROM Sites
COMMIT TRAN