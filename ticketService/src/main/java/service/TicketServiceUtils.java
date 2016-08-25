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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Level;
import model.Seat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.HoldManagerDAO;
import dao.LevelDAO;
import dao.TicketServiceDAO;
import dao.TicketServiceUtilsDAO;
import model.SeatHold;
/**
 * This class contains all the utility methods for ticket service.
 * Created by Vinoth Kumar on 8/21/2016.
 */

public class TicketServiceUtils {
	private static Logger logger = (Logger) LoggerFactory.getLogger(TicketServiceImpl.class);
	public LevelDAO levelDAO;
	public TicketServiceUtilsDAO utilDAO;
	private ApplicationContext ctx;
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		TicketServiceUtils.logger = logger;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public HoldManagerDAO getHoldManager() {
		return holdManager;
	}
	public void setHoldManager(HoldManagerDAO holdManager) {
		this.holdManager = holdManager;
	}
	private JdbcTemplate jdbcTemplate;
	public HoldManagerDAO holdManager;
	
	/*Method to find all level information*/
	public List<Level> getAllLevels(){
		ctx = new ClassPathXmlApplicationContext(
			    "applicationContext.xml");
		levelDAO = (LevelDAO) ctx.getBean("leveldao");
		List<Level> levels = levelDAO.getLevels();
		return levels;
	}
	
	/*Utility Method to display all level information*/
	public void displayLevels(List<Level> levels){
		if(levels.size()==0){
			System.out.println("No levels Available");
		}
		System.out.println("\n\n****Available Levels in the Theater****");
		System.out.println("Level \tLevel Name \t Price");
		for(Level level: levels){
			System.out.format("%5s%12s%12s\n", level.getLevelId(), level.getLevelName(), level.getPrice());
		}
	}
	
	/*Utility Method to display seat hold information for a specific SeatHold*/
	public void displaySeatsInformation(SeatHold seathold){	
		ctx = new ClassPathXmlApplicationContext(
			    "applicationContext.xml");
		utilDAO = (TicketServiceUtilsDAO) ctx.getBean("utildao");
		List<Seat> seats = utilDAO.getSeatsInformation(seathold.getSeatHoldid().toString());
		System.out.println("\n\n*********SeatHold Information**********");
		for(Seat seat : seats){
			System.out.println("Level Id:"+seat.getLevelId());
			System.out.println("Row Number:"+seat.getRow());
			System.out.println("Seat Number:"+seat.getSeatNo()+"\n");
		}	
		System.out.println("******End of SeatHold Information******\n");
	}

}
