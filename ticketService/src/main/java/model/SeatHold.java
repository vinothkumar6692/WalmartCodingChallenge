package model;

import org.joda.time.DateTime;

public class SeatHold {
	private long seatHoldid;
	public long getSeatHoldid() {
		return seatHoldid;
	}
	public void setSeatHoldid(long seatHoldid) {
		this.seatHoldid = seatHoldid;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public DateTime getHoldTime() {
		return holdTime;
	}
	public void setHoldTime(DateTime holdTime) {
		this.holdTime = holdTime;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	public DateTime getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(DateTime reservationTime) {
		this.reservationTime = reservationTime;
	}
	private String customerEmail;
	private DateTime holdTime;
	private String confirmationCode;
	private DateTime reservationTime;
	private Integer noOfSeats;
	public Integer getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(Integer noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
}
