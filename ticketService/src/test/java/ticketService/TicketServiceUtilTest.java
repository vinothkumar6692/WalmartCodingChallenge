/**
 * 
 */
package ticketService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dao.HoldManagerDAO;
import dao.TicketServiceDAO;
import junit.framework.TestCase;
import model.Level;
import model.SeatHold;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;


import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.TicketServiceUtils;

/**
 * @author Vinoth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketServiceUtilTest extends TestCase{


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("test",1,1);
	}

	@Test
	public void testGetAllLevels(){
		 ApplicationContext ctx = new ClassPathXmlApplicationContext(
				    "applicationContext.xml");
		TicketServiceUtils ticketServiceUtil = (TicketServiceUtils) ctx.getBean("utilbean");
		Level level1 = new Level();
		level1.setLevelId(1);
		level1.setLevelName("Orchestra");
		level1.setPrice(100.0);
		level1.setNumberOfRow(25);
		level1.setSeatsInRow(50);
		
		Level level2 = new Level();
		level2.setLevelId(2);
		level2.setLevelName("Main");
		level2.setPrice(75.00);
		level2.setNumberOfRow(20);
		level2.setSeatsInRow(100);
		
		Level level3 = new Level();
		level3.setLevelId(3);
		level3.setLevelName("Balcony 1");
		level3.setPrice(75.00);
		level3.setNumberOfRow(15);
		level3.setSeatsInRow(100);
		
		Level level4 = new Level();
		level4.setLevelId(4);
		level4.setLevelName("Balcony 2");
		level4.setPrice(100.0);
		level4.setNumberOfRow(15);
		level4.setSeatsInRow(100);
		
		List<Level> levels = new ArrayList<Level>();
		levels.add(level1);
		levels.add(level2);
		levels.add(level3);
		levels.add(level4);
		
		List<Level> levelsReceived = ticketServiceUtil.getAllLevels();
		assertEquals("Total Level Size Test",4,levelsReceived.size());
		assertEquals("First Level Name Test",level1.getLevelName(),levelsReceived.get(0).getLevelName());
		assertEquals("Second Level id Test",level2.getLevelId(),levelsReceived.get(1).getLevelId());
		assertEquals("Third Level number of rows Test",level3.getNumberOfRow(),levelsReceived.get(2).getNumberOfRow());
		assertEquals("Fourth Level number of seats Test",level4.getSeatsInRow(),levelsReceived.get(3).getSeatsInRow());
		
	}
}
