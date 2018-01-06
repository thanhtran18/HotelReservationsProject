//-----------------------------------------
// CLASS: Error
//
// Author: Cong Thanh Tran
//
// REMARKS: Base class for all errors in this project
//
//-----------------------------------------

public class Error {

	private String message; //the error message

	//------------------------------------------------------
	// Error Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: error message that will be printed
	// Returns: None
	//------------------------------------------------------
	public Error(String errMsg) {this.message = errMsg;}	
	
	
	//------------------------------------------------------
	// throwError
	//
	// PURPOSE:	prints the error message
	// PARAMETERS: None
	// Returns: none
	//------------------------------------------------------
	public void throwError(){
		System.out.println("An Error Occurred.");
		System.out.println(this);
	}

	//------------------------------------------------------
	// toString
	//
	// PURPOSE:	return a String representation of this object
	// PARAMETERS: None
	// Returns: a String representation of this object
	//------------------------------------------------------
	public String toString(){return message;}

} //Error
