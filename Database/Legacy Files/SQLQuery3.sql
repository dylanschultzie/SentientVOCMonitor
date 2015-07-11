SELECT DISTINCT Email, Sensor.SensorName, ProtectionInformation
FROM Customer JOIN AlertList ON Customer.CustomerID = AlertList.CustomerID JOIN Monitor ON AlertList.MonitorID = Monitor.MonitorID
              JOIN VOCLevels ON Monitor.MonitorID  = VOCLevels.MonitorID JOIN Sensor ON VOCLevels.SensorID = Sensor.SensorID
WHERE Monitor.Zip = (SELECT Monitor.Zip
                     FROM Monitor JOIN VOCLevels ON Monitor.MonitorID  = VOCLevels.MonitorID JOIN Sensor ON VOCLevels.SensorID = Sensor.SensorID
					 WHERE    VOCLevels.Level >= Sensor.DangerZone )

-- The Query to send an e-mail when a VOC level goes out of bounds for human safety