import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.TicketServiceDAO;
import junit.framework.TestCase;
import model.Seat;
import service.TicketServiceUtils;

/**
 * 
 */

/**
 * @author Vinoth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketServiceDAOTest extends TestCase{
	private static ApplicationContext ctx ;
	private static TicketServiceDAO ticketServiceDao ;

	/**
	 * @throws java.lang.Exception
	 */

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		 ctx = new ClassPathXmlApplicationContext(
			    "applicationContext.xml");
		 ticketServiceDao = (TicketServiceDAO) ctx.getBean("tsdao");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBestSeatsInLevel() {	
		List<Seat> s = ticketServiceDao.findBestSeatsInLevel(1,4);
		assertNotNull(s);
		
	}
	
	@Test
	public void testCreateNewSeatHold(){
		String uniqueHoldID = UUID.randomUUID().toString();
		String customerEmail = "abc@nyu.edu";
		Timestamp holdTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		ticketServiceDao.createNewSeatHoldId(uniqueHoldID,customerEmail,holdTime,4);
	}
	@Test
	public void testfindSeatHold(){
		UUID seatHoldId = UUID.randomUUID();
		Boolean isSeatHoldPresent = ticketServiceDao.findSeatHold(seatHoldId);
		assertTrue(isSeatHoldPresent);
	}
	@Test
	public void testReserveHoldTickets(){
		UUID seatHoldId = UUID.randomUUID();
		String confirmationCode = UUID.randomUUID().toString();	
		Integer value = null;		
	    Optional<Integer> a1 = Optional.ofNullable(value);
	    int seatsAvailablebefore = ticketServiceDao.getAvailableSeats(a1);
		Timestamp currentTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		int seatAvailableAfter = ticketServiceDao.getAvailableSeats(a1);
		ticketServiceDao.reserveHoldTicket(confirmationCode,currentTime,seatHoldId);		
		assertEquals("Invalid Hold id Reserve Test",seatsAvailablebefore,seatAvailableAfter);
	}
}
