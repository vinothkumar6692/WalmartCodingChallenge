package model;

/**
 * This class represents the Level Object for the TheaterTicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */

public class Level {
	private Integer levelId;
	private String levelName;
	private Double price;
	private Integer numberOfRow;
	private Integer seatsInRow;
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(double d) {
		this.price = d;
	}
	public Integer getNumberOfRow() {
		return numberOfRow;
	}
	public void setNumberOfRow(Integer numberOfRow) {
		this.numberOfRow = numberOfRow;
	}
	public Integer getSeatsInRow() {
		return seatsInRow;
	}
	public void setSeatsInRow(Integer seatsInRow) {
		this.seatsInRow = seatsInRow;
	}
	
}
