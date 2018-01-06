//-----------------------------------------
// CLASS: HotelFileProcessor
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that reads the hotel file and initializes the hotel database
//
//-----------------------------------------

import java.io.*;

public class HotelFileProcessor
{
	
	//the default hotel file name
	public static final String DEFAULT_HOTEL_FILENAME = "./Hotels.txt";
	
	//------------------------------------------------------
	// processHotelFile 
	//
	// PURPOSE:	reads and parses given hotel file and populates the hotel database
	// PARAMETERS: 
	//		String: path and filename of the hotel data file
	//------------------------------------------------------
	public static void processHotelFile(String filename)
    {
		System.out.println("Processing Hotel file...\nFile name: " + filename);
		
		//open the file and start reading line by line
		try
        {
            FileInputStream fileInStream = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInStream));
            
            //Read until end of file
            String line = reader.readLine();
            while (line != null)
            {
				//ignore extra blank lines
				if (line.trim().length() == 0)
				{
					line = reader.readLine();
					continue; 
				}//if
				
				int hotelId = Integer.parseInt(line); //first line is the hotel ID
				String hotelName = reader.readLine(); //next line is the hotel name
				
				//next we have the list of rooms so read lines until 
				//a blank line is reached or the end of file. Each 
				//room is added to the room list for this hotel.
				SearchableOrderedList roomList = new SearchableOrderedList();
				int roomNumber = 1; //keeps track of the room numbers
				line = reader.readLine();
				while (line != null && line.trim().length() != 0)
                {
					//parse the room info and add the room to the list
					String[] roomInfo = line.split("\\s"); //split the room info into an array
					roomList.insert(new Room(hotelId, roomNumber, roomInfo[0], Integer.parseInt(roomInfo[1])));
					
					//increment the room number
					roomNumber++;
					
					//set the "line" variable for next loop iteration
					line = reader.readLine();	
				}//while
				
				//we are now done reading the rooms so we have all the info we need 
				//to create and add the hotel to the database
				HotelDatabase.addHotel(new Hotel(hotelId, hotelName, roomList));
                
                //set the "line" variable for next loop iteration
                line = reader.readLine();
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
	}//processHotelFile
	
	//------------------------------------------------------
	// processHotelFile 
	//
	// PURPOSE:	convenience method that uses default file name
	// PARAMETERS: none
	// Returns: none
	//------------------------------------------------------
	public static void processHotelFile() { processHotelFile(DEFAULT_HOTEL_FILENAME); }

}//HotelFileProcessor
