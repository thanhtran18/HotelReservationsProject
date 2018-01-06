//-----------------------------------------
// CLASS: SearchableOrderedItem
//
// Author: Cong Thanh Tran
//
// REMARKS: An abstract class defining the group of things that could appear
//			as data in a SearchableOrderedList.  
//
//-----------------------------------------

public abstract class SearchableOrderedItem extends OrderedItem {
	
	//------------------------------------------------------
	// getKey (abstract)
	//
	// PURPOSE:	returns the key value for this object
	// PARAMETERS: none
	// Returns:
	//		String: the key value for this object
	//------------------------------------------------------
	public abstract String getKey();

 } //SearchableOrderedItem
