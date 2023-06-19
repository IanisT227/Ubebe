USE CarBusiness
GO

set transaction isolation level read uncommitted
begin tran
SELECT * FROM Sites
update Sites set SiteName='----------' where SiteId = 2
waitfor delay '00:00:10'
Select * from Sites
rollback tran