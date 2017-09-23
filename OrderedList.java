//-----------------------------------------
// CLASS: OrderedList
//
// Author: Cong Thanh Tran
//
// REMARKS: Implements a simple singly-linked ordered list 
//-----------------------------------------

public class OrderedList extends ListItem
{

	private SimpleLinkedList list; //note use of containment inheritance type
	
	//------------------------------------------------------
	// OrderedList Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: None
	// Returns: None
	//------------------------------------------------------
	OrderedList() { list = new SimpleLinkedList(); }
	
	//------------------------------------------------------
	// insert
	//
	// PURPOSE:	insert item in ascending order
	// PARAMETERS:
	//		OrderedItem: item to insert
	// Returns: none
	//------------------------------------------------------
	public void insert(OrderedItem item)
    {
		//ordered insert
		int index = 0;
		while ( index < list.getSize() && getAt(index).compareTo(item) <= 0 )
            index++;
		list.insertAt(item, index);		 
	} //insert
	
	//------------------------------------------------------
	// getAt
	//
	// PURPOSE:	get item at specified index without changing the list
	// PARAMETERS:
	//		int: index of item to get (zero-based indexing)
	// Returns:
	//		OrderedItem: the fetched item
	//------------------------------------------------------
	public OrderedItem getAt( int index )
    {
		ListItem item = list.getAt(index);
		assert item instanceof OrderedItem;
		return ( OrderedItem )item;
	}
	
	//------------------------------------------------------
	// removeAt
	//
	// PURPOSE:	remove item at specified index
	// PARAMETERS:
	//		int: index at which to remove (zero-based indexing)
	// Returns:
	//		OrderedItem: the item that was removed
	//------------------------------------------------------
	public OrderedItem removeAt( int index )
    {
		ListItem item = list.removeAt(index);
		assert item instanceof OrderedItem;
		return (OrderedItem)item;
	} //removeAt
	
	//------------------------------------------------------
	// getSize
	//
	// PURPOSE:	Returns current number of elements
	// PARAMETERS: None
	// Returns: Curent number of elements
	//------------------------------------------------------
	public int getSize() { return list.getSize(); }
	
	//------------------------------------------------------
	// toString
	//
	// PURPOSE:	obtain a String representation of this list
	// PARAMETERS: none
	// Returns: 
	//		String: String representation of this list
	//------------------------------------------------------
	public String toString() { return list.toString(); }

}//OrderedList
