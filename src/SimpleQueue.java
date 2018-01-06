//-----------------------------------------
// CLASS: SimpleQueue
//
// Author: Cong Thanh Tran
//
// REMARKS: Implements a simple queue of ListItems.
//-----------------------------------------

public class SimpleQueue extends ListItem {
	
	//the list that will be used as a queue internally.
	//but will "turn-off" some many functionalities of a normal list.
	private SimpleLinkedList list;
	
	//------------------------------------------------------
	// SimpleQueue Constructor
	//
	// PURPOSE:	Initializes this object
	//------------------------------------------------------
	public SimpleQueue() { list = new SimpleLinkedList(); }
	
	//------------------------------------------------------
	// getSize
	//
	// PURPOSE:	Returns current number of elements
	// Returns: Curent number of elements
	//------------------------------------------------------
	public int getSize() { return list.getSize(); }
	
	//------------------------------------------------------
	// enqueue
	//
	// PURPOSE: add an item to the back of the queue
	// PARAMETERS: 
	//		ListItem: the item to enqueue
	//------------------------------------------------------
	public void enqueue( ListItem item ) { list.insertAtEnd( item ); }
	
	//------------------------------------------------------
	// dequeue
	//
	// PURPOSE: remove an item from the front of the queue
	// Returns:
	//		ListItem: the item removed
	//------------------------------------------------------
	public ListItem dequeue() { return list.removeFromFront(); }
	
	//------------------------------------------------------
	// isEmpty
	//
	// PURPOSE: check if queue is empty
	// Returns:
	//		boolean: false if there are items left to dequeue, true otherwise
	//------------------------------------------------------
	public boolean isEmpty() { return getSize() == 0; }

}//SimpleQueue
