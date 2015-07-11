USE VOCMS;
if OBJECT_ID('GetVOCData') IS NOT NULL
DROP PROC GetVOCData
GO

CREATE PROC GetVOCData
		@SensorNameVar varchar(30)
AS

SELECT Date, Time, Sensor.SensorName, VOCLevels.Level
FROM VOCLevels JOIN Sensor
  ON VOCLevels.SensorID = Sensor.SensorID
WHERE Sensor.SensorName = @SensorNameVar
ORDER BY Date, Time

