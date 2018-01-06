//-----------------------------------------
// CLASS: HotelDoesNotExistRequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when invalid hotel ID is given in reservation file
//
//-----------------------------------------

public class HotelDoesNotExistRequestError extends RequestError{

	//------------------------------------------------------
	// HotelDoesNotExistRequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file
	//		int: hotel ID
	// Returns: None
	//------------------------------------------------------
	public HotelDoesNotExistRequestError(String request, int id) {
		super(request, "Hotel ID: " + id + " is invalid");		
	}
	
}//HotelDoesNotExistRequestError
