package service;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import model.Level;
import model.SeatHold;
import dao.LevelDAO;
import dao.SeatDAO;

public class LevelService {
	public static void main(String[] args) {

		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		    "applicationContext.xml");
		 // TicketServiceImpl dao1 = (LevelDAO) ctx.getBean("ldao");
		  //int a = dao1.getLevels();
		  //System.out.println(a);
		  Integer value2 = new Integer(1);		
	      //Optional.ofNullable - allows passed parameter to be null.
	      Optional<Integer> a = Optional.ofNullable(value2);
		  TicketServiceImpl dao2 = (TicketServiceImpl) ctx.getBean("tdao");
		  int b = dao2.numSeatsAvailable(a);
		  System.out.println("Seats available:"+b);
		  System.out.println("***");
		  SeatHold s = dao2.findAndHoldSeats(3, a, a, "vrv@nyu.edu");
		 }

}
