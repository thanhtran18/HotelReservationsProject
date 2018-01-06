//-----------------------------------------
// CLASS: InsufficentAvailableRoomsRequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when there aren't enough available rooms in a hotel to satisfy a request
//
//-----------------------------------------

public class InsufficentAvailableRoomsRequestError extends RequestError{

	//------------------------------------------------------
	// InsufficentAvailableRoomsRequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file
	// Returns: None
	//------------------------------------------------------
	public InsufficentAvailableRoomsRequestError(String request) {
		super(request, "Unable to find enough available rooms");		
	}
	
}//InsufficentAvailableRoomsRequestError
