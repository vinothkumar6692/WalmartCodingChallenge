package dao;
import org.springframework.jdbc.core.JdbcTemplate;

import model.Level;

public class LevelDAO {
	 private JdbcTemplate jdbcTemplate;
	 public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
		    this.jdbcTemplate = jdbcTemplate;
		 }
	 /*
	 public int saveStudent(Student s) {
		    String query = "insert into student values ( " + s.getRollno() + ",'"+ s.getName() + "'," + s.getMarks() + ")";
		    return jdbcTemplate.update(query);
		 }
		 */
	 public int getLevels(){
		 String query = "select count(*) from Level";
		 return jdbcTemplate.queryForInt(query);

	 }

}
