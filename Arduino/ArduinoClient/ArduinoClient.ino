/****************************************************
*Author: Thyanna Voisine
 & Dylan Schultz*Date Created: 2/17/2014
*Last Modified: 5/22/2014
*Purpose:
*    Directs the persistence layer for Arduino
*
*****************************************************/
#include <Time.h>

#include <SdFat.h>
#include <SdFatConfig.h>
#include <SdFatmainpage.h>
#include <SdFatStructs.h>
#include <SdFatUtil.h>

#include <XBee.h>
#include "ArduinoXBeeConnector.h"
#include "ArduinoFileStorage.h"
#include "VOCController.h"
#include "LinkedList.h"

#define error(s) sd.errorHalt_P(PSTR(s))

//Global variables
const uint8_t chipSelect = 10;//output
int analogInPin = A0;
int outputValue = 0;
XBee xbee = XBee();
int vocLevel = 0; 
String date = ""; //the current data mm/dd/yyyy
int time = 0;   //in hours
int flag = 0;   //sanity check for VOC level accuracy
String slash = "/";
int index = 0; 
LinkedList filestorage;

SdFat sdi; 

void setup()
{ 
   // Open serial communications and wait for port to open:
   Serial.begin(9600);
   xbee.begin( Serial );
   digitalWrite(10, HIGH);
   pinMode(12, OUTPUT);
   pinMode(11, OUTPUT);
   flashLed( 12, 4, 200 );
   setTime(2,59,0,27,4,14);  //Setting time
  //filestorage.CreateNode( "150,29/04/14,01,0" );
  //filestorage.CreateNode( "175,27/04/12,01,0" );
  //filestorage.CreateNode( "113,28/04/13,01,0" );
}
//main
void loop()
{ 
   //Serial.println(filestorage.Read());
   ReceivePacket( xbee, filestorage ); 

  // if( minute() == 0 && second() == 0 ) //hits once a second every hour
  // {
     if( index < 5 )
     { 
        vocLevel = SignalTransferSensor(analogInPin);      //telling sensor and SD that a VOC level is needed
        flag = CheckAccuracy( vocLevel ); 
        time = hour(); 
        date = day() + slash + month() + slash + year(); 
        SetAccuracyFlag( flag ); 
        SignalTransferPersistence( vocLevel, date, time, filestorage ); 
        flashLed( 11, 1, 200 );
        index++;
     }
   //}
}


