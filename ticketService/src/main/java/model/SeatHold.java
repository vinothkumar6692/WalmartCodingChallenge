package model;

/**
 * This class represents the SeatHold Object for the TheaterTicketService.
 * Created by Vinoth Kumar on 8/21/2016.
 */

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
			Timestamp reservationTime, Integer noOfSeats, Integer isValidHold) {
		super();
		this.seatHoldid = seatHoldid;
		this.customerEmail = customerEmail;
		this.holdtime = holdTime;
		this.confirmationCode = confirmationCode;
		this.reservationTime = reservationTime;
		this.noOfSeats = noOfSeats;
		this.isValidHold = isValidHold;
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
	public Integer isValidHold;
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
