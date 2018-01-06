//-----------------------------------------
// CLASS: InvalidObjectTypeError
//
// Author: Cong Thanh Tran
//
// REMARKS: Error created when an invalid instance of an object is passed to a method
//
//-----------------------------------------

public class InvalidObjectTypeError extends FatalError {

	//------------------------------------------------------
	// InvalidObjectTypeError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: method name in which error occurred
	//		String: given type
	//		String: expected type
	// Returns: None
	//------------------------------------------------------
	public InvalidObjectTypeError(String methodName, String givenType, String expectedType) {
		super("Invalid object passed to " + methodName + " - A " + givenType + " was given but a " + expectedType + " was expected.");
		}	

} //InvalidObjectTypeError
