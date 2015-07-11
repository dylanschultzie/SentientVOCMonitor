IF  DB_ID('VOCMS') IS NOT NULL
DROP DATABASE VOCMS;
GO

CREATE DATABASE VOCMS;
GO

USE VOCMS;

CREATE TABLE AlertSubscriber
(
	AlertSubscriberID	INT				CONSTRAINT Alert_PK PRIMARY KEY		IDENTITY,
	FirstName			VARCHAR(30)		NOT NULL,
	LastName			VARCHAR(30)		NOT NULL,
	DateAdded            DateTime       DEFAULT GetDate(),
	Email				VARCHAR(50)		NOT NULL, 
	CONSTRAINT Email_check CHECK( Email LIKE '%@%.%' )
);

CREATE TABLE Monitor 
(
	MonitorID		INT				CONSTRAINT Monitor_PK PRIMARY KEY		IDENTITY,
	Latitude		float		    NOT NULL,	
	Longitude		DECIMAL(4,4)    NOT NULL,
	Zip				VARCHAR(5)	    NOT NULL,
	CONSTRAINT Zip_Check CHECK( Zip LIKE '[0-9][0-9][0-9][0-9][0-9]')    --Zip Codes are five digits 
);

CREATE TABLE AlertSubscriber_MonitorList 
(
	AlertSubscriberID	INT			CONSTRAINT Alert_FK1 REFERENCES AlertSubscriber(AlertSubscriberID),
	MonitorID			INT			CONSTRAINT Alert_FK2 REFERENCES Monitor(MonitorID),
	CONSTRAINT AlertList_PrimaryKey PRIMARY KEY ( AlertSubscriberID, MonitorID ) 
);

CREATE TABLE VOC 
(
	VOCID					INT				CONSTRAINT VOC_PK PRIMARY KEY	IDENTITY,	
	SensorName				VARCHAR(30)		NOT NULL,
	DangerZone              INT				NOT NULL, 
	ProtectionInformation   VARCHAR(2048)	NOT NULL,
	SymptomInformation	    VARCHAR(2048)	NOT NULL,
	CONSTRAINT DangerZone_Check CHECK( DangerZone BETWEEN 50 AND 5000) 
      --Precision of sensor falls between 50 - 5000ppm
);


CREATE TABLE VOCReading 
(
	VOCReadingID	INT		    CONSTRAINT VOCReading_PK PRIMARY KEY		IDENTITY,
	MonitorID		INT		    CONSTRAINT VOCReading_Monitor_FK REFERENCES Monitor(MonitorID),
	VOCID			INT		    CONSTRAINT VOCReading_VOC_FK REFERENCES VOC(VOCID),
	Level			INT		    NOT NULL,
	Date			DateTime	NOT NULL,              
      --Not when added to database but when retrieved through sensor
	CONSTRAINT VOC_Level_Check CHECK( Level BETWEEN 50 AND 5000 )                     
      --Precision of sensor falls between 50 - 5000ppm
);
