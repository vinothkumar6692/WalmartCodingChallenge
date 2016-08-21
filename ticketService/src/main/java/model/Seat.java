package model;
import model.Level;

public class Seat {
	private Level level;
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	private Integer row;
	private Integer seatNo;
	private Integer score;
	private enum status{
		hold, reserved, available
	}
	private Integer seatID;
	public Integer getSeatID() {
		return seatID;
	}
	public void setSeatID(Integer seatID) {
		this.seatID = seatID;
	}


}
