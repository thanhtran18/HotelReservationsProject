//-----------------------------------------
// CLASS: ReservationFileProcessor
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that reads the reservation file and modifies the 
//			reservation database data accordingly
//
//-----------------------------------------

import java.io.*;

public class ReservationFileProcessor
{
	
	//the default reservation file name
	public static final String DEFAULT_RESERVATION_FILENAME = "./Bookings.txt";
	
	//need an instance of this class in order to instantiate inner classes 
	//from a static context in Java. In other words, a "this" reference to this
	//class must exist if we are to instantiate Request objects. 
	private static final ReservationFileProcessor self = new ReservationFileProcessor();
	
	//------------------------------------------------------
	// processReservationFile 
	//
	// PURPOSE:	reads and parses given Reservation file and modifies the reservation database 
	//			accordingly. Most of the program logic is either contained here or will be
	//			called from here.
	// PARAMETERS: 
	//		String: path and filename of the reservation data file
	// Returns: none
	//------------------------------------------------------
	public static void processReservationFile(String filename)
    {
		System.out.println("Processing Reservations file...\nFile name: " + filename);
		
		//create the queue of ReservationRequests that will be processed after the
		//input file has been read
		SimpleQueue reservationQueue = new SimpleQueue();		
		
		//open the file and start reading line by line
		try
        {
            FileInputStream fileInStream = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInStream));
            
            //Read until end of file
            String line1 = reader.readLine();
            while (line1 != null)
            {
				//ignore extra blank lines
				if (line1.trim().length() == 0)
				{
					line1 = reader.readLine();
					continue; 
				}//if
				
				//get the next line of reservation data
				String line2 = reader.readLine();
				
				//store the two lines of data as a Request in
				//the reservation queue. Note the special "new" syntax
				//for instantiating an inner class from a static context.
				reservationQueue.enqueue(self.new Request(line1, line2));				
                
                //set the "line" variable for next loop iteration
                line1 = reader.readLine();
            }//while         
         
