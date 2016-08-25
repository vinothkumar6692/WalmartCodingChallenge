package dao;

import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.SeatHold;
import service.TicketServiceImpl;

/**
 * This class handles all the Database access for TicketServiceUtil.
 * Created by Vinoth Kumar on 8/21/2016.
 */


public class TicketServiceUtilsDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(TicketServiceDAO.class);
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	
	/*Method to retrieve the seats information for a specific HoldId*/
	public List<Seat> getSeatsInformation(String seatHoldId){
		logger.info("Fetching Seat Information for SeatHold id:"+seatHoldId);
		String getSeatIDQuery="select seatId from SeatholdMapping where seatholdId = ?";
		List<Integer> seatIds = jdbcTemplate.queryForList(getSeatIDQuery,new Object[]{seatHoldId.toString()}, Integer.class);
		List<Seat> seats = new ArrayList<Seat>();
		String getSeatsQuery = "select * from Seat where seatId = ?";
		for(Integer seatId : seatIds){
			List<Seat> seat  =  jdbcTemplate.query(getSeatsQuery,
					new BeanPropertyRowMapper(Seat.class),new Object[]{seatId});
			seats.add(seat.get(0));
		}
		return seats;
	}

}
