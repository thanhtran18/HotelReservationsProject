//-----------------------------------------
// CLASS: CustomerDoesNotExistRequestError
//
// Author: Cong Thanh Tran
//
// REMARKS: error thrown when invalid customer is given in reservation file
//
//-----------------------------------------

public class CustomerDoesNotExistRequestError extends RequestError{

	//------------------------------------------------------
	// CustomerDoesNotExistRequestError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: the request that generated the error as it appears in the reservation file
	//		String: customer name
	// Returns: None
	//------------------------------------------------------
	public CustomerDoesNotExistRequestError(String request, String name) {
		super(request, "Customer name: \"" + name + "\" is invalid");		
	}
	
}//CustomerDoesNotExistRequestError
