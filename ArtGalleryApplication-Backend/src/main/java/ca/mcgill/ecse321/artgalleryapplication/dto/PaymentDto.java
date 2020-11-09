package ca.mcgill.ecse321.artgalleryapplication.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;

public class PaymentDto {
	private PaymentForm paymentForm;
	private Date paymentDate;
	private String cardNumber;
	private Date expirationDate;
	private int cvv;
	private int paymentId;
	private Time paymentTime;

	public PaymentDto() {
	}

	public PaymentDto(PaymentForm pf, Date pd, String cardNumber, Date expiration, int cvv, int paymentId, Time paymentTime) {
		this.paymentForm = pf;
		this.paymentDate = pd;
		this.cardNumber = cardNumber;
		this.expirationDate = expiration;
		this.cvv = cvv;
		this.paymentId = paymentId;
		this.paymentTime = paymentTime;
	}

	public PaymentForm getPaymentForm() {
		return this.paymentForm;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}
	
	public int getCvv() {
		return this.cvv;
	}
	
	public int getPaymentId() {
		return this.paymentId;
	}
	
	public Time getPaymentTime() {
		return this.paymentTime;
	}
}
