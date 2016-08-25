package service;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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

public class LevelService {
	/*
	
	public static void main(String[] args) throws InterruptedException {
	
		Logger logger = (Logger) LoggerFactory.getLogger("TheaterTicketService");
		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		    "applicationContext.xml");

		 // TicketServiceImpl dao1 = (LevelDAO) ctx.getBean("ldao");
		  //int a = dao1.getLevels();
		  //System.out.println(a);
		  
		  Integer value1 = null;
			
	      //Optional.ofNullable - allows passed parameter to be null.
	      Optional<Integer> a1 = Optional.ofNullable(value1);
		  Integer value2 = new Integer(2);		
	      Optional<Integer> a = Optional.ofNullable(value2);
		  TicketServiceImpl dao2 = (TicketServiceImpl) ctx.getBean("tdao");
		  int b = dao2.numSeatsAvailable(a);
		  a = Optional.ofNullable(value2);
		 
		  System.out.println("Seats available:"+b); 
		  System.out.println("***");
		  logger.info("Seats log1");
		  
		  HoldManagerDAO hd = (HoldManagerDAO) ctx.getBean("hdao");
		  hd.removeAllInvalidHolds();
		  
		  SeatHold s = dao2.findAndHoldSeats(3, a, a, "rv@nyu.edu");
		  
		  System.out.println(s.getSeatHoldid());		  
		  
		  //System.out.println(dao2.reserveSeats(s.getSeatHoldid(),s.getCustomerEmail()));
		  
		  TicketServiceUtils serviceUtil = (TicketServiceUtils) ctx.getBean("utilbean");
		  List<Level> l = serviceUtil.getAllLevels();
		  System.out.println("LEVEL SIZE:"+l.size());
		  serviceUtil.displayLevels(l);
		  serviceUtil.displaySeatsInformation(s);
		  
		 }
*/
}
