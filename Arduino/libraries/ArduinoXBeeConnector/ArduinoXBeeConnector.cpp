/*****************************************
*Author: Thyanna Voisine & Dylan Schultz
*****************************************/
#include"ArduinoXBeeConnector.h"

const int MAX = 30;     //The max data size for a packet
uint8_t * payload;   //the VOC entry 


#if SERIES == 1 //if series 1 XBEE 

uint16_t addr16;
Rx16Response rx = Rx16Response();  //configures an XBee response packet
Tx16Request tx;                    //TxStatusResponse txStatus = TxStatusResponse();

//Creates Zigbee packet
void BuildPacket( XBee xbee )
{
	delay(10); 
	tx = Tx16Request(addr16, payload, PACKETSIZE);

	xbee.send( tx );
}
//reveives packets from dongle
void ReceivePacket( XBee xbee )
{
	xbee.readPacket(5000);

    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
		if( xbee.getResponse().getApiId() == RX_16_RESPONSE)
		{
			FlashLed(12, 2, 100 ); 
			xbee.getResponse().getRx16Response(rx);  //grab packet
			ParsePacket( xbee );                     //directs packet retrieval behavior
		}
    }
}
//overloads recieve to simulate persistence with a linked list
void ReceivePacket( XBee xbee, LinkedList & file )
{
	xbee.readPacket(5000);

    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
		if( xbee.getResponse().getApiId() == RX_16_RESPONSE)
		{
			FlashLed(12, 2, 100 );
			xbee.getResponse().getRx16Response(rx);  //grab packet
			ParsePacket( xbee, file );               //directs packet retrieval behavior
			FlashLed(11,1,100);
		}
    }
}
//Disconnects from mobile client
void SendDisconnect( XBee xbee )
{	
	uint8_t discon[] = "Disconnect";
	tx = Tx16Request( addr16, discon, (uint8_t)sizeof(discon) );
	xbee.send(tx);
}
//Direct connection behavior of sending transfer size 
void AcceptConnect( XBee xbee )
{
	int downloadSize = 5;//CalculateDownloadSize();     //get download size
	uint8_t downloadSizePacket[1] = { downloadSize };		//allocate space for download, 48 is the start of numbers in the ascii chart. 

    delay(50);

    addr16 = rx.getRemoteAddress16();
	ReturnDownloadSize( xbee, downloadSizePacket );  //Send the number of VOC entries the mobile client should expect
}
void AcceptConnect( XBee xbee, LinkedList & file )
{
	int downloadSize = CalculateDownloadSize( file );     //get download size
	uint8_t downloadSizePacket[1] = { downloadSize };		//allocate space for download, 48 is the start of numbers in the ascii chart. 

    delay(50);

    addr16 = rx.getRemoteAddress16();
	ReturnDownloadSize( xbee, downloadSizePacket );  //Send the number of VOC entries the mobile client should expect
}
//Send download size to Mobile Client
void ReturnDownloadSize(XBee xbee, uint8_t* downloadSize )
{
	tx = Tx16Request( addr16, (uint8_t*)downloadSize, (uint8_t)sizeof(uint8_t) );
    xbee.send(tx);
}











#else    //else series 2 XBee

ZBRxResponse rx = ZBRxResponse();  //object that allows packet recieving 
ZBTxRequest zbTx; //object that allows packet sending
XBeeAddress64 addr64 = XBeeAddress64();  //Sets sending and recieving address

//Creates Zigbee packet
void BuildPacket( XBee xbee )
{
	delay(10); 
	ZBTxRequest packet = ZBTxRequest(addr64, payload, (uint8_t)sizeof(payload) );
	//SendPacket( packet, xbee ); 
	FlashLed(12, 1, 250 ); 

	xbee.send( packet );
}
//Checks if packet is received 
void ReceivePacket( XBee xbee )
{
	//PullVOCEntry( xbee );
	xbee.readPacket(8000);
	FlashLed(12, 6, 50 ); 
    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
        xbee.getResponse().getZBRxResponse(rx);  //grab packet
		ParsePacket( xbee );                     //directs packet retrieval behavior
    }
    else if( xbee.getResponse().isError() )
    {
        FlashLed(12, 4, 100 ); 
    }
}

