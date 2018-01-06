 //-----------------------------------------
 //
 // AUTHOR	: Cong Thanh Tran
 //
 //-----------------------------------------
 
 public class MainProgram{
	 
	//------------------------------------------------------
	// main 
	//
	// PURPOSE:	main method - gets things going
	// PARAMETERS: 
	//		String[]: commandline argument list
	// Returns: none
	//------------------------------------------------------	 
	public static void main(String[] args){
		//This program begins by reading the default hotel file and
		//using its data to setup the hotel database
		HotelFileProcessor.processHotelFile();
		
		//now run through the reservations file
		ReservationFileProcessor.processReservationFile();
		
		//print final results to screen
		System.out.println("\n"); 
		HotelDatabase.printDatabase();
		CustomerDatabase.printDatabase();
		 
		System.out.println("\nEnd of Program\n");
	}//main

 }//MainProgram
