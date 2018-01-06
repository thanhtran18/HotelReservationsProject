//-----------------------------------------
// CLASS: CustomerDoesNotExistDatabaseError
//
// Author: Cong Thanh Tran
//
// REMARKS: Customer does not exist in database error
//
//-----------------------------------------

public class CustomerDoesNotExistDatabaseError extends Error {

	//------------------------------------------------------
	// CustomerDoesNotExistDatabaseError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: name of non-existing customer
	// Returns: None
	//------------------------------------------------------
	public CustomerDoesNotExistDatabaseError(String name) {super("Customer named " + name + " does not exist in customer database");}	

} //CustomerDoesNotExistError
