//-----------------------------------------
// CLASS: HotelDatabase
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that manages hotel data
//
//-----------------------------------------

public class HotelDatabase {
	
	//the list of Hotels
	private static SearchableOrderedList hotelList = new SearchableOrderedList();
	
	//------------------------------------------------------
	// addHotel 
	//
	// PURPOSE:	add a Hotel to this database
	// PARAMETERS: 
	//		Hotel: the hotel to be added to the database
	// Returns: none
	//------------------------------------------------------
	public static void addHotel(Hotel hotel){hotelList.insert(hotel);}
	
	//------------------------------------------------------
	// getHotelById 
	//
	// PURPOSE:	get a hotel by its ID
	// PARAMETERS: 
	//		int: the hotel ID
	// Returns:
	//		Hotel: the requested hotel or null if not found
	//------------------------------------------------------
	public static Hotel getHotelById(int hotelId)
    {
		SearchableOrderedItem item = hotelList.getByKey(""+hotelId);
		if (item == null)
		    return null;
		//else
		assert item instanceof Hotel;
		return (Hotel)item;
	}

	//------------------------------------------------------
	// getNumRoomsInHotel
	//
	// PURPOSE:	Returns the number of rooms in a hotel
	// PARAMETERS: 
	//		int: hotel ID
	// Returns:
	//		int: the number of rooms in the hotel or -1 if hotel is invalid
	//------------------------------------------------------
	public static int getNumRoomsInHotel(int hotelId) {
		SearchableOrderedItem item = hotelList.getByKey(""+hotelId);
		if (item == null)
		    return -1;
		//else
		assert item instanceof Hotel;
		return ((Hotel)item).getNumRooms();
	}
	
	//------------------------------------------------------
	// getRoomByHotel
	//
	// PURPOSE:	Returns the specified room of the specified hotel
	// PARAMETERS: 
	//		int: hotel ID
	//		int: room number (one based indexing)
	// Returns:
	//		Room: the requested Room or null if it doesn't exist
	//------------------------------------------------------
	public static Room getRoomByHotel(int hotelId, int roomNumber)
    {
		if (!doesHotelExist(hotelId))
		    return null;
		
		SearchableOrderedItem item  = hotelList.getByKey(""+hotelId);
		assert item instanceof Hotel;
		Hotel hotel = (Hotel)item;
		
		return hotel.getRoomByNumber(roomNumber);
	}

	//------------------------------------------------------
	// doesHotelExist
	//
	// PURPOSE:	check to see if a hotel exists in the database
	// PARAMETERS: 
	//		int: the hotel ID to check
	// Returns: 
	//		boolean: true if the hotel exists, false otherwise
	//------------------------------------------------------
	public static boolean doesHotelExist(int hotelId) { return hotelList.hasKey(""+hotelId); }
	
	//------------------------------------------------------
	// printDatabase
	//
	// PURPOSE:	prints the contents of this database to screen
	// PARAMETERS: none
	// Returns: none
	//------------------------------------------------------
	public static void printDatabase()
    {
		//setup header
		String databaseInfo = "Hotel Data:\n***********\n\n";
		
		//loop over the hotel list and build a String of the required information		
		for (int ii = 0; ii < hotelList.getSize(); ii++){
			databaseInfo += hotelList.getAt(ii).toString() + "\n";			
		}//for ii
		
		System.out.println(databaseInfo);
	}//printDatabase

}//HotelDatabase
