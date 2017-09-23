//-----------------------------------------
// CLASS: SearchableOrderedList
//
// Author: Cong Thanh Tran
//
// REMARKS: Implements a simple singly-linked searchable ordered list 
//-----------------------------------------

public class SearchableOrderedList extends OrderedList {
	
	//------------------------------------------------------
	// SearchableOrderedList Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: None
	// Returns: None
	//------------------------------------------------------
	SearchableOrderedList() { super(); }
	
	//------------------------------------------------------
	// insert (overrides)
	//
	// PURPOSE:	override the superclass insert
	//------------------------------------------------------
	public void insert(OrderedItem item)
    {
		//check if the underlying item is really a SearchableOrderedItem.
		//if so then add it to the list, else end program with an error
		if (item instanceof SearchableOrderedItem)
		    super.insert(item);
		else
			(new InvalidObjectTypeError("SearchableOrderedList.insert()", item.getClass().getCanonicalName(), "SearchableOrderedItem")).throwError();
	}
	
	//------------------------------------------------------
	// insert
	//
	// PURPOSE:	insert item in ascending order
	// PARAMETERS:
	//		SearchableOrderedItem: item to insert
	//------------------------------------------------------
	public void insert( SearchableOrderedItem item )
    {
		super.insert(item); 
	}
	
	//------------------------------------------------------
	// getAt (overrides)
	//
	// PURPOSE:	get item at specified index without changing the list.
	// PARAMETERS:
	//		int: index of item to get (zero-based indexing)
	// Returns:
	//		SearchableOrderedItem: the fetched item
	//------------------------------------------------------
	public SearchableOrderedItem getAt( int index )
    {
		OrderedItem item = super.getAt( index );
		assert item instanceof SearchableOrderedItem;
		return ( SearchableOrderedItem )item;
	}
	
	//------------------------------------------------------
	// removeAt (overrides)
	//
	// PURPOSE:	remove item at specified index.
	//			returned from a SearchableOrderedList
	// PARAMETERS:
	//		int: index at which to remove (zero-based indexing)
	// Returns:
	//		SearchableOrderedItem: the item that was removed
	//------------------------------------------------------
	public SearchableOrderedItem removeAt( int index )
    {
		//simple type checking with an assertion is used
		OrderedItem item = super.removeAt(index);
		assert item instanceof SearchableOrderedItem;
		return ( SearchableOrderedItem )item;
	}

	//------------------------------------------------------
	// hasKey
	//
	// PURPOSE:	checks whether an item with the specified key exists in the list
	// PARAMETERS:
	//		String: the search key
	// Returns:
	//		boolean: true if the item exists in the list, false otherwise
	//------------------------------------------------------
	public boolean hasKey( String key )
    {
		boolean isFound = false;
		int index = 0;
		while ( !isFound && index < getSize() )
        {
			if ( key.equals( getAt(index).getKey() ) )
			    isFound = true;
			index++;
		}//while
		
		return isFound;
	}
	
	//------------------------------------------------------
	// hasItem
	//
	// PURPOSE:	convenience method for hasKey
	// PARAMETERS:
	//		SearchableOrderedItem: the item to search for
	// Returns:
	//		boolean: true if the item exists in the list, false otherwise
	//------------------------------------------------------
	public boolean hasItem( SearchableOrderedItem item ) { return hasKey(item.getKey()); }
	
	//------------------------------------------------------
	// removeByKey
	//
	// PURPOSE:	removes the first item in the list with the given key
	// PARAMETERS:
	//		String: the search key
	// Returns:
	//		SearchableOrderedItem: reference to the removed item or null if item not found
	//------------------------------------------------------
	public SearchableOrderedItem removeByKey( String key )
    {
		boolean isFound = false;
		int index = 0;
		SearchableOrderedItem removedItem = null;
		while ( !isFound && index < getSize() )
        {
			if ( key.equals( getAt(index).getKey() ) )
			{
				isFound = true;
				removedItem = removeAt( index );
			}//if
			index++;
		}//while
		
		return removedItem;
	}
	
	//------------------------------------------------------
	// removeItem
	//
	// PURPOSE:	convenience method for removeByKey
	// PARAMETERS:
	//		SearchableOrderedItem: the item to search for
	// Returns:
	//		SearchableOrderedItem: reference to the removed item or null if item not found
	//------------------------------------------------------
	public SearchableOrderedItem removeItem( SearchableOrderedItem item ) { return removeByKey(item.getKey()); }
	
	//------------------------------------------------------
	// getByKey
	//
	// PURPOSE:	get first item in the list by its key without changing the list.
	// PARAMETERS:
	//		String: search key
	// Returns:
	//		SearchableOrderedItem: the fetched item or null if item not found
	//------------------------------------------------------
	public SearchableOrderedItem getByKey(String key){
		//loop over the items and check their keys against the one given.
		//return reference to the first item found in the list.
		boolean isFound = false;
		int index = 0;
		SearchableOrderedItem fetchedItem = null;
		while ( !isFound && index < getSize() )
        {
			if ( key.equals( getAt(index).getKey() ) )
			{
				isFound = true;
				fetchedItem = getAt( index );
			}//if
			index++;
		}//while
		
		return fetchedItem;
	}
	
}//SearchableOrderedList
