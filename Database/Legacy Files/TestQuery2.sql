SELECT Email, Monitor.Zip, Monitor.MonitorID
FROM Customer JOIN AlertList ON Customer.CustomerID = AlertList.CustomerID JOIN Monitor ON AlertList.MonitorID = Monitor.MonitorID
              JOIN VOCLevels ON Monitor.MonitorID = VOCLevels.MonitorID JOIN Sensor ON VOCLevels.SensorID = Sensor.SensorID