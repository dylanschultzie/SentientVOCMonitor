/********************************************************
*Author: Dylan Schultz
*
* LinkedList()
* Purpose: Initilizes data members and methods
* Entry: none
* Exit: class is created
*
* LinkedList( String data )
* Purpose: Initialized data members and methods
* Entry:none
* Exit:class is created
*
* ~LinkedList()
* Purpose: Destorys the current object
* Entry:none
* Exit:object is destroyed
*
* void AppendNode( Node * nn )
* Purpose: Adds a node to the end of the list
* Entry: none
* Exit: list is one element longer
*
* String Read()
* Purpose: Returns the head node and deletes it
* Entry: list must not be empty
* Exit: none
*
* Node * CreateNode( String data )
* Purpose:Creates a node and adds to end of the list
* Entry:none
* Exit:list is one element shorter
*
* void PurgeList()
* Purpose: deallocates list
* Entry: none
* Exit: list contains no elements
*
* int GetCount()
* Purpose:Returns number of elements in list
* Entry:none
* Exit:none
*
******************************************************************/
#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include "Node.h"
#include <Arduino.h>

class LinkedList
{
	public:
		LinkedList();
		LinkedList( String data );
		~LinkedList();

		void AppendNode( Node * nn );
		String Read();
		Node * CreateNode( String data );
		void PurgeList();
		int GetCount();

	private:
		Node * m_head;
		int m_count;
};
#endif