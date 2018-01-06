//-----------------------------------------
// CLASS: FatalError
//
// Author: Cong Thanh Tran
//
// REMARKS: Base class for errors that should terminate the program
//
//-----------------------------------------

public class FatalError extends Error {

	//------------------------------------------------------
	// FatalError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: error message that will be printed
	// Returns: None
	//------------------------------------------------------
	public FatalError(String errMsg) {super(errMsg);}	
	
	
	//------------------------------------------------------
	// throwError (overrides)
	//
	// PURPOSE:	prints the error message and exits the program
	// PARAMETERS: None
	// Returns: none
	//------------------------------------------------------
	public void throwError(){
		super.throwError();
		System.out.println("Cannot recover. Exiting program...");
		System.exit(1); //exit with non-zero error code
	}

} //FatalError
