package ticketService;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import dao.HoldManagerDAO;
import dao.TicketServiceDAO;
import model.SeatHold;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mock;


import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import service.TicketServiceImpl;

public class TicketServiceImplTests {
	TicketServiceImpl ticketservice;	
	TicketServiceDAO barMock;
	
	@Before
    public void setUp() throws Exception {
		ticketservice = new TicketServiceImpl();
		barMock = mock(TicketServiceDAO.class);
		when(barMock.findLevels()).thenReturn(0);

    }
	@After
    public void tearDown() {
		ticketservice = null;
    }
	
	@Test
	public void numSeatsAvailableInvalidTest(){
		TicketServiceImpl test = Mockito.mock(TicketServiceImpl.class);
		Integer value2 = new Integer(5);		
	    Optional<Integer> a = Optional.ofNullable(value2);
		test.numSeatsAvailable(a);
		verify(test).numSeatsAvailable(eq(a));
		assertEquals("Invalid Seat Level Test",0,test.numSeatsAvailable(a));		
	}
	@Test
	public void numSeatsAvailableValidTest() throws Exception{
		when(barMock.findLevels()).thenReturn(0);
		TicketServiceImpl test = Mockito.mock(TicketServiceImpl.class);
		Integer value2 = new Integer(1);		
	    Optional<Integer> a = Optional.ofNullable(value2);        
		assertNotEquals("Valid Seat Level Test",-1,test.numSeatsAvailable(a));	
				
		TicketServiceDAO spy = spy(new TicketServiceDAO());
	        doReturn(0).when(spy).findLevels();
	        test.numSeatsAvailable(a);
	        assertNotEquals(-1,test.numSeatsAvailable(a));
	}
	
	
	@Test
	public void testfindAndHoldSeats(){
		SeatHold mockObject = new SeatHold();
		mockObject.setCustomerEmail("rv@nyu.edu");
		Integer valueMin = new Integer(1);	
		Integer valueMax = new Integer(4);	
	    Optional<Integer> minLevel = Optional.ofNullable(valueMin);
	    Optional<Integer> maxLevel = Optional.ofNullable(valueMax);
		SeatHold seathold = ticketservice.findAndHoldSeats(3, minLevel, maxLevel, "rv@nyu.edu");
		assertNotNull(seathold);
		Integer holdStatus = new Integer(1);
		assertEquals("Verify the hold request for a particular user",mockObject.getCustomerEmail(),seathold.getCustomerEmail());
		assertNotEquals("Verify that a unique seatHoldID is generated for the hold request",null,seathold.getSeatHoldid());
		assertEquals("Verify the status of the current hold. Should be valid",holdStatus ,seathold.getIsValidHold());
	}
	
	@Test
	public void testNumberOfAvailableSeatsinLevel1(){
		/* Run this test case when when after the testfindAndHoldSeats() always . 
		 * This test case is to verify the total number of available seats in a particular level before any hold/reserve.
		 * This also helps in verifying the testfindAndHoldSeats() test case which tries to successfully hold 3 seats.*/
		HoldManagerDAO manager = Mockito.mock(HoldManagerDAO.class);
		Integer value = new Integer(1);		
	    Optional<Integer> level = Optional.ofNullable(value); 
	    ticketservice.numSeatsAvailable(level);
	    ticketservice.holdManager = manager;
	    assertEquals("Initial Test. Check Available Seats in Level 1",1247,ticketservice.numSeatsAvailable(level));	
	}
	
	@Test
	public void testReserveSeats(){
		UUID uniqueId = UUID.randomUUID();
		String reserveResponse = ticketservice.reserveSeats(uniqueId, "abc@nyu.edu");
		assertNotEquals("Reserve Seats for Invalid HoldId","Booking Succesfull!",reserveResponse);
		assertEquals("Reserve Seats for Invalid HoldId","Invalid HoldID",reserveResponse);
	}

}
