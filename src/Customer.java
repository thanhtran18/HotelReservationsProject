//-----------------------------------------
// CLASS: Customer
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains information about individual Customers in the project
//
//-----------------------------------------

public class Customer extends BaseEntity {
	
	//Customer-specific data members - other data handled by superclass
	private RoomType roomPreference; //this customer's preferred room type (double, king or suite)
	private static int nextCustomerIdNumber = 1; //customer ID numbers - incremented each time a Customer is created
	
	//------------------------------------------------------
	// Customer Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS:
	//		String: customer name	
	// Returns: None
	//------------------------------------------------------
	Customer(String customerName) {
		super(nextCustomerIdNumber, customerName);
		nextCustomerIdNumber++;
		roomPreference = RoomType.UNSPECIFIED;
	}
	
	//------------------------------------------------------
	// compareTo (overrides)
	//
	// PURPOSE:	compares two customers. We override the default functionality available in BaseEntity because
	//			we want to compare customer names when ordering them, rather than their IDs.
	// PARAMETERS:
	//		OrderedItem: the OrderedItem to compare to this
	// Returns:
	//		int: a negative integer, zero, or a positive integer if this object is, respectively, less than, equal to, or greater than the specified object.
	//------------------------------------------------------
	public int compareTo(OrderedItem other)
    {
		//check type
		int compareResult = 0;
		if (other instanceof Customer)
			compareResult = getName().compareTo(((Customer)other).getName());
		else
		    (new InvalidObjectTypeError("Customer.compareTo()", other.getClass().getCanonicalName(), "Customer")).throwError();
		
		return compareResult;
	}
	
	//------------------------------------------------------
	// setPreferredRoomType
	//
	// PURPOSE:	sets this customer's preferred room type
	// PARAMETERS:
	//		RoomType: the desired room type
	// Returns: none
	//------------------------------------------------------
	public void setPreferredRoomType(RoomType type) { roomPreference = type; }
	
	//------------------------------------------------------
	// getPreferredRoomType
	//
	// PURPOSE:	gets this customer's preferred room type
	// PARAMETERS: none
	// Returns:
	//		RoomType: the preferred room type
	//------------------------------------------------------
	public RoomType getPreferredRoomType() { return roomPreference; }
	
	//------------------------------------------------------
	// toString (overrides)
	//
	// PURPOSE:	returns a string representation of this object
	// PARAMETERS: none
	// Returns:
	//		String: this customer's name, preferred room type and reservation information
	//------------------------------------------------------
	public String toString(){
		//add customer header info to the return String
		String customerInfo = getName() + " - Room Preference: " + getPreferredRoomType() + "\n";
		
		//get the reservation list for this customer and add this info
		//to the return string
		OrderedList reservationList = ReservationDatabase.getReservationsByCustomerName(getName());		
		if (reservationList.getSize() == 0)
		    customerInfo += "Reservations: none\n";
		else
		    {
			customerInfo += "Reservations:\n";
			
			//add reservation info to the string
			for (int iReservation = 0; iReservation < reservationList.getSize(); iReservation++)
			{
				OrderedItem item = reservationList.getAt(iReservation);
				assert item instanceof Reservation;
				Reservation reservation = (Reservation)item;
				customerInfo += "    Start: " + reservation.getStartDate() + "  Duration: " + reservation.getDuration() + "  Cost: $" + reservation.getTotalCost() + "\n";
			}//for iReservation
			
		}//else
		
		return customerInfo;
	}//toString

}
