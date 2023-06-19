USE CarBusiness	
GO


SET DEADLOCK_PRIORITY HIGH
BEGIN TRAN
-- exclusive lock on table Allergens
update Sites SET SiteName = '**********' where SiteId = 3
WAITFOR DELAY '00:00:10'

update Cars SET Model = 'UpdateNEW' where CarId = 8
COMMIT TRAN
