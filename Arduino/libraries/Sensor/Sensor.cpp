/*****************************************
*Author: Thyanna Voisine
*****************************************/
#include"Sensor.h"

//Pulls analog value from sensor
int RetrieveVOCLevel(int analogInPin) 
{
	int vocLevel = 0; 
	vocLevel = analogRead(analogInPin); 
	return vocLevel;
}