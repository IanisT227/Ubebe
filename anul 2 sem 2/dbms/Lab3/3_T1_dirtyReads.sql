USE CarBusiness
GO

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
WHERE SiteId = 2
WAITFOR DELAY '00:00:10'
ROLLBACK TRAN