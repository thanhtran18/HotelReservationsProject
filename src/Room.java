//-----------------------------------------
// CLASS: Room
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains information about individual Rooms in the project Hotels
//
//-----------------------------------------

public class Room extends BaseEntity
{
	
	//Room-specific data members - other data handled by superclass
	private RoomType roomType; //the room type
	private int hotelId;
	private int rate; //cost per night of this room
	
	//------------------------------------------------------
	// Room Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS:
	//		int: hotel ID of the hotel that this room belongs to
	//		int: room number
	//		String: room type (double, king or suite)
	//		int: cost per night of this room
	//------------------------------------------------------
	Room(int hotelId, int roomNumber, String type, int rate)
    {
		super(roomNumber, (""+roomNumber)); //set the name (search key) of this room to the room number
		this.hotelId = hotelId;
		this.rate = rate;		
		roomType = RoomType.convertStringToRoomType(type);
	}
	
	//------------------------------------------------------
	// getRate
	//
	// PURPOSE:	returns the rate for this room
	// Returns:
	//		int: the rate for this room
	//------------------------------------------------------
	public int getRate() { return rate; }

	//------------------------------------------------------
	// getRoomType
	//
	// PURPOSE:	returns the type of this room
	// Returns:
	//		RoomType: the type of this room
	//------------------------------------------------------
	public RoomType getRoomType() { return roomType; }

	//------------------------------------------------------
	// toString (overrides)
	//
	// PURPOSE:	returns a string representation of this object
	// Returns:
	//		String: this room's number, type, rate and reservation information
	//------------------------------------------------------
	public String toString()
    {
		//add room header info to the return String
		String roomInfo = "Room " + getId() + " - " + roomType + "  rate: $" + rate + "\n";
		
		//get the reservation list for this room and add this info
		//to the return string
		OrderedList reservationList = ReservationDatabase.getReservationsByRoomNumber(hotelId, getId());
		if (reservationList.getSize() == 0)
		    roomInfo += "Reservations: none\n";
		else
        {
			roomInfo += "Reservations:\n";
			
			//add reservation info to the string
			for (int iReservation = 0; iReservation < reservationList.getSize(); iReservation++)
			{
				OrderedItem item = reservationList.getAt(iReservation);
				assert item instanceof Reservation;
				Reservation reservation = (Reservation)item;
				roomInfo += "    Start: " + reservation.getStartDate() + "  Duration: " + reservation.getDuration() + "  Customer: " + reservation.getCustomerName() + "\n";
			}//for iReservation
			
		}//else
		
		return roomInfo;
	}//toString

}
