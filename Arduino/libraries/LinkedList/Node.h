#ifndef NODE_H
#define NODE_H
/******************************************************************
* Author: Dylan Schultz
*
* Purpose:
*		The purpose of this struct is to simply act as a node
*		for the linkedlist class. This has no functionality aside
*		from containing data. 
*
******************************************************************/
#include <Arduino.h>

struct Node
{
	String m_data;
	Node * m_next;
};

#endif