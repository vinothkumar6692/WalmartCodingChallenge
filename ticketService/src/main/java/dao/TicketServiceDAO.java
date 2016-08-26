package dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.Seat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.SeatHold;
import service.TicketServiceImpl;

/**
 * This class handles all the Database access for TheaterTicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */


public class TicketServiceDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(TicketServiceDAO.class);
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	/* Method to retrieve the number of available seats from the database*/
	public int getAvailableSeats(Optional<Integer> venueLevel) {
		logger.info("Retrieving seats availablility from Database");	
		if(!venueLevel.isPresent()){
			String query = "select count(*) from Seat where status = 1";
			logger.info("Returning number of available seats..");
			 return jdbcTemplate.queryForInt(query);
		}
		else{
			if(venueLevel.get()<=findLevels()){
				logger.info("Given Level is Valid..");
				int level = venueLevel.get();
				logger.info("Returning number of available seats..");
				return findAvailableSeatsinLevel(level);				
			}
			else
				return -1;					
		}
	
	}
	/* Method to find number of available seats in a specific level*/
	public int findAvailableSeatsinLevel(int level){
		logger.info("Finding Seats availability in Level: "+level);
		String query = "select count(*) from Seat where levelId = ? and status =1";
		int currentLevelSeatsAvailable = jdbcTemplate.queryForInt(query,new Object[]{level});	
		logger.info("Returning number of seats in level: "+level);
		return currentLevelSeatsAvailable;
	}
	
	/* Method to find the best possible seats in a given level for a given number of seats*/
	public List<Seat> findBestSeatsInLevel(int level, int numSeats) {
		logger.info("Finding Best Seats in Level: "+level);
		String findBestSeatsQuery = "select * from Seat where levelId = ? and status = 1 order by score limit ?";
		List<Seat> seats  = jdbcTemplate.query(findBestSeatsQuery,
				new BeanPropertyRowMapper(Seat.class),new Object[]{level,numSeats});
		logger.info("Returning the Best seats available in Level: "+level);
		return seats;
	}
	
	/* Method to create a new seatHold entry in the database*/
	public void createNewSeatHoldId(String uniqueHoldID, String customerEmail, Timestamp holdTime, int numSeats){
		logger.info("Creating a new SeatHold entry in the Database..");
		String seatHoldTableInsertQuery= "insert into Seathold (seatholdId, customerEmail,holdtime,noOfSeats) values(?, ?, ?, ?)" ;
		jdbcTemplate.update(seatHoldTableInsertQuery, new Object[] { uniqueHoldID, customerEmail,
			holdTime, numSeats
		});	
		
	}
	
	/*Method to update all seat related information for a specific hold request in the database*/
	public void updateSeatHoldInformation(List<Seat> seats, String uniqueHoldID){
		logger.info("Updating Seats information in the Database for HoldId: "+uniqueHoldID);
		for(Seat seat : seats){
			/*Update the obtained seat no information in the SeatHoldMapping Table
			 * */									
			String holdMappingQuery= "insert into SeatholdMapping (seatholdId,seatId) values(?, ?)" ;
			jdbcTemplate.update(holdMappingQuery, new Object[] { uniqueHoldID, seat.getSeatID()							
			});
			/*Change Status of Seats to Hold in Seats Table
			 * */
			String seatStatusChangeQuery = "update Seat set status = 2 where seatId = ?";
			jdbcTemplate.update(seatStatusChangeQuery,new Object[]{seat.getSeatID()});
		}
		
	}
	
	/* Helper method to find the number of Levels in the theater*/
	public int findLevels(){
		logger.info("Finding all available levels in the Theater..");
		String query = "select count(*) from level";
			return jdbcTemplate.queryForInt(query);		
	}
	
	/* Method to find the timestamp for a specific HoldId(Hold Request)*/
	public Timestamp findHoldTime(UUID holdId){
		
		String query="select holdtime from Seathold where seatholdId = ?";
		Timestamp holdTime = jdbcTemplate.queryForObject(query,new Object[]{holdId.toString()}, Timestamp.class);
		logger.info("Time at which HoldId: "+holdId+" was hold: "+holdTime);
		return holdTime;		
	}
	public void reserveHoldTicket(String confirmationCode, Timestamp currentTime, UUID seatHoldId){
		logger.info("Reserving seats for HoldId: "+seatHoldId);
		/* Update in SeatHold Table with new confirmation code*/
		String updateSeatHoldQuery = "update Seathold set confirmationCode = ? , reservationTime = ? where seatholdId = ?";
		jdbcTemplate.update(updateSeatHoldQuery,new Object[]{confirmationCode,currentTime, seatHoldId.toString()});
		logger.info("Updating SeatHold Mapping information in the database");		
		/* Update Status of Seats to 'reserved' in Seat Table*/
		System.out.println("SeatHoldMapping Change");
		String findHoldSeats = "select seatId from SeatholdMapping where seatholdId = ?";
		logger.info("Updating Seat status and other information in the database for HoldId"+seatHoldId);
		List<Map<String, Object>> res = jdbcTemplate.queryForList(findHoldSeats,new Object[]{seatHoldId.toString()});
		for (Map<String, Object> rowMap : res) {
			for(Entry<String, Object> entry : rowMap.entrySet())
		    {   
		         Integer currentSeatId = (Integer) entry.getValue();
		         String seatStatusReserveQuery = "update Seat set status = 0 where seatId = ?";
		         jdbcTemplate.update(seatStatusReserveQuery,new Object[]{currentSeatId});
		    }
		}
		logger.info("Updating seatHold status in the database..");
		/*set isValidHold in seatHold table*/
		String changeHoldValidStatusQuery = "update seatHold set isValidHold = 0 where seatholdId = ?";
		jdbcTemplate.update(changeHoldValidStatusQuery,new Object[]{seatHoldId.toString()});
		logger.info("Reservation completed for HoldId"+seatHoldId);
	}
	
	
	/* Method to find if a given SeatHoldId is Valid*/
	public Boolean findSeatHold(UUID seatHoldId){
		String findBestSeatsQuery = "select * from SeatHold where seatholdId= ?";
		List<SeatHold> seats  = jdbcTemplate.query(findBestSeatsQuery,new Object[]{seatHoldId.toString()},
				new BeanPropertyRowMapper(SeatHold.class));
		return seats.isEmpty();
	}
	

}
