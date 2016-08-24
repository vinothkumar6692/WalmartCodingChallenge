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
import org.joda.time.DateTime;
import model.Seat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import model.SeatHold;

/**
 * This class handles all the Database access for TheaterTicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */


public class TicketServiceDAO {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	/* Method to retrieve the number of available seats from the database*/
	public int getAvailableSeats(Optional<Integer> venueLevel) {
			
		if(!venueLevel.isPresent()){
			String query = "select count(*) from Seat where status = 1";
			 return jdbcTemplate.queryForInt(query);
		}
		else{
			if(venueLevel.get()<=findLevels()){
				int level = venueLevel.get();
				return findAvailableSeatsinLevel(level);				
			}
			else
				return -1;					
		}
	
	}
	/* Method to find number of available seats in a specific level*/
	public int findAvailableSeatsinLevel(int level){
		
		String query = "select count(*) from Seat where levelId = ? and status =1";
		int currentLevelSeatsAvailable = jdbcTemplate.queryForInt(query,new Object[]{level});	
		return currentLevelSeatsAvailable;
	}
	
	/* Method to find the best possible seats in a given level for a given number of seats*/
	public List<Seat> findBestSeatsInLevel(int level, int numSeats) {
		String findBestSeatsQuery = "select * from Seat where levelId = ? and status = 1 order by score limit ?";
		List<Seat> seats  = jdbcTemplate.query(findBestSeatsQuery,
				new BeanPropertyRowMapper(Seat.class),new Object[]{level,numSeats});
		return seats;
	}
	
	/* Method to create a new seatHold entry in the database*/
	public void createNewSeatHoldId(String uniqueHoldID, String customerEmail, Timestamp holdTime, int numSeats){
		String seatHoldTableInsertQuery= "insert into Seathold (seatholdId, customerEmail,holdtime,noOfSeats) values(?, ?, ?, ?)" ;
		jdbcTemplate.update(seatHoldTableInsertQuery, new Object[] { uniqueHoldID, customerEmail,
			holdTime, numSeats
		});	
		
	}
	
	/*Method to update all seat related information for a specific hold request in the database*/
	public void updateSeatHoldInformation(List<Seat> seats, String uniqueHoldID){
		for(Seat s : seats){
			/*Update the obtained seat no information in the SeatHoldMapping Table
			 * */									
			String holdMappingQuery= "insert into SeatholdMapping (seatholdId,seatId) values(?, ?)" ;
			jdbcTemplate.update(holdMappingQuery, new Object[] { uniqueHoldID, s.getSeatID()							
			});
			/*Change Status of Seats to Hold in Seats Table
			 * */
			String seatStatusChangeQuery = "update Seat set status = 2 where seatId = ?";
			jdbcTemplate.update(seatStatusChangeQuery,new Object[]{s.getSeatID()});
		}
		
	}
	
	/* Helper method to find the number of Levels in the theater*/
	public int findLevels(){
		String query = "select count(*) from level";
			return jdbcTemplate.queryForInt(query);		
	}
	
	/* Method to find the timestamp for a specific HoldId(Hold Request)*/
	public Timestamp findHoldTime(UUID holdId){
		String query="select holdtime from Seathold where seatholdId = ?";
		Timestamp holdTime = jdbcTemplate.queryForObject(query,new Object[]{holdId.toString()}, Timestamp.class);
		return holdTime;		
	}
	public void reserveHoldTicket(String confirmationCode, Timestamp currentTime, UUID seatHoldId){
		/* Update in SeatHold Table with new confirmation code*/
		String updateSeatHoldQuery = "update Seathold set confirmationCode = ? , reservationTime = ? where seatholdId = ?";
		jdbcTemplate.update(updateSeatHoldQuery,new Object[]{confirmationCode,currentTime, seatHoldId.toString()});
				
		/* Update Status of Seats to 'reserved' in Seat Table*/
		System.out.println("SeatHoldMapping Change");
		String findHoldSeats = "select seatId from SeatholdMapping where seatholdId = ?";
		
		List<Map<String, Object>> res = jdbcTemplate.queryForList(findHoldSeats,new Object[]{seatHoldId.toString()});
		for (Map<String, Object> rowMap : res) {
			for(Entry<String, Object> entry : rowMap.entrySet())
		    {   
		         Integer currentSeatId = (Integer) entry.getValue();
		         String seatStatusReserveQuery = "update Seat set status = 0 where seatId = ?";
		         jdbcTemplate.update(seatStatusReserveQuery,new Object[]{currentSeatId});
		    }
		}
	
		/*set isValidHold in seatHold table*/
		String changeHoldValidStatusQuery = "update seatHold set isValidHold = 0 where seatholdId = ?";
		jdbcTemplate.update(changeHoldValidStatusQuery,new Object[]{seatHoldId.toString()});
		
	}
	
	
	/* Method to find if a given SeatHoldId is Valid*/
	public Boolean findSeatHold(UUID seatHoldId){
		String findBestSeatsQuery = "select * from SeatHold where seatholdId= ?";
		List<SeatHold> seats  = jdbcTemplate.query(findBestSeatsQuery,new Object[]{seatHoldId.toString()},
				new BeanPropertyRowMapper(SeatHold.class));
		return seats.isEmpty();
	}
	

}
