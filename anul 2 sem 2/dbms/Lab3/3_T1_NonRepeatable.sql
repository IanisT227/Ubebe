USE CarBusiness
	GO


	INSERT INTO Sites(SiteId, SiteName) VALUES (10, 'LaJumate.ro')
	BEGIN TRAN
	WAITFOR DELAY '00:00:10'
	UPDATE Sites
	SET SiteName = 'Mobile.deu' 
	WHERE SiteId = 10
	COMMIT TRAN
