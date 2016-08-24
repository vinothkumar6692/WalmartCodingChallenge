package dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dao.HoldManagerDAO;
import dao.TicketServiceDAO;
import model.Seat;
import model.SeatHold;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.junit.Test;

public class TicketServiceDAOTest {
	public TicketServiceDAO ticketServiceDao;
	
	@Before
	public void setUp(){
		ticketServiceDao = new TicketServiceDAO();
	}
	@Test(expected = NullPointerException.class) 
	public void TestfindAvailableSeatsinLevell(){
		Integer value1 = new Integer(1);		
	    Optional<Integer> a1 = Optional.ofNullable(value1);
	    int seatsAvailable = ticketServiceDao.findAvailableSeatsinLevel(1);
		assertEquals("Find all available Seats in the theater at Level 1",1250,seatsAvailable);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void TestCreateNewSeatHold(){
		String uniqueHoldID = UUID.randomUUID().toString();
		String customerEmail = "abc@nyu.edu";
		Timestamp holdTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		ticketServiceDao.createNewSeatHoldId(uniqueHoldID,customerEmail,holdTime,4);
	}
	
	@Test(expected = NullPointerException.class) 
	public void TestBestSeatsInLevel(){
		List<Seat> s = ticketServiceDao.findBestSeatsInLevel(1,4);
		assertNotNull(s);
	}
	
	@Test(expected = NullPointerException.class)
	public void TestReserveHoldTickets(){
		UUID seatHoldId = UUID.randomUUID();
		String confirmationCode = UUID.randomUUID().toString();	
		Timestamp currentTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		ticketServiceDao.reserveHoldTicket(confirmationCode,currentTime,seatHoldId);		
	}
	
	@Test(expected = NullPointerException.class)
	public void TestfindSeatHold(){
		UUID seatHoldId = UUID.randomUUID();
		ticketServiceDao.findSeatHold(seatHoldId);
	}
	
}
