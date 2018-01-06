//-----------------------------------------
// CLASS: DuplicateCustomerDatabaseError
//
// Author: Cong Thanh Tran
//
// REMARKS: Duplicate Customer error in the database
//
//-----------------------------------------

public class DuplicateCustomerDatabaseError extends Error {

	//------------------------------------------------------
	// DuplicateCustomerDatabaseError Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: name of duplicate customer
	// Returns: None
	//------------------------------------------------------
	public DuplicateCustomerDatabaseError(String name) {super("Customer named " + name + " already exists in customer database");}	

} //DuplicateCustomerError
