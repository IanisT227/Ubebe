USE CarBusiness
GO

--- create a stored procedure that inserts data in tables that are in a m:n relationship;
--- if one insert fails, all the operations performed by the procedure must be rolled back (grade 3);

create or alter function udf_validateParamsSites(@name varchar(100), @id INT) RETURNS INT AS
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
		insert into Ads(SiteId,CarId, AdId) values(@id, 5, 101)
		commit tran
		select 'T committed'
	end try
	begin catch
		rollback tran
		select 'T rollbacked'
	end catch
end
go

usp_insertAds 'OLXyyyy',80;

usp_insertAds 'OLX',1;

Select * from Sites;
Select * from Ads;