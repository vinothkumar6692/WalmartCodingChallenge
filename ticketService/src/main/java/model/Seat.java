package model;
import model.Level;

/**
 * This class represents the Seat Object for the TheaterTicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */

public class Seat {
	private Integer levelId;
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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
	public Seat() {
		super();
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
