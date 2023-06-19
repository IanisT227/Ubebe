begin tran
Select * from Drone_Manufacturer
waitfor delay '00:00:10'
update Drone_Manufacturer set ManufacturerName='-----------' where ManufacturerId = 2006
Select * from Drone_Manufacturer
commit