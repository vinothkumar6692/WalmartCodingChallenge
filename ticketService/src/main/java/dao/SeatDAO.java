package dao;
import org.springframework.jdbc.core.JdbcTemplate;

public class SeatDAO {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	 }
	 public int getAvailableSeats(){
		 String query = "select count(*) from Seat where status = 1";
		 return jdbcTemplate.queryForInt(query);
	 }
}
