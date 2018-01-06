//-----------------------------------------
// CLASS: Reservation
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains information about hotel reservations.
//			This class only extends OrderedItem and not BaseEntity.
//			This is because, at the moment, a reservation doesn't have any use 
//			for ID or name data.
//
//-----------------------------------------

public class Reservation extends OrderedItem
{
	
	private int startDate; //start date of this reservation
	private int numDays; //number of days for this reservation
	private Customer customer; //the customer this reservation is associated with
	private Hotel hotel; //the hotel this reservation is associated with
	private SearchableOrderedList roomList; //list of rooms booked by this reservation
	
	//------------------------------------------------------
	// Reservation Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		int: reservation start date
	//		int: reservation number of days
	//		Customer: the customer who made this reservation
	//		Hotel: the hotel for this reservation
	//		SearchableOrderedList: list of rooms booked by this reservation
	//------------------------------------------------------
	public Reservation(int startDate, int numDays, Customer customer, Hotel hotel, SearchableOrderedList roomList)
	{
		this.startDate = startDate;
		this.numDays = numDays;
		this.customer = customer;
		this.hotel = hotel;
		this.roomList = roomList;
	}
	
	//------------------------------------------------------
	// compareTo (implements)
	//
	// PURPOSE:	compares two reservations by start date
	// PARAMETERS:
	//		OrderedItem: the OrderedItem to compare to this
	// Returns:
	//		int: a negative integer, zero, or a positive integer if this Reservation starts, respectively, before, on the same date, or after the specified Reservation.
	//------------------------------------------------------
	public int compareTo(OrderedItem other)
    {
		//check type
		int compareResult = 0;
		if (other instanceof Reservation)
		    compareResult = startDate - ((Reservation)other).startDate;
		else
		    (new InvalidObjectTypeError("Reservation.compareTo()", other.getClass().getCanonicalName(), "Reservation")).throwError();
		
		return compareResult;
	}
	
	//------------------------------------------------------
	// isRecordMatch
	//
	// PURPOSE:	determines whether the given parameters match the
	//			data recorded in this reservation. (this method may
	//			be overloaded for various record combinations)
	// PARAMETERS:
	//		String: customer name
	//		int: hotel id
	//		int: start date
	// Returns:
	//		boolean: true if all parameters match this reservation's records, false otherwise.
	//------------------------------------------------------
	public boolean isRecordMatch(String customerName, int hotelId, int startDate)
    {
		boolean isMatch = true;
		
		if (!customerName.equals(customer.getName()))
		    isMatch = false;
		if (hotelId != hotel.getId())
		    isMatch = false;
		if (startDate != this.startDate)
		    isMatch = false;
		
		return isMatch;
	}
	
	//------------------------------------------------------
	// isRecordMatch
	//
	// PURPOSE:	determines whether the given parameters match the
	//			data recorded in this reservation. (this method may
	//			be overloaded for various record combinations)
	// PARAMETERS:
	//		int: hotel id
	//		int: room number (one based indexing)
	// Returns:
	//		boolean: true if all parameters match this reservation's records, false otherwise.
	//------------------------------------------------------
	public boolean isRecordMatch(int hotelId, int roomNumber)
    {
		boolean isMatch = true;
		
		if (hotelId != hotel.getId())
		    isMatch = false;
		if (!roomList.hasKey(""+roomNumber))
		    isMatch = false;
		
		return isMatch;
	}
	
	//------------------------------------------------------
	// isRecordMatch
	//
	// PURPOSE:	determines whether the given parameters match the
	//			data recorded in this reservation. (this method may
	//			be overloaded for various record combinations)
	// PARAMETERS:
	//		String: customer name
	// Returns:
	//		boolean: true if all parameters match this reservation's records, false otherwise.
	//------------------------------------------------------
	public boolean isRecordMatch(String customerName){		
		return customerName.equals(customer.getName());
	}
	
	//------------------------------------------------------
	// getStartDate
	//
	// PURPOSE:	returns the start date of this reservation
	// Returns:
	//		int: the start date of this reservation
	//------------------------------------------------------
	public int getStartDate(){
		return startDate;
	}
	
	//------------------------------------------------------
	// getDuration
	//
	// PURPOSE:	returns the duration of this reservation
	// Returns:
	//		int: the duration of this reservation
	//------------------------------------------------------
	public int getDuration(){
		return numDays;
	}
	
	//------------------------------------------------------
	// getCustomerName
	//
	// PURPOSE:	returns the name of the customer of this reservation
	// PARAMETERS: none
	// Returns:
	//		String: the customer's name
	//------------------------------------------------------
	public String getCustomerName(){
		return customer.getName();
	}
	
	//------------------------------------------------------
	// getTotalCost
	//
	// PURPOSE:	returns the total cost of this reservation
	// PARAMETERS: none
	// Returns:
	//		int: the total cost of this reservation
	//------------------------------------------------------
	public int getTotalCost()
    {
		int totalCost = 0;
		
		//iterate over the rooms of this reservation and sum their individual rates
		for (int iRoom = 0; iRoom < roomList.getSize(); iRoom++)
		{
			SearchableOrderedItem item = roomList.getAt(iRoom);
			assert item instanceof Room;
			totalCost += ((Room)item).getRate();
		}//for iRoom
		
		//return the total costs of the rooms multiplied by the duration of the reservation
		return totalCost * numDays;
	}



}//Reservation
