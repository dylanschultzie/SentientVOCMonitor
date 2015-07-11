/*****************************************
*Author: Thyanna Voisine
*Note: VOC- Volatile Organic Compound 
*****************************************/
#include "ArduinoFileStorage.h"

int accuracyFlag = 0;    //true if sanity check fails
int vocLevelS = 0;       //VOC level for the (S)torage level

SdFat sd; //gives SD persistence capabilities

//Creates files used to store VOC entries
void CreateFiles()
{
	SdFile newDataFile("NEWDATA.TXT", O_WRITE | O_APPEND | O_CREAT);
	SdFile oldDataFile( "OLDDATA.TXT", O_WRITE | O_APPEND | O_CREAT); 
}
//Sets the accuracy flag
void SetAccuracyFlag( int flag )
{
	accuracyFlag = flag;  
}
//Retains the voc level, date, time, and accuracy level for storage
void SignalTransferPersistence( int level , String date, int time )
{
	vocLevelS = level; 
	
	SendVOCLevel( vocLevelS, date, time, accuracyFlag ); 
}
//Deletes Old file, renames New file to Old file, and creates another New file
void WriteOverOld()
{
    SdFile oldDataFile("OLDDATA.TXT", O_WRITE | O_CREAT );  //Open old
    oldDataFile.remove();                         //Remove old

    SdFile newDataFile("NEWDATA.TXT", O_WRITE | O_CREAT ); //Open New
    newDataFile.close(); 
  
    if (!newDataFile.rename(sd.vwd(), "OLDDATA.TXT")) //Rename New file to Old file
    {
		Serial.println("Error: New file not renamed");
    }

    SdFile newNewDataFile( "OLDDATA.TXT", O_WRITE | O_CREAT ); 
    newNewDataFile.close();
 
}
//Sending VOCLevel data to file persistence layer
void SendVOCLevel(int level, String date, int time, int accuracy) 
{
	String dataString = ""; 
	String endType = ",";    //type delimiter
	String endEntry = ";";   //Entry delimiter

	SdFile newDataFile("NEWDATA.TXT", O_WRITE | O_APPEND | O_CREAT ); 

    if (newDataFile.isOpen()) 
    { 
		dataString = level + endType +
                             date  + endType + 
			     time  + endType + 
			     accuracy + endEntry;

		newDataFile.print(dataString);
		newDataFile.close();
    }  
    else 
    {
		Serial.println("Error: New file not opened");
    } 
}
//Retains the voc level, date, time, and accuracy level for storage
void SignalTransferPersistence( int level , String date, int time, LinkedList & list )
{
	vocLevelS = level; 
	
	SendVOCLevel( vocLevelS, date, time, accuracyFlag, list ); 
}
//Sending VOCLevel data to file persistence layer
void SendVOCLevel(int level, String date, int time, int accuracy, LinkedList & list ) 
{
	String dataString = ""; 
	String endType = ",";    //type delimiter
	String endEntry = ";";   //Entry delimiter

		dataString = level + endType +
                             date  + endType + 
			     time  + endType + 
			     accuracy + endEntry;
		list.CreateNode( dataString );
}
