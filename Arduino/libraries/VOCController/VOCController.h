/**************************************************
*Author: Thyanna Voisine
*
*int SignalTransferSensor( int analogInPin)
*Purpose: SignalTransferSensor 
*         returns current sensor
*         reading
*Entry: none
*Exit: voc level
*
*int CheckAccuary( int vocLevel )
*Purpose: CheckAccuracy acts as a 
*         sanity check for the sensor
*
*int RetrieveVOCLevel( int analogPin )
*Purpose: Pulls analog value from sensor
*Entry: none
*Exit: voc level retrieved
*
***************************************************/
#ifndef VOCCONTROLLER_H
#define VOCCONTROLLER_H

#include<Arduino.h>
#include<SdFat.h>
#include<time.h>

//---------------------------------------------
// store error strings in flash to save RAM
#define error(s) sd.errorHalt_P(PSTR(s))
//----------------------------------------------

int SignalTransferSensor(int analogInPin); 
int CheckAccuracy( int vocLevel ); 

//Suppose to be called from Sensor, but the Arduino platform does not allow this 
int RetrieveVOCLevel(int analogInPin);


#endif
