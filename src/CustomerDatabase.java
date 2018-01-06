//-----------------------------------------
// CLASS: CustomerDatabase
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that manages customer data
//
//-----------------------------------------

public class CustomerDatabase {
	
	//the list of Customers
	private static SearchableOrderedList customerList = new SearchableOrderedList();
	
	//------------------------------------------------------
	// addCustomer
	//
	// PURPOSE:	add a Customer to this database
	// PARAMETERS: 
	//		Customer: the customer to be added to the database
	// Returns: none
	//------------------------------------------------------
	public static void addCustomer(Customer customer){
		//check if this customer already exists in the list.
		//we don't allow duplicate customers.
		if (doesCustomerExist(customer)){(new DuplicateCustomerDatabaseError(customer.getName())).throwError();}
		else customerList.insert(customer);
	}
	
	//------------------------------------------------------
	// getCustomerByName
	//
	// PURPOSE:	gets a Customer stored in this database by its name
	// PARAMETERS: 
	//		String: the name of the customer to get
	// Returns:
	//		Customer: the requested Customer object or null if not found
	//------------------------------------------------------
	public static Customer getCustomerByName(String customerName){
		SearchableOrderedItem item = customerList.getByKey(customerName);
		if (item == null) return null;
		//else
		assert item instanceof Customer;
		return (Customer)item;
	}
	
	//------------------------------------------------------
	// doesCustomerExist
	//
	// PURPOSE:	check to see if customer already exists in database
	// PARAMETERS: 
	//		Customer: the customer to check for
	// Returns: 
	//		boolean: true if the customer alreacy exists, false otherwise
	//------------------------------------------------------
	public static boolean doesCustomerExist(Customer customer){return customerList.hasItem(customer);}
	
	//------------------------------------------------------
	// doesCustomerExist
	//
	// PURPOSE:	check to see if customer already exists in database
	// PARAMETERS: 
	//		String: the customer to check for by name
	// Returns: 
	//		boolean: true if the customer alreacy exists, false otherwise
	//------------------------------------------------------
	public static boolean doesCustomerExist(String name){return customerList.hasKey(name);}

	//------------------------------------------------------
	// setCustomerRoomPreference
	//
	// PURPOSE:	set a customer's room preference
	// PARAMETERS: 
	//		String: the customer's name
	//		RoomType: the RoomType to set the preference to
	// Returns: none
	//------------------------------------------------------
	public static void setCustomerRoomPreference(String name, RoomType roomType){
		//first check if this customer exists in the database
		if (!doesCustomerExist(name)) (new CustomerDoesNotExistDatabaseError(name)).throwError();
		else{
			//if customer exists then change their room preference
			SearchableOrderedItem item = customerList.getByKey(name);
			assert item instanceof Customer;
			((Customer)item).setPreferredRoomType(roomType);
		}//else
	}
	
	//------------------------------------------------------
	// getCustomerRoomPreference
	//
	// PURPOSE:	get a customer's room preference
	// PARAMETERS: 
	//		String: the customer's name
	// Returns:
	//		RoomType: the customer's preferred room type or null if customer name invalid
	//------------------------------------------------------
	public static RoomType getCustomerRoomPreference(String name){
		//The return value
		RoomType prefRoomType = null;
		
		//first check if this customer exists in the database
		if (!doesCustomerExist(name)) (new CustomerDoesNotExistDatabaseError(name)).throwError();
		else{
			//if customer exists then get their room preference
			SearchableOrderedItem item = customerList.getByKey(name);
			assert item instanceof Customer;
			prefRoomType = ((Customer)item).getPreferredRoomType();
		}//else
		
		return prefRoomType;
	}
		
	//------------------------------------------------------
	// printDatabase
	//
	// PURPOSE:	prints the contents of this database to screen
	// PARAMETERS: none
	// Returns: none
	//------------------------------------------------------
	public static void printDatabase(){
		//setup header
		String databaseInfo = "Customer Data:\n**************\n\n";
		
		//loop over the customer list and build a String of the required information
		for (int ii = 0; ii < customerList.getSize(); ii++){
			databaseInfo += customerList.getAt(ii).toString() + "\n";			
		}//for ii
		
		System.out.println(databaseInfo);
	}//printDatabase

}//CustomerDatabase
