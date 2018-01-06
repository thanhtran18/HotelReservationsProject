//-----------------------------------------
// CLASS: BaseEntity
//
// Author: Gabriel Faucher
//
// REMARKS: A base class grouping what is common in the data classes of this project  
//
//-----------------------------------------

public class BaseEntity extends SearchableOrderedItem {

	//default integer and string identifiers of this entity
	private int id;
	private String name;
	
	//------------------------------------------------------
	// BaseEntity Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		int: default integer identifier for this entity
	//		String: default String identifier for this entity
	// Returns: None
	//------------------------------------------------------
	BaseEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	//------------------------------------------------------
	// compareTo (implements)
	//
	// PURPOSE:	compares the "id" members of two BaseEntitys by calling "getId()".
	//			Provides default implementation of a required method.
	//			This method can be overriden in a subclass as necessary.
	// PARAMETERS:
	//		OrderedItem: the OrderedItem to compare to this
	// Returns:
	//		int: a negative integer, zero, or a positive integer if this object is, respectively, less than, equal to, or greater than the specified object.
	//------------------------------------------------------
	public int compareTo(OrderedItem other){
		//check type
		int compareResult = 0;
		if (other instanceof BaseEntity) compareResult = getId() - ((BaseEntity)other).getId();
		else (new InvalidObjectTypeError("BaseEntity.compareTo()", other.getClass().getCanonicalName(), "BaseEntity")).throwError();
		
		return compareResult;
	}
	
	//------------------------------------------------------
	// getId
	//
	// PURPOSE:	returns the id field for this object. 
	// PARAMETERS: none
	// Returns:
	//		int: the id field for this object
	//------------------------------------------------------
	public int getId() {return id;}
	
	//------------------------------------------------------
	// getKey (implements)
	//
	// PURPOSE:	returns the name value for this object by calling "getName()".
	//			Provides default implementation of a required method.
	//			This method can be overriden in a subclass as necessary.
	// PARAMETERS: none
	// Returns:
	//		String: the key value (name field) for this object
	//------------------------------------------------------
	public String getKey() {return getName();}
	
	//------------------------------------------------------
	// getName
	//
	// PURPOSE:	returns the name field for this object. 
	// PARAMETERS: none
	// Returns:
	//		String: the name field for this object
	//------------------------------------------------------
	public String getName() {return name;}	

} //BaseEntity
