--insert success
INSERT INTO VOCLevels ( MonitorID, SensorID, Level, Date ) VALUES
(1, 5, 550, '2013-06-02 04:03:55' )

--insert fail
INSERT INTO Monitor ( Latitude, Longitude, Zip ) VALUES
( 42.254173, -121.786324, 976013 )

--will fail due to zip codes only being 5 characters long. They would be entering an incorrect zip.

--update success

UPDATE Sensor 
	SET DangerZone = 173.0
	WHERE SensorName = 'Ammonia';

--update fail

UPDATE VOCLevels
	SET Level = 1
	WHERE SensorID = 2
	
-- will fail due to not having permissions. nobody should be allowed to update the voc levels.

--delete success

DELETE FROM AlertList
	WHERE CustomerID = (SELECT CustomerID
						FROM Customer
						WHERE FirstName = 'Thyanna' AND LastName = 'Voisine')
DELETE FROM Customer
WHERE FirstName = 'Thyanna' AND LastName = 'Voisine'	

--delete fail

DELETE FROM VOCLevels
	WHERE SensorID = (SELECT SensorID
					  FROM Sensor
					  WHERE SensorName = 'Methyl mercaptan')

--fails due to permissions. nobody is allowed to delete data from VOCLevels.