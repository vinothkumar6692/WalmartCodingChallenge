package service;
import java.util.Calendar;
import java.util.Optional;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import model.Level;
import model.SeatHold;
import dao.LevelDAO;
import dao.SeatDAO;
import dao.TicketServiceDAO;
import dao.HoldManagerDAO;

import java.sql.Date;
import java.sql.Timestamp;


public class LevelService {
	
	
	public static void main(String[] args) throws InterruptedException {

		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		    "applicationContext.xml");
		 // TicketServiceImpl dao1 = (LevelDAO) ctx.getBean("ldao");
		  //int a = dao1.getLevels();
		  //System.out.println(a);
		  Integer value2 = new Integer(1);		
	      Optional<Integer> a = Optional.ofNullable(value2);
		  TicketServiceImpl dao2 = (TicketServiceImpl) ctx.getBean("tdao");
		  int b = dao2.numSeatsAvailable(a);
		  System.out.println("Seats available:"+b);
		  System.out.println("***");
		  
		  HoldManagerDAO hd = (HoldManagerDAO) ctx.getBean("hdao");
		  //hd.removeAllInvalidHolds();
		  //SeatHold s = dao2.findAndHoldSeats(3, a, a, "rv@nyu.edu");
		  //System.out.println(s.getSeatHoldid());		  
		  //System.out.println(dao2.reserveSeats(s.getSeatHoldid(),s.getCustomerEmail()));
		  
		 }

}
