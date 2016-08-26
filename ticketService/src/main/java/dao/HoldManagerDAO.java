package dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import model.Seat;
import model.SeatHold;

/**
 * This class acts as a Hold Manager and removes all expired holds.
 * Created by Vinoth Kumar on 8/21/2016.
 */


public class HoldManagerDAO {
	public JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	public synchronized void removeAllInvalidHolds(){
		
		/*Retrieve all valid holds from Seathold table*/
		try{
			String checkExistingHoldsQuery = "select * from Seathold where isValidHold = 1";
			
			List<SeatHold> validSeatHolds  = jdbcTemplate.query(checkExistingHoldsQuery,
					new BeanPropertyRowMapper<SeatHold>(SeatHold.class));
			System.out.println(validSeatHolds.size());
			
			for(SeatHold seatHold : validSeatHolds){			
				/* Check the time difference for each holdID*/
				Timestamp currentTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				Timestamp holdTime = seatHold.getHoldTime();
				long holdReserveTimeDifference = currentTime.getTime()-holdTime.getTime();
				String holdID = seatHold.getSeatHoldid().toString();
				if(holdReserveTimeDifference>60000){
					/*Remove all the holds that have expired*/
					
					/*set isValidHold in seatHold table to invalid*/
					String changeHoldValidStatusQuery = "update seatHold set isValidHold = 0 where seatholdId = ?";
					jdbcTemplate.update(changeHoldValidStatusQuery,new Object[]{holdID.toString()});
					
					/*Retrieve all the corresponding seatIDs for the current Hold and set the status of the seats to available in the 'Seat' table */
					String findHoldSeats = "select seatId from SeatholdMapping where seatholdId = ?";
					
					List<Map<String, Object>> res = jdbcTemplate.queryForList(findHoldSeats,new Object[]{holdID.toString()});
					for (Map<String, Object> rowMap : res) {
						for(Entry<String, Object> entry : rowMap.entrySet())
					    {   //print keys and values
					         Integer currentSeatId = (Integer) entry.getValue();
					         String seatStatusReserveQuery = "update Seat set status = 1 where seatId = ?";
					         jdbcTemplate.update(seatStatusReserveQuery,new Object[]{currentSeatId});
					    }
					}
					
					
					/*Remove entries in SeatHoldMapping Table*/
					String deleteSeatHoldMappingEntriesQuery = "delete from SeatholdMapping where seatholdId = ?";
					jdbcTemplate.update(deleteSeatHoldMappingEntriesQuery,new Object[]{holdID.toString()});
					
				}
				else{
					continue;
				}
				
				
				
			}
			
			
			
		}
		catch(NullPointerException es){
			System.out.println("Exception");
			
		}
		finally{
			
		}
		
	
		
		
	}

}
