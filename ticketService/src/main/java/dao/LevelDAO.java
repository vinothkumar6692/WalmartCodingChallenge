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


import model.Level;
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
 * This class handles all the Database access for Levels.
 * Created by Vinoth Kumar on 8/21/2016.
 */


public class LevelDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(TicketServiceDAO.class);
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
	    this.jdbcTemplate = jdbcTemplate;
	 }
	
	/* Returns a list of all Levels in the theater configuration*/
	public List<Level> getLevels(){	
		String findAllLevelsQuery = "select * from Level";
		List<Level> levels  = jdbcTemplate.query(findAllLevelsQuery,
				new BeanPropertyRowMapper(Level.class));
		logger.info("Returning all Levels in the theater ");
		return levels;
		
	}

}
