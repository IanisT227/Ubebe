USE CarBusiness
GO

--part 1
BEGIN TRAN
WAITFOR DELAY '00:00:10'
insert into Sites(SiteId,SiteName) values (258, 'ttttttttttt' )
COMMIT TRAN