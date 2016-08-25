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
		Logger logger = (Logger) LoggerFactory.getLogger("TheaterTicketService");
		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		    "applicationContext.xml");
		TicketServiceUtils controllerUtil = (TicketServiceUtils) ctx.getBean("utilbean");
		TicketServiceImpl ticketServiceController = (TicketServiceImpl) ctx.getBean("tdao");

		Scanner scanner = new Scanner(System.in);
        int input;
		
		System.out.println("\t\t******  Welcome to the Theater Ticket Service ******");
		
		while (true) {

	            System.out.println("\n\n**Please Select one of the Options Below**\n\n");
	            System.out.println("Please choose from the choices below.");
	            System.out.println("1. View all Levels in the theater");
	            System.out.println("2. Find the number of seats available in the entire Theater.");
	            System.out.println("3. Find the number of seats available with a specific level.");
	            System.out.println("4. Find and hold the best seats in the theater.");
	            System.out.println("5. Hold and Reserve the best seats in the theater.");
	            System.out.println("6. Reserve tickets");
	            System.out.println("7. Hold Seats, wait for 60 seconds and Reserve");
	            System.out.println("8. Exit\n\n");
	            System.out.println("Enter your Option: ");
	            try{
	                input = scanner.nextInt();
	            }
	            catch (InputMismatchException e){
	                System.out.println("Please enter an integer value within the range.");
	                break;
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
	            		System.out.println("\n\nTotal Number of Available Seats in the Theater: "+noOfSeats);
	            		break;
	            	}
	            	
	            case 3:{
	            	System.out.println("Enter the Level Id: ");
	            	Integer level = scanner.nextInt();
	            	Optional<Integer> levelInput = Optional.ofNullable(level);
	            	int noOfSeats = ticketServiceController.numSeatsAvailable(levelInput);
            		System.out.println("\n\nTotal Number of Available Seats in the Level "+level+": "+ noOfSeats);
	            	break;
	            }
	            
	            case 4:{
	            	Integer noOfSeats,minLevel,maxLevel;
	            	String emailId;
	            	scanner.nextLine();
	            	System.out.println("Enter your email id: ");
	            	emailId = scanner.nextLine();   		
	            	System.out.println("Enter the number of seats to hold: ");	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);            	
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	if(seatHold==null){
	            		System.out.println("Unable to Hold Tickets. Please Try again later.");
	            	}
	            	else{
	            		controllerUtil.displaySeatsInformation(seatHold);
	            		System.out.println("Seat Hold Successful!!");
	            	}
	            	break;
	            }
	            case 5:{
	            	Integer noOfSeats,minLevel,maxLevel;
	            	String emailId;
	            	scanner.nextLine();
	            	System.out.println("Enter your email id: ");
	            	emailId = scanner.nextLine();
	            		
	            	System.out.println("Enter the number of seats to hold: ");
	            	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);
	            		
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	String reserveResponse = ticketServiceController.reserveSeats(seatHold.getSeatHoldid(),seatHold.getCustomerEmail());
	            	if(reserveResponse=="Booking Succesful!"){	            		
	            		controllerUtil.displaySeatsInformation(seatHold);
	            		System.out.println("Congrats..Ticket Booking Successful..\n Your Seats Information are above..");
	            	}
	            	else{
	            		System.out.println("Oops.Unable to Reserve Tickets \n\n Error Message: !!!"+reserveResponse);
	            	}
	            	break;
	            }
	            case 6:{
	            	System.out.println("\n\nYou need to hold seats before you can reserve tickets since the HoldIds are automatically generated by the application.");
	            	System.out.println("Try option 4 instead if you would like to reserve tickets.!");
	            	break;
	            }
	            case 7:{
	            	
	            	Integer noOfSeats,minLevel,maxLevel;
	            	String emailId;
	            	scanner.nextLine();
	            	System.out.println("Enter your email id: ");
	            	emailId = scanner.nextLine();
	            		
	            	System.out.println("Enter the number of seats to hold: ");
	            	
	            	try {
	            		noOfSeats = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		minLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> minLevelInt = Optional.ofNullable(minLevel);
	            	System.out.println("Enter the minimum Level you would like to hold:");
	            	try {
	            		maxLevel = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Please enter an integer value.");
                        break;         
                    }
	            	Optional<Integer> maxLevelInt = Optional.ofNullable(maxLevel);
	            		
	            	SeatHold seatHold = ticketServiceController.findAndHoldSeats(noOfSeats,minLevelInt, maxLevelInt, emailId);
	            	Thread.sleep(60000);
	            	String reserveResponse = ticketServiceController.reserveSeats(seatHold.getSeatHoldid(),seatHold.getCustomerEmail());
	            	if(reserveResponse=="Booking Succesfull!"){	            		
	            		controllerUtil.displaySeatsInformation(seatHold);
	            		System.out.println("Congrats..Ticket Booking Successful..\n Your Seats Information are above..");
	            	}
	            	else{
	            		System.out.println("Oops.Unable to Reserve Tickets \n\n Error Message: !!!"+reserveResponse);
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
