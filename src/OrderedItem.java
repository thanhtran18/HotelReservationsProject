//-----------------------------------------
// CLASS: OrderedItem
//
// Author: Cong Thanh Tran
//
// REMARKS: An abstract class defining the group of things that could appear
//			as data in an OrderedList.  
//
//-----------------------------------------

public abstract class OrderedItem extends ListItem {
	
	//------------------------------------------------------
	// compareTo (abstract)
	//
	// PURPOSE:	compares two OrderedItems
	// PARAMETERS:
	//		OrderedItem: the OrderedItem to compare to this
	// Returns:
	//		int: a negative integer, zero, or a positive integer if this object is, respectively, less than, equal to,
	// 		or greater than the specified object.
	//------------------------------------------------------
	public abstract int compareTo(OrderedItem other);

 } //OrderedItem