void ReceivePacket( XBee xbee, LinkedList & file )
{
	FlashLed(12, 6, 50 ); 
	//PullVOCEntry( xbee );
	xbee.readPacket(5000);
	FlashLed(12, 3, 75 );

    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
		if( xbee.getResponse().getApiId() == ZB_RX_RESPONSE)
		{
			xbee.getResponse().getZBRxResponse(rx);  //grab packet
			ParsePacket( xbee, file );                     //directs packet retrieval behavior
		}
    }
    else if( xbee.getResponse().isError() )
    {
        //FlashLed(12, 4, 100 ); 
    }
}
//Disconnects from mobile client
void SendDisconnect( XBee xbee )
{
	uint8_t discon[] = "Disconnect";
	zbTx = ZBTxRequest( addr64, discon, (uint8_t)sizeof(discon) );
	FlashLed(12, 2, 250 ); 
	xbee.send(zbTx);
}
//Direct connection behavior of sending transfer size 
void AcceptConnect( XBee xbee )
{
	int downloadSize = CalculateDownloadSize();     //get download size
	uint8_t downloadSizePacket = downloadSize + 48;		//allocate space for download, 48 is the start of numbers in the ascii chart. 

    delay(50);

    addr64 = rx.getRemoteAddress64();
	ReturnDownloadSize( xbee, downloadSizePacket );  //Send the number of VOC entries the mobile client should expect
}
//Direct connection behavior of sending transfer size 
void AcceptConnect( XBee xbee, LinkedList & list )
{
	uint8_t downloadSizePacket = (uint8_t)CalculateDownloadSize( list ) + 48;     //get download size, 48 is the start of numbers in the ascii chart. 
																				  //if calculatedownloadsize returns 0, 0+48 = 0 in terms of characters
    delay(50);

    addr64 = rx.getRemoteAddress64();
	ReturnDownloadSize( xbee, downloadSizePacket );  //Send the number of VOC entries the mobile client should expect
}
//Send download size to Mobile Client
void ReturnDownloadSize(XBee xbee, int downloadSize )
{
    zbTx = ZBTxRequest(  addr64, (uint8_t *)downloadSize, (uint8_t)sizeof(uint8_t) );
    xbee.send(zbTx);
}
#endif
//PullVOCEntry pulls each entry from the persistence layer and directs packet formation
void PullVOCEntry( XBee xbee )
{
	payload = new uint8_t[MAX*2];
	int entryIndex = 0; //Keeps track of number of entries read
    int arrayIndex = 0; //keeps track of location in a single entry
	int8_t dataString = 0; 
	int numEntries = CalculateDownloadSize();

	SdFile newDataFile( "NEWDATA.TXT", O_READ | O_CREAT );

	if( newDataFile.isOpen() )
	{
		while ((dataString = newDataFile.read()) >= 0 && entryIndex < numEntries) 
		{
			delay(50);

			if( dataString == SEMICOLON )
			{
				payload[arrayIndex++] = dataString;
				entryIndex++;
				BuildPacket( xbee );                   //Once entry is completely pulled, build packet
				delay(50);
			}
			else
            {
				payload[arrayIndex++] = dataString;
            }
		}
		newDataFile.close(); 
	}
	else
	{
		Serial.print("Error: New file not opened"); 
	}
}
//PullVOCEntry pulls each entry from the persistence layer and directs packet formation
void PullVOCEntry( XBee xbee, LinkedList & list )
{
	int entryIndex = 0; //Keeps track of number of entries read
	byte buf[MAX*2] = {0};
    int arrayIndex = 0; //keeps track of location in a single entry
	int8_t dataString = 0; 
	int numUnits = CalculateDownloadSize( list );
	String temp;

	if( numUnits == 0)
	{
		//this is what happens if there are no new packets to send
		uint8_t noNewData[] = "None";
		tx = Tx16Request( addr16, noNewData, (uint8_t)sizeof(noNewData) );
		xbee.send(tx);
	}
	else
	{
		while ( entryIndex < numUnits ) 
		{
			temp = list.Read();
			temp.getBytes(buf,MAX*2);
			payload = buf;
			BuildPacket( xbee );
			entryIndex++;
			FlashLed(11, 1, 200);
		}
	}
}
//deallocated memory upon monitor shut down
void Purge()
{
	delete [] payload; 
}
//Debugging: Displays item when needed
void Display( int size )
{

}
//Sends Zigbee packet
void SendPacket( ZBTxRequest packet, XBee xbee )
{
	xbee.send(packet);
}
//Direct packet retrieval behavior
void ParsePacket( XBee xbee )
{
	uint8_t * packetData = NULL;
	packetData = rx.getData();
    if( !strcmp( (char *)packetData, "Connect" ))  
    {
		AcceptConnect(xbee);										
    }
	else if( !strcmp( (char *)packetData, "Disconnect" )) 
	{
		AcceptDisconnect(xbee);
	}
	else if( !strcmp( (char *)packetData, "Transfer" ))
	{
		FlashLed(11,2,100);
		AcceptTransfer(xbee); 
	}
	else if( !strcmp( (char *)packetData, "Shutdown" ))
	{
		Shutdown(xbee);
	}
	else if( !strcmp( (char *)packetData, "Initialize" ))
	{
		Initialize(xbee);
	}
}
//Direct packet retrieval behavior
void ParsePacket( XBee xbee, LinkedList & list )
{
	uint8_t * packetData = NULL;
	packetData = rx.getData();
	packetData[rx.getDataLength()] = '\0';

    if( !strcmp( (char *)packetData, "Connect" ))  
    {
		AcceptConnect(xbee, list);										
    }
	else if( !strcmp( (char *)packetData, "Disconnect" )) 
	{
		AcceptDisconnect(xbee);
	}
	else if( !strcmp( (char *)packetData, "Transfer" ))
	{
		AcceptTransfer(xbee, list); 
		FlashLed(12,1,100);
	}
	else if( !strcmp( (char *)packetData, "Shutdown" ))
	{
		Shutdown(xbee, list);
	}
	else if( !strcmp( (char *)packetData, "Initialize" ))
	{
		Initialize(xbee, list);
	}
	else
	{
		tx = Tx16Request( addr16, packetData, 20 );
		xbee.send(tx);		
	}
}
void Shutdown( XBee xbee )
{

}
void Shutdown( XBee xbee, LinkedList & file )
{
	uint8_t shutdown[] = "Shutdown";
	tx = Tx16Request( addr16, shutdown, sizeof(shutdown) );
	xbee.send(tx);
	while(1);
}
void Initialize( XBee xbee )
{

}
void Initialize( XBee xbee, LinkedList & file )
{
	String newTime = (char *)rx.getData();


	if( newTime == "Initialize" )		
		xbee.readPacket(5000);				 //throw out previous packet

    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
		if( xbee.getResponse().getApiId() == RX_16_RESPONSE)
		{
			xbee.getResponse().getRx16Response(rx);  //grab packet
			newTime = (char *)rx.getData();
			String vocLevel = "50,";
			vocLevel += newTime;
			vocLevel += ",";
			vocLevel += "0";
			vocLevel += ";";

			file.PurgeList();
			file.CreateNode( vocLevel );
		}
    }

	FlashLed(11,1,250);
}
/*
void Initialize( XBee xbee, LinkedList & file )									//this is actual function. the above is a tester.
{
	String newTime = (char *)rx.getData();


	if( newTime == "Initialize" )		
		xbee.readPacket(5000);				 //throw out previous packet

    if( xbee.getResponse().isAvailable() )   //if able to receive 
    {
		if( xbee.getResponse().getApiId() == RX_16_RESPONSE)
		{
			xbee.getResponse().getRx16Response(rx);  //grab packet
			newTime = (char *)rx.getData();
			String vocLevel = "50,";
			vocLevel += newTime;
			vocLevel += ",";
			vocLevel += "0";
			vocLevel += ";";

			file.PurgeList();
			file.CreateNode( vocLevel );
		}
    }

	FlashLed(11,1,250);
}
*/
//Directs behavior of sending 'disconnect' packet back
void AcceptDisconnect( XBee xbee )
{
	SendDisconnect( xbee ); 
}
//Directs transfer of all packets from new file 
void AcceptTransfer( XBee xbee )
{
	PullVOCEntry( xbee );  //Start voc entry packet sending
    delay( 10 );
}
//Directs transfer of all packets from new file 
void AcceptTransfer( XBee xbee, LinkedList & list )
{
	PullVOCEntry( xbee, list );  //Start voc entry packet sending
    delay( 10 );
}
//Calculate number of VOC entries in New File
int CalculateDownloadSize()
{
	int size = 0; 
	int16_t dataString; 

	SdFile newDataFile( "NEWDATA.txt", O_READ | O_CREAT );

	if( newDataFile.isOpen() )
	{
		while( (dataString = newDataFile.read() ) >= 0 )
		{
			if( dataString == SEMICOLON )   //end of VOC entry
			{
				size++; 	
			}
		}
		newDataFile.close(); 
	}
	else
	{
		Serial.print("Error: New file not opened"); 
	}

	return size; 
}
int CalculateDownloadSize( LinkedList & files )
{
	return files.GetCount();
}

////Debugging: used to test where in program system stops working 
void FlashLed(int pin, int times, int wait) 
{
    for (int i = 0; i < times; i++) 
    {
      digitalWrite(pin, HIGH);
      delay(wait);
      digitalWrite(pin, LOW);
      
      if (i + 1 < times) 
      {
        delay(wait);
      }
    }
}
