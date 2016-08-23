package service;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.joda.time.DateTime;
import model.Seat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.HoldManagerDAO;
import dao.TicketServiceDAO;

import model.SeatHold;
/**
 * This class implements the methods of the interface TicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */

public class TicketServiceImpl implements TicketService{

	private ApplicationContext ctx;
	public TicketServiceImpl() {
		super();
	}

	
	  /**
     * @return returns the number of available seats for the specified level,
     *         if no level is specified it returns the total number of seats available in the entire theatre.
     *         if an undefined level is passed, it returns '-1'.
     * @param venueLevel 
     * 			venue level identifier to limit the search. Eg: 1,2
     */
	public int numSeatsAvailable(Optional<Integer> venueLevel){		
			ctx = new ClassPathXmlApplicationContext(
				    "applicationContext.xml");
			HoldManagerDAO holdManager = (HoldManagerDAO) ctx.getBean("hdao");
			holdManager.removeAllInvalidHolds();
			TicketServiceDAO ticketServiceDAO = (TicketServiceDAO) ctx.getBean("tsdao");
			return ticketServiceDAO.getAvailableSeats(venueLevel);
		
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail){
		ctx = new ClassPathXmlApplicationContext(
			    "applicationContext.xml");
		HoldManagerDAO holdManager = (HoldManagerDAO) ctx.getBean("hdao");
		holdManager.removeAllInvalidHolds();
		TicketServiceDAO ticketServiceDAO = (TicketServiceDAO) ctx.getBean("tsdao");
		for(int i=minLevel.get();i<=maxLevel.get();i++){		
			int currentLevelSeatsAvailable = ticketServiceDAO.findAvailableSeatsinLevel(i);
			
			// Seats are available in this level.
			if(numSeats<= currentLevelSeatsAvailable){ 		
				/* Find the best seats in the given level*/
				List<Seat> seats  = ticketServiceDAO.findBestSeatsInLevel(i,numSeats);
				System.out.println("Finding Seats");
				System.out.println("Seat count:"+seats.size());			
				for(Seat s : seats){
					System.out.println("Seat count:"+s.getSeatID());
				}							
				/* Create an entry in the Seathold Table for the current customer request*/
				UUID uniqueId = UUID.randomUUID();
				String uniqueHoldID = uniqueId.toString();
				Timestamp holdTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());				
				ticketServiceDAO.createNewSeatHoldId(uniqueHoldID, customerEmail,holdTime, numSeats);	
				ticketServiceDAO.updateSeatHoldInformation(seats, uniqueHoldID);				
				SeatHold seatHold = new SeatHold(uniqueId,customerEmail,holdTime,null,null,numSeats);
				return seatHold;				
			}
			else
				continue;
		}
		return null;
	}

	public String reserveSeats(UUID seatHoldId, String customerEmail){
		ctx = new ClassPathXmlApplicationContext(
			    "applicationContext.xml");
		HoldManagerDAO holdManager = (HoldManagerDAO) ctx.getBean("hdao");
		holdManager.removeAllInvalidHolds();
		TicketServiceDAO ticketServiceDAO = (TicketServiceDAO) ctx.getBean("tsdao");
		Timestamp currentTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Timestamp holdTime = ticketServiceDAO.findHoldTime(seatHoldId);
		long holdReserveTimeDifference = currentTime.getTime()-holdTime.getTime();		
		System.out.println("Time current:"+currentTime);
		System.out.println("Time fetched:"+holdTime);
		System.out.println(holdReserveTimeDifference);
		if(holdReserveTimeDifference>60000){
			return "Unable to reserve ticket! Hold time has expired 60 seconds";
		}
		else{
			/* Reserve the seats for the corresponding seatHoldId*/		
			String confirmationCode = UUID.randomUUID().toString();					
			ticketServiceDAO.reserveHoldTicket(confirmationCode,currentTime,seatHoldId);
			
			}			
			return "Booking Succesfull!";			
		}	
}
