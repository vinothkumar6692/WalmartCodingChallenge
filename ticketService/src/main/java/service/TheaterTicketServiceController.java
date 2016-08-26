package service;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.log4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import model.Level;
import model.SeatHold;
import dao.TicketServiceDAO;
import dao.HoldManagerDAO;
import java.sql.Date;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheaterTicketServiceController {
	public static void main(String args[]) throws InterruptedException{
		System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
		Logger logger = (Logger) LoggerFactory.getLogger("TheaterTicketService");
		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		    "applicationContext.xml");
		TicketServiceUtils controllerUtil = (TicketServiceUtils) ctx.getBean("utilbean");
		TicketServiceImpl ticketServiceController = (TicketServiceImpl) ctx.getBean("tdao");

		Scanner scanner = new Scanner(System.in);
        int input;
		String userChoice = "y";
		System.out.println("\n\n\t\t\t******  Welcome to the Theater Ticket Service ******\n");
		
		while (true) {
				
				System.out.println("\n\n\t\t==============================================================");
			    System.out.println("\t\t|   Please Select one of the Options Below                   |");
			    System.out.println("\t\t==============================================================");
			    System.out.println("\t\t|                                                            |");
			    System.out.println("\t\t|1. View all Levels in the theater                           |");
			    System.out.println("\t\t|2. Find the number of seats available in the entire Theater |");
	            System.out.println("\t\t|3. Find the number of seats available with a specific level |");
	            System.out.println("\t\t|4. Find and hold the best seats in the theater              |");
	            System.out.println("\t\t|5. Hold and Reserve the best seats in the theater           |");
	            System.out.println("\t\t|6. Reserve tickets                                          |");
	            System.out.println("\t\t|7. Hold Seats, wait for 60 seconds and Reserve              |");
	            System.out.println("\t\t|8. Exit                                                     |");
			    System.out.println("\t\t==============================================================");
			    System.out.println("\n\t\tEnter your Option: ");
			

	            try{
	                input = scanner.nextInt();
	            }
	            catch (InputMismatchException e){
	                System.out.println("\t\tERROR: Please enter an integer for Option..");
	                scanner.nextLine();
	                continue;
	            }
	            
	            switch(input){
	            case 1:{
	            	List<Level> levels = controllerUtil.getAllLevels();
	            	controllerUtil.displayLevels(levels);
	            	break;
	            }
	            case 2:
	            	{
	            		Integer value = null;
	            		Optional<Integer> levelInput = Optional.ofNullable(value);
	            		int noOfSeats = ticketServiceController.numSeatsAvailable(levelInput);
	            		if(noOfSeats==-1){
	            			System.out.println("\t\tERROR: Invalid Level. Please try again with a valid Level id");
	            			break;
	            		}
	            		System.out.println("\n\n\t\tTotal Number of Available Seats in the Theater: "+noOfSeats);
	            		break;
	            	}
	            	
	            case 3:{       	
	            	System.out.println("\t\tEnter the Level Id: ");	            	
	            	Integer level;
	            	try{
	            		level = scanner.nextInt();
		            }
		            catch (InputMismatchException e){
		                System.out.println("\t\tERROR: Please enter an integer value for Level");
		                break;
		            }	            	
	            	Optional<Integer> levelInput = Optional.ofNullable(level);
	            	int noOfSeats = ticketServiceController.numSeatsAvailable(levelInput);
	            	if(noOfSeats==-1){
            			System.out.println("\t\tERROR: Invalid Level. Please try again with a valid Level id");
            			break;
            		}
            		System.out.println("\n\n\t\tTotal Number of Available Seats in the Level "+level+": "+ noOfSeats);
	            	break;
	            }
	            
	            case 4:{
	            	Integer noOfSeats,minLevel,maxLevel;
	            	int availableLevels = controllerUtil.getNumberOfLevels();
	            	String emailId;
	            	scanner.nextLine();
	            	System.out.println("\t\tEnter your email id: ");            	
	            	try{
	            		emailId = scanner.nextLine(); 
		            }
		            catch (InputMismatchException e){
		                System.out.println("\t\tERROR: Please enter a valid email Id.");
		                scanner.nextLine();
		                continue;
		            }        	
	            	System.out.println("\t\tEnter the number of seats to hold: ");	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tERROR: Please enter an integer value.");
                        break;         
                    }
	            	
	            	System.out.println("\t\tEnter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tERROR: Please enter an integer value.");
                        break;         
                    }
	            	if(minLevel>availableLevels){
	            		 System.out.println("\t\tERROR: Please enter an valid Level Range. Choose from 1 to "+availableLevels);
	            		 break;
	            	}
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("\t\tEnter the maximum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tPlease enter an integer value.");
                        break;         
                    }
	            	if(maxLevel>availableLevels){
	            		 System.out.println("\t\tERROR: Please enter an valid Level Range. Choose from 1 to "+availableLevels);
	            		 break;
	            	}
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);   
	            	
	            	/* Verify Ticket Availablity in Specified Levels*/
	            	
	            	Integer availableTicketsinLevel=0;
	            	for(int level=minLevel;level<=maxLevel;level++){
	            		Optional<Integer> levelInput = Optional.ofNullable(level);
	            		availableTicketsinLevel+=ticketServiceController.numSeatsAvailable(levelInput);
	            	}
	            	if(availableTicketsinLevel<noOfSeats){
	            		System.out.println("\n\n\t\tERROR: Requested number of seats in not available");
	            		System.out.println("\n\n\t\tNumber of Available Seats in specified level:"+availableTicketsinLevel);
	            		break;      		
	            	}
	            	
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	if(seatHold==null){
	            		System.out.println("\t\tERROR: Unable to Hold Tickets. Please Try again later.");
	            	}
	            	else{
	            		controllerUtil.displaySeatsInformation(seatHold);
	            		System.out.println("\t\tSeat Hold Successful!!");
	            	}
	            	break;
	            }
	            case 5:{
	            	Integer noOfSeats,minLevel,maxLevel;
	            	String emailId;
	            	int availableLevels = controllerUtil.getNumberOfLevels();	
	            	scanner.nextLine();
	            	String userRequestChoice;
	            	System.out.println("\t\tEnter your email id: ");	            	
	            	emailId = scanner.nextLine();	            	
	            	System.out.println("\t\tEnter the number of seats to hold: ");	            	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tERROR: Please enter an integer value.");
                        break;         
                    }
	            	System.out.println("\t\tEnter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tERROR: Please enter an integer value.");
                        break;         
                    }
	            	if(minLevel>availableLevels){
	            		 System.out.println("\t\tERROR: Please enter an valid Level Range. Choose from 1 to "+availableLevels);
	            	}
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("\t\tEnter the maximum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tERROR: Please enter an integer value.");
                        break;         
                    }
	            	if(maxLevel>availableLevels){
	            		 System.out.println("\t\tERROR: Please enter an valid Level Range. Choose from 1 to "+availableLevels);
	            		 break;
	            	}
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);
	            	/* Verify Ticket Availablity in Specified Levels*/
	            	
	            	Integer availableTicketsinLevel=0;
	            	for(int level=minLevel;level<=maxLevel;level++){
	            		Optional<Integer> levelInput = Optional.ofNullable(level);
	            		availableTicketsinLevel+=ticketServiceController.numSeatsAvailable(levelInput);
	            	}
	            	if(availableTicketsinLevel<noOfSeats){
	            		System.out.println("\n\n\t\tERROR: Requested number of seats in not available");
	            		System.out.println("\n\n\t\tNumber of Available Seats in specified level:"+availableTicketsinLevel);
	            		break;      		
	            	}
	            	
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	
	            	if(seatHold!=null){
		            		controllerUtil.displaySeatsInformation(seatHold);
		            		System.out.println("Ticket Hold Succesful. Please see above for seat information");
		            		System.out.println("Would you like to reserve the tickets that are on hold for you? (Press y/n)");
		            		scanner.nextLine();
		            		userRequestChoice = scanner.nextLine();
		            		System.out.println("Choice:"+userRequestChoice);
		            		if(userRequestChoice.toLowerCase().equals("y")){
		            			String reserveResponse = ticketServiceController.reserveSeats(seatHold.getSeatHoldid(),seatHold.getCustomerEmail());
		            			if(reserveResponse!=null){	            		
		    	            		System.out.println("\n\n\t\tCongrats..Ticket Booking Successful..\n \t\tYour Seats Information are above..");
		    	            		System.out.println("\t\tConfirmation Code: "+reserveResponse);
		    	            	}
		    	            	else{
		    	            		System.out.println("\t\tOops..Unable to Reserve Tickets \n\n Your hold must have expired. Please try again !!!");
		    	            	}
		            		}
		            		else if(userRequestChoice.toLowerCase().equals("n")){
		            			System.out.println("Hold Request Cancelled");break;
		            		}
		            		else{
		            			System.out.println("Invalid Input");break;
		            		}
		            	}
	            	else{
	            		System.out.println("\t\tOops..Unable to Hold Tickets \n\n Please try again !!!");break;
	            	}
	           
	           	
	            	break;
	            }
	            case 6:{
	            	System.out.println("\n\n\t\tYou need to hold seats before you can reserve tickets since the HoldIds are automatically generated by the application.");
	            	System.out.println("\t\tTry option 4 instead if you would like to reserve tickets.!");
	            	break;
	            }
	            case 7:{
	            	
	            	Integer noOfSeats,minLevel,maxLevel;
	            	String emailId;
	            	scanner.nextLine();
	            	System.out.println("\t\tEnter your email id: ");
	            	emailId = scanner.nextLine();
	            		
	            	System.out.println("\t\tEnter the number of seats to hold: ");
	            	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tPlease enter an integer value.");
                        break;         
                    }
	            	System.out.println("\t\tEnter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tPlease enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("\t\tEnter the minimum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("\t\tPlease enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);
	            		
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	System.out.println("Waiting for 60 seconds. Please Hold!!!!");
	            	Thread.sleep(60000);
	            	
	            	String reserveResponse = ticketServiceController.reserveSeats(seatHold.getSeatHoldid(),seatHold.getCustomerEmail());
	            	if(reserveResponse!=null){	            		
	            		controllerUtil.displaySeatsInformation(seatHold);
	            		System.out.println("\t\tCongrats..Ticket Booking Successful..\n Your Seats Information are above..");
	            	}
	            	else{
	            		System.out.println("\t\tOops.Unable to Reserve Tickets \n\n Your hold must have expired. Please try again !!!");
	            	}
	            	break;
	            	
	            }
	            case 8:{
	            	System.exit(0);
	            	}
	            
	            default: {
                    System.out.println("Wrong input. Please try again between the given values.");
                }
	            
	            }
	            
	           
			
		}		
	}
}
