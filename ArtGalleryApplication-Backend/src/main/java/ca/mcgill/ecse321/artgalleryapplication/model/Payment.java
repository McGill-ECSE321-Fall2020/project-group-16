package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.*;
import java.sql.Date;
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
private String cardNumber;

public void setCardNumber(String value) {
this.cardNumber = value;
    }
public String getCardNumber() {
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
