package model;

public class Level {
	private Integer levelId;
	private String levelName;
	private Float price;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
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
