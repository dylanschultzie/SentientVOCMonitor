USE VOCMS;

CREATE TABLE AlertSubscriber
(
	AlertSubscriberID	INT			CONSTRAINT AlertSubscriber_PK PRIMARY KEY IDENTITY(0,1),
	FirstName			VARCHAR(20)	NOT NULL,
	LastName			VARCHAR(20)	NOT NULL,
	DateAdded			DateTime	DEFAULT GetDate(),
	Email				VARCHAR(50)	NOT NULL, 
	CONSTRAINT Email_check CHECK( Email LIKE '%@%.%' )
) 

CREATE TABLE Monitor
(
	MonitorID		INT				CONSTRAINT Monitor_PK PRIMARY KEY		IDENTITY(0,1),
	Latitude		DECIMAL(9,6)	NOT NULL,
	Longitude		DECIMAL(9,6)	NOT NULL,
	Zip				VARCHAR(5)		NOT NULL,
	SerialNumber	VARCHAR(19)		NOT NULL,
	CONSTRAINT Zip_Check CHECK( Zip LIKE '[0-9][0-9][0-9][0-9][0-9]')    --Zip Codes are five digits
)

CREATE TABLE VOC
(
	VOCID					INT				CONSTRAINT VOC_PK PRIMARY KEY	IDENTITY(0,1),
	SensorName				VARCHAR(30)		NOT NULL,
	DangerZone				INT				NOT NULL,
	ProtectionInformation	VARCHAR(2048)	NOT NULL,
	SymptomInformation		VARCHAR(2048)	NOT NULL,
	CONSTRAINT DangerZone_Check CHECK( DangerZone BETWEEN 50 AND 5000)
)

CREATE TABLE AlertSubscriber_MonitorList
(
	AlertSubscriberID	INT			CONSTRAINT AlertSubscriber_MonitorList_FK1 REFERENCES AlertSubscriber(AlertSubscriberID),
	MonitorID			INT			CONSTRAINT AlertSubscriber_MonitorList_FK2 REFERENCES Monitor(MonitorID),
	CONSTRAINT AlertList_PrimaryKey PRIMARY KEY ( AlertSubscriberID, MonitorID ) 
)

CREATE TABLE VOCReading
(
	MonitorID	INT		    CONSTRAINT VOCReading_FK1 REFERENCES Monitor(MonitorID),
	VOCID		INT		    CONSTRAINT VOCReading_FK2 REFERENCES VOC(VOCID),
	Level		INT			NOT NULL,
	Date		DateTime	DEFAULT GetDate(),
	Accuracy	INT			NOT NULL,
)