//-----------------------------------------
// CLASS: InvalidHotelIdLookupError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when invalid hotel ID is given for Database lookup
//
//-----------------------------------------

public class InvalidHotelIdLookupError extends LookupError{

	//------------------------------------------------------
	// InvalidHotelIdLookupError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: string representation of method in which error occurred
	//		int: the invalid hotel ID
	// Returns: None
	//------------------------------------------------------
	public InvalidHotelIdLookupError(String method, int id) {
		super(method, "Hotel ID: " + id + " is invalid");			
	}
	
}//InvalidHotelIdLookupError
