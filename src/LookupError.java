//-----------------------------------------
// CLASS: LookupError
//
// Author: Cong Thanh Tran
//
// REMARKS: Base class for all database lookup errors in this project
//
//-----------------------------------------

public class LookupError extends FatalError{

	//------------------------------------------------------
	// LookupError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: string representation of method in which error occurred
	//		String: Error message
	// Returns: None
	//------------------------------------------------------
	public LookupError(String method, String errorMessage) {
		super("In DatabaseLookup." + method + ": " + errorMessage);		
	}	
}//LookupError
