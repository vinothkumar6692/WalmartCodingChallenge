package service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.joda.time.DateTime;
import model.Seat;

import model.SeatHold;
/**
 * This class implements the methods of the interface TicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */

public class TicketServiceImpl implements TicketService{
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	
	  /**
     * @return returns the number of available seats for the specified level,
     *         if no level is specified it returns the total number of seats available in the entire theatre.
     *         if an undefined level is passed, it returns '-1'.
     * @param venueLevel 
     * 			venue level identifier to limit the search. Eg: 1,2
     */
	public synchronized int numSeatsAvailable(Optional<Integer> venueLevel){
		if(venueLevel.isPresent()){
			if(venueLevel.get()<=findLevels()){
				int level = venueLevel.get();
				String query = "select count(*) from Seat where levelId =" + level+" and status = 1";
				return jdbcTemplate.queryForInt(query);					
			}
			else
				return -1;					
		}
		 String query = "select count(*) from Seat where status = 1";
		 return jdbcTemplate.queryForInt(query);
		
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail){
		for(int i=minLevel.get();i<=maxLevel.get();i++){
			String query = "select count(*) from Seat where levelId="+i+" and status =1";
			int currentLevelSeatsAvailable = jdbcTemplate.queryForInt(query);
			if(numSeats<= currentLevelSeatsAvailable){
				// Seats are available in this level.
				
				/* Find the best seats in the given level*/
				String findBestSeatsQuery = "select * from Seat where levelId="+i+" and status =1"+" order by score limit "+numSeats;
				List<Seat> seats  = jdbcTemplate.query(findBestSeatsQuery,
						new BeanPropertyRowMapper(Seat.class));
				System.out.println("Finding Seats");
				System.out.println("Seat count:"+seats.size());
				
				for(Seat s : seats){
					System.out.println("Seat count:"+s.getSeatID());
				}
							
				/* Create an entry in the Seathold Table for the current customer request*/
				String uniqueHoldID = UUID.randomUUID().toString();
				DateTime holdTime = new DateTime();
				String seatHoldTableInsertQuery= "insert into Seathold (seatholdId, customerEmail,holdtime,noOfSeats) values(?, ?, ?, ?)" ;
					jdbcTemplate.update(seatHoldTableInsertQuery, new Object[] { uniqueHoldID, customerEmail,
						holdTime.toString(), numSeats
					});				
					for(Seat s : seats){
						/*Update the obtained seat no information in the SeatHoldMapping Table
						 * */									
						String holdMappingQuery= "insert into SeatholdMapping (seatholdId,seatId) values(?, ?)" ;
						jdbcTemplate.update(holdMappingQuery, new Object[] { uniqueHoldID, s.getSeatID()							
						});
						/*Change Status of Seats to Hold in Seats Table
						 * */
						String seatStatusChangeQuery = "update Seat set status = 2 where seatId = "+s.getSeatID();
						jdbcTemplate.update(seatStatusChangeQuery);
					}
			}
			else
				continue;
		}
		return null;
	}

	public String reserveSeats(int seatHoldId, String customerEmail){
		return "";
	}
	/* Helper method to find the number of Levels in the theater*/
	public int findLevels(){
		String query = "select count(*) from level";
		return jdbcTemplate.queryForInt(query);
		
	}
}
