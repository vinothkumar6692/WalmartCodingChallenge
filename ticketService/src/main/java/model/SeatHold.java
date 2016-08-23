package model;

import java.sql.Timestamp;
import java.util.UUID;

public class SeatHold {
	private UUID seatHoldid;
	public UUID getSeatHoldid() {
		return seatHoldid;
	}
	public SeatHold() {
		super();
	}
	public SeatHold(UUID seatHoldid, String customerEmail, Timestamp holdTime, String confirmationCode,
			Timestamp reservationTime, Integer noOfSeats) {
		super();
		this.seatHoldid = seatHoldid;
		this.customerEmail = customerEmail;
		this.holdtime = holdTime;
		this.confirmationCode = confirmationCode;
		this.reservationTime = reservationTime;
		this.noOfSeats = noOfSeats;
	}
	public void setSeatHoldid(UUID seatHoldid) {
		this.seatHoldid = seatHoldid;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Timestamp getHoldTime() {
		return holdtime;
	}
	public void setHoldTime(Timestamp holdTime) {
		this.holdtime = holdTime;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	public Timestamp getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(Timestamp reservationTime) {
		this.reservationTime = reservationTime;
	}
	private String customerEmail;
	private Timestamp holdtime;
	private String confirmationCode;
	private Timestamp reservationTime;
	private Integer noOfSeats;
	private Integer isValidHold;
	public Integer getIsValidHold() {
		return isValidHold;
	}
	public void setIsValidHold(Integer isValidHold) {
		this.isValidHold = isValidHold;
	}
	public Integer getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(Integer noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
}