			//close the file now that we are done reading
			reader.close();
			fileInStream.close();
        }//try 
        catch (Exception ex)
        {
            System.out.println(ex);
            System.exit(1);
        }//catch
        
        //At this point we have read-in the reservation file and stored each
        //request in a queue. We will now process each request in turn.
        while (!reservationQueue.isEmpty())
        {
			//get the next Request from the queue. A simple
			//assertion of the underlying type will suffice.
			ListItem item = reservationQueue.dequeue();
			assert item instanceof Request;
			Request request = (Request)item;
			
			//call appropriate request method based on the request type
			if (request.getRequestType() == RequestType.RESERVATION) processReservationRequest(request);
			else if (request.getRequestType() == RequestType.CANCELLATION) processCancellationRequest(request);
			else{assert 1==0;}//would catch invalid input but we can assume input format is valid			
		}//while
        	
	}//processReservationFile
	
	//------------------------------------------------------
	// processReservationFile 
	//
	// PURPOSE:	convenience method that uses default file name
	// PARAMETERS: none
	// Returns: none
	//------------------------------------------------------
	public static void processReservationFile(){processReservationFile(DEFAULT_RESERVATION_FILENAME);}

	//------------------------------------------------------
	// processReservationRequest
	//
	// PURPOSE:	process a reservation request
	// PARAMETERS:
	//		Request: the reservation request
	// Returns: none
	//------------------------------------------------------
	private static void processReservationRequest(Request request)
    {
		assert request.getRequestType() == RequestType.RESERVATION; //for good measure
		
		//**Start by adding the customer to the database and updating their 
		//room preference. By taking care of customer details first, we can 
		//assume that the customer exists, with up-to-date room preference,
		//when setting up the reservation later.
		processCustomer(request);
		
		//**Here we valiate the request parameters
		
		//validate the hotel id of the request
		if (!HotelDatabase.doesHotelExist(request.getHotelId())){
			//issue an error and stop processing this request
			//because the given hotel ID does not exist
			(new HotelDoesNotExistRequestError(request.getRequestLines(), request.getHotelId())).throwError();
			return;
		}//if
		
		//**From here the program deals with the single room reservation case separately
		//from the the multi-room reservation case
		
		if (request.getNumberOfRooms() == 1) processSingleRoomReservation(request);
		else processMultiRoomReservation(request);		
	}
	
	//------------------------------------------------------
	// processCustomer
	//
	// PURPOSE:	process customer information in the given request: add
	//			the customer if necessary and update their room preference
	//			if necessary.
	// PARAMETERS:
	//		Request: the reservation request
	// Returns: none
	//------------------------------------------------------
	private static void processCustomer(Request request){
		//if customer does not exist then we add them to the database
		if(!CustomerDatabase.doesCustomerExist(request.getCustomerName()))
			CustomerDatabase.addCustomer(new Customer(request.getCustomerName()));
		
		//update the customer's room preference if necessary
		if (request.getPreferredRoomType() != RoomType.UNSPECIFIED)
			CustomerDatabase.setCustomerRoomPreference(request.getCustomerName(), request.getPreferredRoomType());
	}

	//------------------------------------------------------
	// processSingleRoomReservation
	//
	// PURPOSE:	process a reservation request that involves only a single room
	// PARAMETERS:
	//		Request: the reservation request
	// Returns: none
	//------------------------------------------------------
	private static void processSingleRoomReservation(Request request){
		//for good measure
		assert request.getRequestType() == RequestType.RESERVATION; 
		assert request.getNumberOfRooms() == 1;
		
		//**It is assumed that the customer associated with this request is already setup.
		
		//see if the customer has a room preference. if so, get a list of 
		//available rooms of that type, else, get list of all available rooms
		RoomType roomPreference = CustomerDatabase.getCustomerRoomPreference(request.getCustomerName());
		assert roomPreference != null; //check assumption
		OrderedList availableRooms; //available rooms
		if (roomPreference != RoomType.UNSPECIFIED){
			availableRooms = DatabaseLookup.getAvailableRoomsByType(request.getHotelId(), request.getStartDate(), request.getDuration(), roomPreference);
			
			//if no rooms of the preferred type are available then get list of all available rooms
			//at the requested hotel for the date range.
			if (availableRooms.getSize() == 0)
				availableRooms = DatabaseLookup.getAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration());
		}//if
		else{
			//if customer has no room preference then get a list of all available rooms in specified
			//hotel for the given date range
			availableRooms = DatabaseLookup.getAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration());
		}//else
		
		//**At this point we have a list of appropriate available rooms (which could be empty). We must now
		//pick the lowest cost room and create the reservation
		
		//first check if any rooms are actually available. if not, issue an error
		if (availableRooms.getSize() == 0) (new InsufficentAvailableRoomsRequestError(request.getRequestLines())).throwError();
		else{
			//otherwise we are good to create the reservation.
			//start by ordering the list of available rooms by increasing rate.			
			SimpleLinkedList roomsByRate = orderRoomsByRate(availableRooms);
			
			//then create the reservation room list
			ListItem item = roomsByRate.getAt(0); //get the cheapest room
			assert item instanceof Room;			
			SearchableOrderedList reservationRoomList = new SearchableOrderedList();
			reservationRoomList.insert((Room)item); //add the cheapest room to the reservation
			
			//finally, add the reservation to the reservation database.
			addReservationToDatabase(request, reservationRoomList);
		}//else		
	}

	//------------------------------------------------------
	// orderRoomsByRate
	//
	// PURPOSE:	order the rooms in the given list by increasing rate
	// PARAMETERS:
	//		OrderedList: list of rooms to order by rate
	// Returns:
	//		SimpleLinkedList: the room list ordered by rate
	//------------------------------------------------------
	private static SimpleLinkedList orderRoomsByRate(OrderedList roomList){
		SimpleLinkedList roomsByRate = new SimpleLinkedList(); //list to be returned
		
		if (roomList.getSize() > 0){
			//iterate over the rooms in the given list and order them by increasing rate
			roomsByRate.insert(roomList.getAt(0)); //initialize the return list with one room
			for (int iRoom = 1; iRoom < roomList.getSize(); iRoom++){
				OrderedItem item = roomList.getAt(iRoom);
				assert item instanceof Room;
				Room room = (Room)item;
				
				//determine where to insert this room and then do so
				int index = 0;
				while (index < roomsByRate.getSize() && room.getRate() > ((Room)(roomsByRate.getAt(index))).getRate()) index++;
				roomsByRate.insertAt(room, index);				
			}//for iRoom
		}//if
		
		assert roomsByRate.getSize() == roomList.getSize(); //debugging
		
		return roomsByRate;		
	}
		
	//------------------------------------------------------
	// processMultiRoomReservation
	//
	// PURPOSE:	process a reservation request that involves multiple rooms
	// PARAMETERS:
	//		Request: the reservation request
	// Returns: none
	//------------------------------------------------------
	private static void processMultiRoomReservation(Request request){
		//for good measure
		assert request.getRequestType() == RequestType.RESERVATION; 
		assert request.getNumberOfRooms() > 1;
		
		//**It is assumed that the customer associated with this request is already setup.
		
		//Start by getting a set of lists of contiguous rooms that are as large as possible
		int numContiguous = request.getNumberOfRooms(); //number of contiguous rooms
		SimpleLinkedList contiguousRoomListSet = //set of available contiguous room lists
			DatabaseLookup.getContiguousAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration(), numContiguous);
		while (numContiguous > 1 && contiguousRoomListSet.getSize() == 0){
			//if no list of contiguous available rooms could be mustered then
			//try to get a list of one size smaller.
			numContiguous--;
			contiguousRoomListSet =
				DatabaseLookup.getContiguousAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration(), numContiguous);			
		}//while
		
		//**At this point, if no list of contiguous available rooms is available then we have to 
		//get a list of non-contiguous rooms to fill the request. If contiguous rooms are available
		//then we have to pick the cheapest set, along with any other individual rooms, if necessary.
		
		//check if any contiguous rooms are available, if not then proceed with non-contiguous room assignment
		if (contiguousRoomListSet.getSize() == 0){
			//if no set of available contiguous rooms is available, get all available rooms
			OrderedList availableRoomList = DatabaseLookup.getAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration());
			
			//ensure enough rooms are available
			if (availableRoomList.getSize() < request.getNumberOfRooms())
				(new InsufficentAvailableRoomsRequestError(request.getRequestLines())).throwError();
			else{
				//if there are enough non-contiguous available rooms to fill the request then
				//order them by rate.
				SimpleLinkedList roomsByRate = orderRoomsByRate(availableRoomList);
				
				//then create the reservation room list
				SearchableOrderedList reservationRoomList = new SearchableOrderedList();
				for (int iRoom = 0; iRoom < request.getNumberOfRooms(); iRoom++){
					ListItem item = roomsByRate.getAt(iRoom); //get the next cheapest room
					assert item instanceof Room;					
					reservationRoomList.insert((Room)item); //add this room to the reservation
				}//for iRoom
				
				//finally, add the reservation to the reservation database.
				addReservationToDatabase(request, reservationRoomList);
			}//else
		}//if
		else{
			//if at least one list of contiguous rooms is available,
			//then begin by getting the lowest cost list
			OrderedList cheapestRoomList = getCheapestRoomList(contiguousRoomListSet);
			
			//for good measure
			assert cheapestRoomList.getSize() <= request.getNumberOfRooms(); 
			
			//if this list has too few rooms then we have to add some individual ones to it
			if (cheapestRoomList.getSize() < request.getNumberOfRooms()){
				//start by getting all available rooms
				SearchableOrderedList availableRoomList = DatabaseLookup.getAvailableRooms(request.getHotelId(), request.getStartDate(), request.getDuration());
				
				//if enough rooms are available then add them to the room reservation list
				if (availableRoomList.getSize() >= request.getNumberOfRooms()){
					//if there are enough non-contiguous available rooms to fill the request then
					//start by removing from the non-contiguous list the ones that are already in 
					//the contiguous list in order to avoid room duplications in the same reservation
					for (int iRoom = 0; iRoom < cheapestRoomList.getSize(); iRoom++){
						OrderedItem item = cheapestRoomList.getAt(iRoom);
						assert item instanceof Room;
						availableRoomList.removeItem((Room)item);
					}//for iRoom
					
					//order the remaining rooms by increasing rate
					SimpleLinkedList roomsByRate = orderRoomsByRate(availableRoomList);
					
					//fill the reservation room list to the requested number
					for (int ii = cheapestRoomList.getSize(); ii < request.getNumberOfRooms(); ii++){
						cheapestRoomList.insert(availableRoomList.removeAt(0));
					}//for ii					
				}//if
			}//if
			
			//ensure we were able to gather enough rooms before adding this reservation to the database.
			//if not then issue an error.
			if (cheapestRoomList.getSize() < request.getNumberOfRooms())
				(new InsufficentAvailableRoomsRequestError(request.getRequestLines())).throwError();
			else{
				//we have to make a SearchableOrderedList out of our cheapestRoomList
				//before adding it to the database
				SearchableOrderedList reservationRoomList = new SearchableOrderedList();
				for (int ii = 0; ii < cheapestRoomList.getSize(); ii++){
					OrderedItem item = cheapestRoomList.getAt(ii);
					assert item instanceof Room;
					reservationRoomList.insert((Room)item);
				}//for ii
				
				//now add the reservation to the reservation database.
				addReservationToDatabase(request, reservationRoomList);
			}//else			
		}//else
	}
	
	//------------------------------------------------------
	// getCheapestRoomList
	//
	// PURPOSE:	gets the lowest cost room list in a given set of such lists
	// PARAMETERS:
	//		SimpleLinkedList: the set of room lists
	// Returns:
	//		OrderedList: the lowest cost room list, null if the given set is empty
	//------------------------------------------------------
	private static OrderedList getCheapestRoomList(SimpleLinkedList roomListSet){
		if (roomListSet.getSize() == 0) return null;
		
		//loop over the given lists and find the cheapest one
		int cheapestCost = Integer.MAX_VALUE; //initial lowest cost to compare against
		OrderedList cheapestRoomList = null; //reference to cheapest list of rooms
		for (int iList = 0; iList < roomListSet.getSize(); iList++){
			//get the next list in the set and determine its cost
			ListItem itemList = roomListSet.getAt(iList);
			assert itemList instanceof OrderedList;
			OrderedList currentRoomList = (OrderedList)itemList;
			int currentCost = 0;		
			for (int iRoom = 0; iRoom < currentRoomList.getSize(); iRoom++){
				OrderedItem item = currentRoomList.getAt(iRoom);
				assert item instanceof Room;
				currentCost += ((Room)item).getRate();
			}//for iRoom
			
			//now check if the current room is cheaper. if so, update our comparison data
			if (currentCost < cheapestCost){
				cheapestCost = currentCost;
				cheapestRoomList = currentRoomList;
			}//if
		}//for iList
		
		return cheapestRoomList;
	}

	//------------------------------------------------------
	// addReservationToDatabase
	//
	// PURPOSE:	adds a new reservation to the reservation database
	// PARAMETERS:
	//		Request: the reservation request object
	//		SearchableOrderedList: the list of rooms for this reservation
	// Returns: none
	//------------------------------------------------------
	private static void addReservationToDatabase(Request request, SearchableOrderedList roomList){
		//it is assumed that by this point all data in the Request have been validated
		ReservationDatabase.addReservation(new Reservation(
			request.getStartDate(), 
			request.getDuration(), 
			CustomerDatabase.getCustomerByName(request.getCustomerName()), 
			HotelDatabase.getHotelById(request.getHotelId()),
			roomList));
	}
		
	//------------------------------------------------------
	// processCancellationRequest
	//
	// PURPOSE:	process a cancellation request
	// PARAMETERS:
	//		Request: the cancellation request
	// Returns: none
	//------------------------------------------------------
	private static void processCancellationRequest(Request request){
		assert request.getRequestType() == RequestType.CANCELLATION; //for good measure
		
		//do some input validation
		if (!CustomerDatabase.doesCustomerExist(request.getCustomerName())){
			//issue an error because the given customer does not exist
			(new CustomerDoesNotExistRequestError(request.getRequestLines(), request.getCustomerName())).throwError();
		}//if
		else if (!HotelDatabase.doesHotelExist(request.getHotelId())){
			//issue an error because the given hotel ID does not exist
			(new HotelDoesNotExistRequestError(request.getRequestLines(), request.getHotelId())).throwError();
		}//else if
		else{
			//so far so good. Try to remove the reservation from the database
			SimpleLinkedList removedReservations = ReservationDatabase.removeReservation(request.getCustomerName(), request.getHotelId(), request.getStartDate());
			
			//check if any reservations were found. issue an error if not.
			if (removedReservations.getSize() == 0)
				(new NoSuchReservationExistsRequestError(request.getRequestLines())).throwError();
			
			//if at least one reservation was returned in the list then cancellation request was successful and we are done.
		}//else
	}	
	
	//-----------------------------------------
	// CLASS: Inner enum class RequestType 
	//
	// Author: Cong Thanh Tran
	//
	// REMARKS: An enum-type class that manages reservation types
	//-----------------------------------------
	private enum RequestType{RESERVATION, CANCELLATION};	
	
	//-----------------------------------------
	// CLASS: Inner class Request
	//
	// Author: Cong Thanh Tran
	//
	// REMARKS: A class that holds information about a request as it 
	//			appears in the reservation file. 
	//-----------------------------------------
	private class Request extends ListItem
    {
		//data members
		private RequestType requestType;
		private int hotelId;
		private int startDate;
		private int duration;
		private String customerName;
		private RoomType preferredRoomType;
		private int numberOfRooms;
		private String line1;
		private String line2;	
		
		//------------------------------------------------------
		// Request Constructor
		//
		// PURPOSE:	Initializes this object by taking the two lines of input from the 
		//			reservations file and parsing them.
		// PARAMETERS: 
		//		String: first line describing the request
		//		String: second line describing the request
		// Returns: None
		//------------------------------------------------------
		public Request(String line1, String line2){
			this.line1 = line1;
			this.line2 = line2;
			duration = -1; //if we have a cancellation then duration may not be initialized. So we do that here
			
			parseLine1(line1);
			parseLine2(line2);
		}
		
		//------------------------------------------------------
		// parseLine1
		//
		// PURPOSE:	Parses the first line of request input and stores 
		//			the data in this class' private data members
		// PARAMETERS: 
		//		String: the line to parse
		// Returns: None
		//------------------------------------------------------
		private void parseLine1(String line)
        {
			//assumed format as given in assignment 1 document
			String[] tokens = line.split("[\\s:]");
			
			//deal with first token: reserve or cancel
			if (tokens[0].equals("RESERVE")) requestType = RequestType.RESERVATION;
			else if (tokens[0].equals("CANCEL")) requestType = RequestType.CANCELLATION;
			else{assert 1==0;}//would catch invalid input but we can assume input format is valid
			
			//deal with HOTEL, START and DURATION tags
			int index = 1;
			while (index < tokens.length){
				if (tokens[index].equals("HOTEL")){hotelId = Integer.parseInt(tokens[index+1]);}
				if (tokens[index].equals("START")){startDate = Integer.parseInt(tokens[index+1]);}
				if (tokens[index].equals("DURATION")){duration = Integer.parseInt(tokens[index+1]);}				
				index += 2;
			}//while
		}
		
		//------------------------------------------------------
		// parseLine2
		//
		// PURPOSE:	Parses the second line of request input and stores 
		//			the data in this class' private data members
		// PARAMETERS: 
		//		String: the line to parse
		// Returns: None
		//------------------------------------------------------
		private void parseLine2(String line)
        {
			//assumed format as given in assignment 1 document
			String[] tokens = line.split("[\\s,]");
			
			//store the customer name
			customerName = tokens[0] + ", " + tokens[1];
			
			//deal with third token, if given
			if (tokens.length >= 3){
				String token = tokens[2]; //assign to a varible to make code more readable
				if (token.matches("\\d+"))
				{
					//if the token is a number then:
					preferredRoomType = RoomType.UNSPECIFIED;
					numberOfRooms = Integer.parseInt(token);								
				}//if
				else
				    {
					//if the token is not a number then:
					preferredRoomType = RoomType.convertStringToRoomType(token);
					numberOfRooms = 1;					
				}//else
			}//if
			else
            {
				//if the third token was not given, we then set default values
				preferredRoomType = RoomType.UNSPECIFIED;
				numberOfRooms = 1;				
			}//else
		}
		
		//------------------------------------------------------
		// getRequestType
		//
		// PURPOSE:	returns the held request type
		// PARAMETERS: none
		// Returns:
		//		RequestType: the held request type
		//------------------------------------------------------
		public RequestType getRequestType(){return requestType;}
		
		//------------------------------------------------------
		// getHotelId
		//
		// PURPOSE:	returns the held hotel ID
		// PARAMETERS: none
		// Returns:
		//		int: the held hotel ID
		//------------------------------------------------------
		public int getHotelId(){return hotelId;}		
		
		//------------------------------------------------------
		// getStartDate
		//
		// PURPOSE:	returns the held start date
		// PARAMETERS: none
		// Returns:
		//		int: the held start date
		//------------------------------------------------------
		public int getStartDate(){return startDate;}			
		
		//------------------------------------------------------
		// getDuration
		//
		// PURPOSE:	returns the held reservation duration
		// PARAMETERS: none
		// Returns:
		//		int: the held reservation duration
		//------------------------------------------------------
		public int getDuration(){return duration;}			

		//------------------------------------------------------
		// getCustomerName
		//
		// PURPOSE:	returns the held customer name
		// PARAMETERS: none
		// Returns:
		//		String: the held customer name
		//------------------------------------------------------
		public String getCustomerName(){return customerName;}		

		//------------------------------------------------------
		// getPreferredRoomType
		//
		// PURPOSE:	returns the held preferred room type
		// PARAMETERS: none
		// Returns:
		//		RoomType: the held preferred room type
		//------------------------------------------------------
		public RoomType getPreferredRoomType(){return preferredRoomType;}		

		//------------------------------------------------------
		// getNumberOfRooms
		//
		// PURPOSE:	returns the held number of rooms
		// Returns:
		//		int: the held number fo rooms
		//------------------------------------------------------
		public int getNumberOfRooms(){return numberOfRooms;}
		
		//------------------------------------------------------
		// getLine1
		//
		// PURPOSE:	returns the first line from file of this request
		// Returns:
		//		String: the first line from file of this request 
		//------------------------------------------------------
		public String getLine1(){return line1;}	
		
		//------------------------------------------------------
		// getLine2
		//
		// PURPOSE:	returns the second line from file of this request 
		// Returns:
		//		String: the second line from file of this request 
		//------------------------------------------------------
		public String getLine2(){return line2;}			
		
		//------------------------------------------------------
		// getRequestLines
		//
		// PURPOSE:	returns the request lines as they appeared in the file
		// Returns:
		//		String: the request lines as they appeared in the file
		//------------------------------------------------------
		public String getRequestLines(){return line1 + " - " + line2;}	
		
	}//Request
	
}//ReservationFileProcessor
