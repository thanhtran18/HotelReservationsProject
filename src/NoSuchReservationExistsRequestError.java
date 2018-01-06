//-----------------------------------------
// CLASS: NoSuchReservationExistsRequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when the specified reservation is not found
//
//-----------------------------------------

public class NoSuchReservationExistsRequestError extends RequestError{

	//------------------------------------------------------
	// NoSuchReservationExistsRequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file
	// Returns: None
	//------------------------------------------------------
	public NoSuchReservationExistsRequestError(String request) {
		super(request, "No such reservation exists");		
	}
	
}//NoSuchReservationExistsRequestError
