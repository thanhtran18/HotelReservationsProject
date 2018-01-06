//-----------------------------------------
// CLASS: Hotel
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains information about individual Hotels in the project
//
//-----------------------------------------

public class Hotel extends BaseEntity
{
	
	//hold a list of rooms. Other hotel data is handled by superclass
	private SearchableOrderedList roomList;
	
	//------------------------------------------------------
	// Hotel Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		int: hotel ID
	//		String: hotel name
	//		SearchableOrderedList: list of rooms
	//------------------------------------------------------
	Hotel(int id, String name, SearchableOrderedList roomList)
    {
		super(id, name);
		this.roomList = roomList;
	}

	//------------------------------------------------------
	// getKey (overrides)
	//
	// PURPOSE:	overrides default behaviour because we want to search
	//			hotels by their IDs rather than by their names.
	// Returns:
	//		String: the key value (name field) for this object
	//------------------------------------------------------
	public String getKey() { return ""+getId(); }
	
	//------------------------------------------------------
	// getNumRooms
	//
	// PURPOSE:	Returns the number of rooms in this hotel
	// Returns:
	//		int: the number of rooms in this hotel
	//------------------------------------------------------
	public int getNumRooms() { return roomList.getSize(); }
	
	//------------------------------------------------------
	// getRoomByNumber
	//
	// PURPOSE:	Returns the room with the given number
	// PARAMETERS:
	//		int: room number
	// Returns:
	//		Room: the Room with the given room number of null if not found
	//------------------------------------------------------
	public Room getRoomByNumber(int roomNumber)
    {
		SearchableOrderedItem room = roomList.getByKey(""+roomNumber);
		if (room == null)
		    return null;
		//else
		assert room instanceof Room;
		return (Room)room;
	}
	
	//------------------------------------------------------
	// toString (overrides)
	//
	// PURPOSE:	returns a string representation of this object
	// PARAMETERS: none
	// Returns:
	//		String: this hotel's ID and name followed by room information
	//------------------------------------------------------
	public String toString()
    {
		//add hotel header info to the return String
		String hotelInfo = "" + getId() + ": " + getName() + "\n";
		
		//loop over each room and add their info to the String
		for (int ii = 0; ii < roomList.getSize(); ii++){
			hotelInfo += roomList.getAt(ii).toString();			
		}//for ii
		
		return hotelInfo;
	}//toString
}
