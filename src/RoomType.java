//-----------------------------------------
// CLASS: RoomType
//
// Author: Cong Thanh Tran
//
// REMARKS: An enum-type class that manages room types in the project Hotels. This centralized
//			class is created so that we can add new types of rooms in the future with ease.
//
//-----------------------------------------	
	
public enum RoomType {
	//create a public set of constants for the types of rooms.
	//the following is the syntax used for enum classes.
	UNSPECIFIED("Unspecified"), 
	DOUBLE("Double"), 
	KING("King"), 
	SUITE("Suite");
	
	//the string version of this RoomType (used in toString)
	private final String strVersion;
	
	//------------------------------------------------------
	// RoomType Constructor
	//
	// PURPOSE:	Initializes this object
	// PARAMETERS: 
	//		String: String version of this RoomType instance
	//------------------------------------------------------
	RoomType(String strVersion){this.strVersion = strVersion;}
	
	//------------------------------------------------------
	// convertStringToRoomType
	//
	// PURPOSE:	converts a given String to a RoomType, ignoring letter case
	// PARAMETERS: 
	//			String: the String denoting a room type
	// Returns:
	//		RoomType: the resulting room type as a RoomType.
	//------------------------------------------------------
	public static RoomType convertStringToRoomType(String typeStr){
		//note that if the given string is not recognized as a valid room type
		//then the default room type is returned: UNSPECIFIED
		if (typeStr.equalsIgnoreCase("double"))
			return RoomType.DOUBLE;
		else if (typeStr.equalsIgnoreCase("king"))
		    return RoomType.KING;
		else if (typeStr.equalsIgnoreCase("suite"))
		    return RoomType.SUITE;
		else
		    return RoomType.UNSPECIFIED; //The default value if the room String is invalid
	}
	
	//------------------------------------------------------
	// toString (overrides)
	// Returns:
	//		String: the String version of this RoomType instance
	//------------------------------------------------------
	public String toString(){return strVersion;}
	
}
