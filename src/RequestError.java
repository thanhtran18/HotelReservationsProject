//-----------------------------------------
// CLASS: RequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: Base class for all request errors in this project
//
//-----------------------------------------

public class RequestError extends Error{

	//------------------------------------------------------
	// RequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file 
	//		String: Error message
	// Returns: None
	//------------------------------------------------------
	public RequestError(String request, String errorMessage) {
		super("When processing: " + request + "\n" + errorMessage);		
	}	
}//RequestError
