package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.sql.Date;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class Payment{
@Enumerated
private PaymentForm paymentForm;
   
   public void setPaymentForm(PaymentForm value) {
this.paymentForm = value;
    }
public PaymentForm getPaymentForm() {
return this.paymentForm;
    }
private Date paymentDate;

public void setPaymentDate(Date value) {
this.paymentDate = value;
    }
public Date getPaymentDate() {
return this.paymentDate;
    }
private long cardNumber;

public void setCardNumber(long value) {
this.cardNumber = value;
    }
public long getCardNumber() {
return this.cardNumber;
    }
private Date expirationDate;

public void setExpirationDate(Date value) {
this.expirationDate = value;
    }
public Date getExpirationDate() {
return this.expirationDate;
    }
private int cvv;

public void setCvv(int value) {
this.cvv = value;
    }
public int getCvv() {
return this.cvv;
    }
private int paymentId;

public void setPaymentId(int value) {
this.paymentId = value;
    }
@Id
public int getPaymentId() {
return this.paymentId;
    }
private Time paymentTime;

public void setPaymentTime(Time value) {
this.paymentTime = value;
    }
public Time getPaymentTime() {
return this.paymentTime;
       }
   }
