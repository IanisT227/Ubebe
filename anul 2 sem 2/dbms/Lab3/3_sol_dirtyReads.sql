USE CarBusiness	
GO

/*SOLUTION*/
set transaction isolation level read committed
begin tran
Select * from Sites
waitfor delay '00:00:10'
Select * from Sites
rollback tran