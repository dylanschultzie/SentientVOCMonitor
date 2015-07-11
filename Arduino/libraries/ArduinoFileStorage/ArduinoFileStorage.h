/********************************************************
*Author: Thyanna Voisine
*
*void CreateFiles()
*Purpose: CreateFiles creates the new and old files
*Entry: none
*Exit: none
*
*void SetAccuracy( int flag )
*Purpose: SetAccuracy sets the accuracy flag
*Entry: none
*Exit: none
*
*void WriteOverOld(  )
*Purpose: WriteOverOld deletes the old folder
*         and renames the new folder to old
*Entry: none
*Exit: none
*
*void SendVOCLevel(int level, string date, 
*          string time, int accuracy ) 
*Purpose: SendVOCLevel writes the sensor value to the
*          new folder
*Entry: current voc level, the date in d/m/y, time in
*       military time, the accuracy check, and 
*       corruption check
*Exit: none
*
*void SignalTransferPersistence( int vocLevel, 
*                         string date, string time )
*Purpose: SignalTransferPersistence initiates the
*         storage of the voc data
*Entry: current voc level, the date in d/m/y, and 
*       time in military format 
* Exit: none
*
***********************************************************/
#ifndef ARDUINOFILESTORAGE_H
#define ARDUINOFILESTORAGE_H

#include <SdFat.h>
#include <SdFatUtil.h>
#include "LinkedList.h"

void CreateFiles();
void SetAccuracyFlag( int flag );
void WriteOverOld();
void SendVOCLevel(int level, String date, int time, int accuracy); 
void SignalTransferPersistence( int vocLevel, String date, int time); 
void SendVOCLevel(int level, String date, int time, int accuracy, LinkedList & list); 
void SignalTransferPersistence( int vocLevel, String date, int time, LinkedList & list); 

#endif
