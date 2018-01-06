//-----------------------------------------
// CLASS: InvalidStartDateRequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when invalid start date is given in reservation file
//
//-----------------------------------------

public class InvalidStartDateRequestError extends RequestError{

	//------------------------------------------------------
	// InvalidStartDateRequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file
	//		int: reservation start date
	// Returns: None
	//------------------------------------------------------
	public InvalidStartDateRequestError(String request, int id) {
		super(request, "Reservation start date: " + id + " is invalid");		
	}
	
}//InvalidStartDateRequestError
