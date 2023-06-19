USE CarBusiness	
GO

SET TRAN ISOLATION LEVEL SNAPSHOT
BEGIN TRAN

WAITFOR DELAY '00:00:10'

-- T1 has now updated and obtained a lock on this table
-- trying to update the same row will result in a error (process is blocked)
UPDATE Sites SET SiteName='lalalala' WHERE SiteId=1
COMMIT TRAN