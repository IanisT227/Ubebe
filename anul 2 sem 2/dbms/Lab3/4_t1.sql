
USE CarBusiness
GO

alter database CarBusiness set ALLOW_SNAPSHOT_ISOLATION ON

WAITFOR DELAY '00:00:10'
BEGIN TRAN
UPDATE Sites SET SiteName='lalalala' WHERE SiteId=1
WAITFOR DELAY '00:00:05'
COMMIT TRAN