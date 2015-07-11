/********************************************************
*Author: Thyanna Voisine & Dylan Schultz
*
*void PullVOCEntry( XBee xbee )
*Purpose: PullVOCEntry pulls each entry from
*         the persistence layer and directs
*         packet formation
*Entry: Xbee object that allows Xbee communication
*Exit:none
*
*void BuildPacket( XBee xbee )
*Purpose:BuildPacket takes a voc entry and creates
*        a Zigbee packet
*Entry: Xbee object that allows Xbee communication
*Exit: none
*
*void SendPacket( ZBTxRequest packet, XBee xbee )
*Purpose:SendPacket sends out the given packet out
*        on the Xbee connection
*Entry: The packet to be sent and Xbee object that
*       allows Xbee communication
*Exit: none
*
*void ReceivePacket( XBee xbee )
*Purpose: ReceivePacket checks if there are any 
*         incoming packets 
*Entry:Xbee object that allows Xbee communication
*Exit: none
*
*void ParsePacket( XBee xbee )
*Purpose:Parse Packet directs the packet retrieval
*        behavior of program
*Entry:Xbee object that allows Xbee communication
*Exit: none
*
*void Display( int size )
*Purpose: Display is used for debugging in
*         order to test if the New File is
*         being pulled from correctly
*Entry: the number of entries in the New file
*Exit: none
*
*void SendDisconnect( XBee xbee )
*Purpose:
*Entry: Xbee object that allows Xbee communication
*Exit: none
*
*int CalculateDownloadSize()
*Purpose: Calculate Download Size counts the
*         number of VOC entries in the New
*         folder
*Entry: none
*Exit: number of VOC entries for download
*
*void Purge()
*Purpose:Purge deallocates memory upon shut down
*Entry: none
*Exit: none
*
*void ReturnDownloadSize(XBee xbee, int downloadSize )
*Purpose: ReturnDownloadSize creates a Zigbee packet
*         that contains the number of VOC enteries
*         that need to be sent. 
*Entry:Xbee object that allows Xbee communication and
*      and the value stores in the packet
*Exit:none
*
*void flashLed(int pin, int times, int wait)
*Purpose:flashLed is used for debugging where the program
*        is having issues through flashing the LED on 
*        the arduino
*Entry: what pin the LED to flash is located at, how 
*       many times to flash the LED, and how long to 
*       flash the LED
*Exit:none
*
***********************************************************/
#ifndef ARDUINOXBEECONNECTOR
#define ARDUINOXBEECONNECTOR

#include <Arduino.h>
#include <SdFat.h>
#include <SdFatUtil.h>
#include <XBee.h>
#include "LinkedList.h"

#define SERIES 1
#define PACKETSIZE 17

#define SEMICOLON 59  //delimiter check

void PullVOCEntry( XBee xbee );
void PullVOCEntry( XBee xbee, LinkedList & list );
void BuildPacket( XBee xbee );
void SendPacket( ZBTxRequest packet, XBee xbee );
void ReceivePacket( XBee xbee );
void ReceivePacket( XBee xbee, LinkedList & file );
void ParsePacket( XBee xbee ); 
void ParsePacket( XBee xbee, LinkedList & list );
void Display( int size ); 
void SendDisconnect( XBee xbee );
int CalculateDownloadSize(); 
int CalculateDownloadSize( LinkedList & files );
void Purge();
void Shutdown( XBee xbee );
void Shutdown( XBee xbee, LinkedList & list );
void Initialize( XBee xbee );
void Initialize( XBee xbee, LinkedList & list );
void ReturnDownloadSize(XBee xbee, uint8_t* );
void AcceptDisconnect( XBee xbee );
void AcceptConnect( XBee xbee );
void AcceptConnect( XBee xbee, LinkedList & list );
void AcceptTransfer( XBee xbee ); 
void AcceptTransfer( XBee xbee, LinkedList & list );


void FlashLed(int pin, int times, int wait);   //Debugging 

#endif
