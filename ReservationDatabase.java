//-----------------------------------------
// CLASS: ReservationDatabase
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that manages reservation data
//
//-----------------------------------------

public class ReservationDatabase
{

	//the list of Reservations
	private static OrderedList reservationList = new OrderedList();
	
	//------------------------------------------------------
	// addReservation
	//
	// PURPOSE:	add a Reservation to this database
	// PARAMETERS: 
	//		Reservation: the Reservation to be added to the database
	//------------------------------------------------------
	public static void addReservation(Reservation reservation){
		//note: it is assumed we can add "duplicate" reservations to the list.
		reservationList.insert(reservation);
	}
	
	//------------------------------------------------------
	// removeReservation
	//
	// PURPOSE:	remove a Reservation from this database. The particular 
	//			reservation(s) is(are) indentified using the given parameters.
	// PARAMETERS: 
	//		String: customer name
	//		int: hotel id
	//		int: start date
	// Returns: 
	//		SimpleLinkedList: the list of removed reservations
	//------------------------------------------------------
	public static SimpleLinkedList removeReservation(String customerName, int hotelId, int startDate){
		//list to return
		SimpleLinkedList removedReservations = new SimpleLinkedList();
		
		int index = 0;
		while (index < reservationList.getSize())
        {
			//get each reservation from the list and see if
			//we have a match
			OrderedItem item = reservationList.getAt(index);
			assert item instanceof Reservation;
			Reservation reservation = (Reservation)item;
			
			//remove and return it if it matches the record criteria
			if (reservation.isRecordMatch(customerName, hotelId, startDate)){
				removedReservations.insert(reservationList.removeAt(index));
			}//if
			else index++;
		}//while
		
		return removedReservations;
	}
	
	//------------------------------------------------------
	// getReservationsByRoomNumber
	//
	// PURPOSE:	get a list of reservations for a certain room number
	// PARAMETERS: 
	//		int: ID of the hotel
	//		int: room number (one based indexing)
	// Returns:
	//		OrderedList: the list of reservations for this room
	//------------------------------------------------------
	public static OrderedList getReservationsByRoomNumber(int hotelId, int roomNumber){
		//list to return
		OrderedList reservationForRoom = new OrderedList();
		
		//iterate over each reservation and see if the room is reserved
		for (int ii = 0; ii < reservationList.getSize(); ii++)
		{
			OrderedItem item = reservationList.getAt(ii);
			assert item instanceof Reservation;
			
			//see if this reservation includes the given room.
			//if so, add to return list
			if (((Reservation)item).isRecordMatch(hotelId, roomNumber))
			    reservationForRoom.insert(item);
		}//for ii
		
		return reservationForRoom;
	}
	
	//------------------------------------------------------
	// getReservationsByCustomerName
	//
	// PURPOSE:	get a list of reservations for a certain customer by name
	// PARAMETERS: 
	//		String: customer name
	// Returns:
	//		OrderedList: the list of reservations for this room
	//------------------------------------------------------
	public static OrderedList getReservationsByCustomerName(String customerName)
    {
		//list to return
		OrderedList reservationsForCustomer = new OrderedList();
		
		//iterate over each reservation and see which are for the given customer
		for (int ii = 0; ii < reservationList.getSize(); ii++)
		{
			OrderedItem item = reservationList.getAt(ii);
			assert item instanceof Reservation;
			
			//see if this reservation includes the given customer.
			//if so, add to return list
			if (((Reservation)item).isRecordMatch(customerName)) reservationsForCustomer.insert(item);
		}//for ii
		
		return reservationsForCustomer;
	}

}//ReservationDatabase
