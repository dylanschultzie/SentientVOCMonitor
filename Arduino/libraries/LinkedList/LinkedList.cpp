/*****************************************
*Author: Dylan Schultz
*****************************************/
#include "LinkedList.h"

//ctor initializes members
LinkedList::LinkedList()
{
	m_head = NULL;
	m_count = 0;
}
//one arg ctor initializes members
LinkedList::LinkedList( String data )
{
	m_count = 0;
	m_head = NULL;
	m_head = CreateNode( data );
}
//dtor destroys object
LinkedList::~LinkedList()
{
	PurgeList();
}
//adds node to back of list
void LinkedList::AppendNode( Node * nn )
{
	Node * travel = m_head;

	if( m_head == NULL )
		m_head = nn;
	else
	{
		while( travel->m_next != NULL )
			travel = travel->m_next;
		travel->m_next = nn;
	}
	m_count++;
}
//returns data from head of string
String LinkedList::Read()
{
	Node * readNode = NULL;
	String data;

	readNode = m_head;
	m_head = m_head->m_next;
	data = readNode->m_data;
	delete readNode;
	m_count = m_count - 1;
	return data;
}
//deallocated memory in list
void LinkedList::PurgeList()
{
	Node * travel = 0;
	while( m_head->m_next != 0 )
	{
		travel = m_head;
		m_head = m_head->m_next;
		delete travel;
	}
	m_count = 0;
}
//creates a node and adds to end of list
Node * LinkedList::CreateNode( String data )
{
	Node * nNode = new Node;
	nNode->m_data = data;
	nNode->m_next = NULL;
	
	AppendNode( nNode );
	return nNode;
}
//returns number of elements in list
int LinkedList::GetCount()
{
	return m_count;
}
