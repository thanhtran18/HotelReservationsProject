//-----------------------------------------
// CLASS: SimpleLinkedList
//
// Author: Cong Thanh Tran
//
// REMARKS: Implements a simple singly-linked list 
//-----------------------------------------

public class SimpleLinkedList extends ListItem
{
	
	private Node head; //first node in the list
	private int size; //number of elements in this list
	
	//------------------------------------------------------
	// SimpleLinkedList Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: None
	// Returns: None
	//------------------------------------------------------
	SimpleLinkedList()
	{
		head = null;
		size = 0; 
	}
		
	//------------------------------------------------------
	// getSize
	//
	// PURPOSE:	Returns current number of elements
	// PARAMETERS: None
	// Returns: Curent number of elements
	//------------------------------------------------------
	public int getSize() { return size; }

	//------------------------------------------------------
	// insertAt
	//
	// PURPOSE:	insert item at specified index
	// PARAMETERS:
	//		ListItem: item to insert
	//		int: index at which to insert (zero-based indexing)
	// Returns: none
	//------------------------------------------------------
	public void insertAt( ListItem item, int index )
    {
		//bounds check
		if ( index < 0 || index > size )
		    (new SimpleLinkedListOutOfBoundsError()).throwError();
		
		//insert the item
		if ( index == 0 )
		    head = new Node(item, head);
		else
        {
			Node prev = head;
			for ( int ii = 1; ii < index; ii++ )
			    prev = prev.getNext();
			prev.setNext( new Node( item, prev.getNext() ) );
		}//else		
		
		size++;
	}

	//------------------------------------------------------
	// removeAt
	//
	// PURPOSE:	remove item at specified index
	// PARAMETERS:
	//		int: index at which to remove (zero-based indexing)
	// Returns:
	//		ListItem: the item that was removed
	//------------------------------------------------------
	public ListItem removeAt( int index ) {
		//bounds check
		if ( index < 0 || index >= size )
		    ( new SimpleLinkedListOutOfBoundsError() ).throwError();
		
		//remove the node
		Node removedNode;
		if ( index == 0 )
		{
			removedNode = head;
			head = head.getNext();
		}//if
		else
        {
			Node prev = head;
			for ( int ii = 1; ii < index; ii++ )
			    prev = prev.getNext();
			removedNode = prev.getNext();
			prev.setNext( prev.getNext().getNext() );
		}//else		
		
		size--;
		return removedNode.getData();
	}

	//------------------------------------------------------
	// insertAtFront
	//
	// PURPOSE:	add item at top of list
	// PARAMETERS:
	//		ListItem: item to insert
	//------------------------------------------------------
	public void insertAtFront( ListItem item ) { insertAt(item, 0); }
	
	//------------------------------------------------------
	// removeFromFront
	//
	// PURPOSE:	convenience method for removeAt(0)
	// Returns: 
	//		ListItem: the item removed
	//------------------------------------------------------
	public ListItem removeFromFront() { return removeAt(0); }
	
	//------------------------------------------------------
	// insertAtEnd
	//
	// PURPOSE:	add item at end of list
	// PARAMETERS:
	//		ListItem: item to insert
	//------------------------------------------------------
	public void insertAtEnd( ListItem item ) { insertAt(item, size); }
	
	//------------------------------------------------------
	// insert
	//
	// PURPOSE:	insert item at unspecified location (insert at front internally)
	// PARAMETERS:
	//		ListItem: item to insert
	//------------------------------------------------------
	public void insert(ListItem item) { insertAtFront(item); }
	
	//------------------------------------------------------
	// getAt
	//
	// PURPOSE:	get data at specified location in the list without changing the list
	// PARAMETERS:
	//		int: index of item to get (zero-based indexing)
	// Returns: 
	//		ListItem: item retrived from the list
	//------------------------------------------------------
	public ListItem getAt( int index )
    {
		//bounds check
		if ( index < 0 || index >= size )
		    ( new SimpleLinkedListOutOfBoundsError() ).throwError();
		
		//get the node containing the item
		Node itemNode = head;
		for ( int ii = 0; ii < index; ii++ )
		    itemNode = itemNode.getNext();
		
		//return the item. Nodes should never be returned as they are internal to the list
		return itemNode.getData();
	}
	
	//------------------------------------------------------
	// toString
	//
	// PURPOSE:	obtain a String representation of this list
	// PARAMETERS: none
	// Returns: 
	//		String: String representation of this list
	//------------------------------------------------------
	public String toString()
    {
		String returnString = "List containing " + size + " elements:\n";
		
		Node currentNode = head;
		for ( int ii = 0; ii < size; ii++ )
		{
			returnString += "    Element " + ii + ":\t" + currentNode.getData().toString() + "\n";
			currentNode = currentNode.getNext();
		}//for ii
		
		return returnString;
	}

	//-----------------------------------------
	// CLASS: Inner class Node
	//
	// REMARKS: A singly-linked SimpleLinkedList node 
	//-----------------------------------------

	private class Node
    {
	  
		private ListItem dataItem;
		private Node nextNode;
	  
		//------------------------------------------------------
		// Node Constructor
		//
		// PURPOSE:	Initializes this object
		// PARAMETERS:
		//		ListItem: The data this node is to hold
		//		Node: Pointer to the next Node in the list
		//------------------------------------------------------	  
		public Node( ListItem initItem, Node initLink )
        {
			dataItem = initItem; 
			nextNode = initLink;
		}

		//------------------------------------------------------
		// getNext
		//
		// PURPOSE:	returns reference to next node
		// Returns: reference to next node
		//------------------------------------------------------	 	  
		public Node getNext() { return nextNode; }
		
		//------------------------------------------------------
		// setNext
		//
		// PURPOSE:	sets reference to next node
		// PARAMETERS: 
		//		Node: reference to next node
		// Returns: none
		//------------------------------------------------------	 	  
		public void setNext(Node nextNode) { this.nextNode = nextNode; }
		
		//------------------------------------------------------
		// getData
		//
		// PURPOSE:	returns reference to data contained in this node
		// Returns: reference to data contained in this node
		//------------------------------------------------------	 	  
		public ListItem getData(){return dataItem;}
		
	} //Node

} //SimpleLinkedList







