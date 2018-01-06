//-----------------------------------------
// CLASS: DatabaseLookup
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that groups static methods for looking-up and filtering
//			through information contained in the databases
//
//-----------------------------------------

public class DatabaseLookup {
	
	//------------------------------------------------------
	// getAvailableRooms
	//
	// PURPOSE:	get a list of available rooms in a hotel for the
	//			given date range
	// PARAMETERS: 
	//		int: the hotel ID
	//		int: start date
	//		int: duration
	// Returns: 
	//		SearchableOrderedList: list of available rooms in the given date range
	//------------------------------------------------------
	public static SearchableOrderedList getAvailableRooms(int hotelId, int startDate, int duration){
		SearchableOrderedList availableRooms = new SearchableOrderedList(); //the list of rooms to return
		
		//ensure the hotel exists
		if (!HotelDatabase.doesHotelExist(hotelId))
			(new InvalidHotelIdLookupError("getAvailableRooms(int,int,int)", hotelId)).throwError();
		
		//loop over the hotel rooms, checking if each is available
		int numRooms = HotelDatabase.getNumRoomsInHotel(hotelId);
		for (int iRoomNumber = 1; iRoomNumber <= numRooms; iRoomNumber++){ //note one-based indexing for room number
			//get current reservations for this room
			OrderedList reservationList = ReservationDatabase.getReservationsByRoomNumber(hotelId, iRoomNumber);
			
			//iterate over the reservations to determine whether this room is available
			boolean isRoomAvailable = true;
			int index = 0;
			while (index < reservationList.getSize() && isRoomAvailable){
				OrderedItem item = reservationList.getAt(index);
				assert item instanceof Reservation;
				Reservation reservation = (Reservation)item;
				
				//if given dates and reservation dates overlap then this room is not available
				if (isDateOverlap(startDate, duration, reservation.getStartDate(), reservation.getDuration())) isRoomAvailable = false;
				
				index++;
			}//while
			
			//add this room to the list if it is available
			if (isRoomAvailable) availableRooms.insert(HotelDatabase.getRoomByHotel(hotelId, iRoomNumber));
			
		}//for iRoomNumber
		
		return availableRooms;		
	}

	//------------------------------------------------------
	// getAvailableRoomsByType
	//
	// PURPOSE:	get a list of available rooms of a specified type in a hotel for the
	//			given date range
	// PARAMETERS: 
	//		int: the hotel ID
	//		int: start date
	//		int: duration
	//		RoomType: the type of room
	// Returns: 
	//		OrderedList: list of available rooms of the specified type in the given date range
	//------------------------------------------------------
	public static OrderedList getAvailableRoomsByType(int hotelId, int startDate, int duration, RoomType roomType){
		OrderedList availableRooms = getAvailableRooms(hotelId, startDate, duration); //the list of rooms to return
		
		//iterate over the available rooms in the list, removing the ones that do not 
		//match the specified type
		int index = 0;
		while (index < availableRooms.getSize()){
			OrderedItem item = availableRooms.getAt(index);
			assert item instanceof Room;
			Room room = (Room)item;
			
			if (room.getRoomType() != roomType) availableRooms.removeAt(index);
			else index++;			
		}//while
		
		return availableRooms;
	}
	
	//------------------------------------------------------
	// getContiguousAvailableRooms
	//
	// PURPOSE:	get a list of lists of available rooms that are contiguous in the
	//			specified hotel for the givne date range. The number of contiguous
	//			rooms is also a parameter.
	// PARAMETERS: 
	//		int: the hotel ID
	//		int: start date
	//		int: duration
	//		int: number of contiguous rooms
	// Returns: 
	//		SimpleLinkedList: list of lists of contiguous available rooms in the given date range
	//------------------------------------------------------
	public static SimpleLinkedList getContiguousAvailableRooms(int hotelId, int startDate, int duration, int numContiguous){
		SimpleLinkedList contiguousRoomsList = new SimpleLinkedList(); //the list of lists of contiguous rooms to return
		
		//start by getting the available rooms at this hotel for the given date range.
		OrderedList availableRooms = getAvailableRooms(hotelId, startDate, duration);
		
		//Since the rooms are ordered by room number in this list, we can compare rooms "numContiguous"
		//items away from each other. so iterate over the available room list and compare rooms as such
		int offset = numContiguous - 1; //the desired offset between compared rooms
		for (int iRoom = 0; iRoom+offset < availableRooms.getSize(); iRoom++){
			//get a room and antoher "offset" items away in the list
			OrderedItem roomA = availableRooms.getAt(iRoom);
			OrderedItem roomB = availableRooms.getAt(iRoom+offset);
			
			//check if the room numbers are contiguous. If so, add them to the 
			//list to be returned
			if (roomB.compareTo(roomA) == offset){
				//create the list of contiguous rooms to put into the retured list.
				SearchableOrderedList contiguousRooms = new SearchableOrderedList(); //the list of contiguous rooms
				for (int jRoom = iRoom; jRoom < iRoom+numContiguous; jRoom++)
					contiguousRooms.insert(availableRooms.getAt(jRoom));
				
				contiguousRoomsList.insert(contiguousRooms);				
			}//if	
		}//for iRoom
		
		return contiguousRoomsList;		
	}
	
	//------------------------------------------------------
	// isDateOverlap
	//
	// PURPOSE: determine whether the given date ranges overlap
	// PARAMETERS: 
	//		int: start date 1
	//		int: duration 1
	//		int: start date 2
	//		int: duration 2
	// Returns: 
	//		boolean: true if dates overlap, false otherwise
	//------------------------------------------------------
	private static boolean isDateOverlap(int startDate1, int duration1, int startDate2, int duration2){
		boolean isOverlap = false;		
		int endDate1 = startDate1 + duration1 - 1;
		int endDate2 = startDate2 + duration2 - 1;
		
		isOverlap = isOverlap || (startDate1 <= startDate2 && startDate2 <= endDate1);
		isOverlap = isOverlap || (startDate1 <= endDate2 && endDate2 <= endDate1);
		isOverlap = isOverlap || (startDate2 <= startDate1 && startDate1 <= endDate2);
		isOverlap = isOverlap || (startDate2 <= endDate1 && endDate1 <= endDate2);
		
		return isOverlap;
	}

}//DatabaseLookup
