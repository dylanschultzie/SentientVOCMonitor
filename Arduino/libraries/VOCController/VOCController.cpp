/******************************************
*Author: Thyanna Voisine
******************************************/
#include "VOCController.h"


//Returns current sensor reading
int SignalTransferSensor(int analogPin)
{
	return RetrieveVOCLevel(analogPin); 
}
//Ensures sensor is retrieving realistic values
int CheckAccuracy( int Level )
{
	int sanityCheck = 0; 

	if( Level < 570 && Level > 553 )   //analog values the sensor is capable of accurately calculating 
	{
		sanityCheck = 0; 
	}
	else
		sanityCheck = 1; 

	return sanityCheck; 
}
//retrieved VOC level from sensor
int RetrieveVOCLevel(int analogInPin)
{
	int vocLevel = 0; 
	vocLevel = analogRead(analogInPin); 
	return vocLevel;
}