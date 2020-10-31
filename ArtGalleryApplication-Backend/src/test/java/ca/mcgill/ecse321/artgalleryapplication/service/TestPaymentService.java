package ca.mcgill.ecse321.artgalleryapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.PaymentRepository;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;

@ExtendWith(MockitoExtension.class)
public class TestPaymentService {
	@Mock
	private PaymentRepository paymentDao;
	
	@InjectMocks
	private PaymentService service;
	
	private static final int PAYMENT_ID = 123456789;
	
	private static final PaymentForm PAYMENT_FORM = PaymentForm.CreditCard;
	private static final Date PAYMENT_DATE = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
	private static final String CARD_NUMBER = "123454321";
	private static final Date EXPIRATION_DATE = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
	private static final int CVV = 111;
	private static final Time PAYMENT_TIME = java.sql.Time.valueOf(LocalTime.of(9, 05));
	
	private static final PaymentForm PAYMENT_FORM2 = PaymentForm.PayPal;
	private static final Date PAYMENT_DATE2 = java.sql.Date.valueOf(LocalDate.of(2020, Month.AUGUST, 19));
	private static final String CARD_NUMBER2 = "222221";
	private static final Date EXPIRATION_DATE2 = java.sql.Date.valueOf(LocalDate.of(2024, Month.OCTOBER, 19));
	private static final int CVV2 = 121;
	private static final Time PAYMENT_TIME2 = java.sql.Time.valueOf(LocalTime.of(9, 55));
	private static final int PAYMENT_ID2 = 987654321;
	
	private static final int NONEXISTING_ID = 111111111;
	

	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(paymentDao.findPaymentByPaymentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PAYMENT_ID)) {
				Payment payment = new Payment();
				payment.setPaymentId(PAYMENT_ID);
				return payment;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(paymentDao.save(any(Payment.class))).thenAnswer(returnParameterAsAnswer);
	
		lenient().when(paymentDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
	           Payment payment = new Payment();
	           payment.setCardNumber(CARD_NUMBER);
	           payment.setCvv(CVV);
	           payment.setExpirationDate(EXPIRATION_DATE);
	           payment.setPaymentDate(PAYMENT_DATE);
	           payment.setPaymentForm(PAYMENT_FORM);
	           payment.setPaymentId(PAYMENT_ID);
	           payment.setPaymentTime(PAYMENT_TIME);

	           List<Payment> list = new ArrayList<Payment>();
	           list.add(payment);
	           return list;
		});
		
		lenient().when(paymentDao.findAllPaymentByCardNumber(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			Payment payment = new Payment();
			if(invocation.getArgument(0).equals(CARD_NUMBER)) {
				payment.setCvv(CVV);
		        payment.setExpirationDate(EXPIRATION_DATE);
		        payment.setPaymentDate(PAYMENT_DATE);
		        payment.setPaymentForm(PAYMENT_FORM);
		        payment.setPaymentId(PAYMENT_ID);
		        payment.setPaymentTime(PAYMENT_TIME);
		        payment.setCardNumber(CARD_NUMBER);
				List<Payment> allPayments = new ArrayList<>();
				allPayments.add(payment);
				return allPayments;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(paymentDao.findAllPaymentByPaymentDate(any())).thenAnswer((InvocationOnMock invocation) -> {
			Payment payment = new Payment();
			if(invocation.getArgument(0).equals(PAYMENT_DATE)) {
				payment.setCvv(CVV);
		        payment.setExpirationDate(EXPIRATION_DATE);
		        payment.setPaymentDate(PAYMENT_DATE);
		        payment.setPaymentForm(PAYMENT_FORM);
		        payment.setPaymentId(PAYMENT_ID);
		        payment.setCardNumber(CARD_NUMBER);
		        payment.setPaymentTime(PAYMENT_TIME);
				List<Payment> allPayments = new ArrayList<>();
				allPayments.add(payment);
				return allPayments;
			} 
			else {
				return null;
			}
		});
		lenient().when(paymentDao.findAllPaymentByPaymentTime(any())).thenAnswer((InvocationOnMock invocation) -> {
			Payment payment = new Payment();
			if(invocation.getArgument(0).equals(PAYMENT_TIME)) {
				payment.setCvv(CVV);
		        payment.setExpirationDate(EXPIRATION_DATE);
		        payment.setPaymentDate(PAYMENT_DATE);
		        payment.setPaymentForm(PAYMENT_FORM);
		        payment.setPaymentId(PAYMENT_ID);
		        payment.setCardNumber(CARD_NUMBER);
		        payment.setPaymentTime(PAYMENT_TIME);
				List<Payment> allPayments = new ArrayList<>();
				allPayments.add(payment);
				return allPayments;
			} 
			else {
				return null;
			}
		});
		
	}
	
	@Test
	public void testCreatePayment() {

		Payment payment = null;
		try {
			payment = service.createPayment(PAYMENT_FORM2, PAYMENT_DATE2, CARD_NUMBER2, EXPIRATION_DATE2, CVV2, PAYMENT_TIME2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(payment);

		assertEquals(PAYMENT_FORM2, payment.getPaymentForm());
		assertEquals(PAYMENT_DATE2, payment.getPaymentDate());
		assertEquals(CARD_NUMBER2, payment.getCardNumber());
		assertEquals(EXPIRATION_DATE2, payment.getExpirationDate());
		assertEquals(CVV2, payment.getCvv());
		assertEquals(PAYMENT_ID2, payment.getPaymentId());
		assertEquals(PAYMENT_TIME2, payment.getPaymentTime());
	}
	
	@Test
	public void testCreatePaymentNull() {
		Payment payment = null;
		String error = null;
		String theoreticalError="";
		ArrayList<String> nulls = new ArrayList<String>();
		PaymentForm paymentForm;
		Date paymentDate;
		String cardNumber;
		Date expirationDate;
		Time paymentTime;
		int cvv = 000;
		
		for(int i = 0; i<2; i++) {
			if(i ==1) {
				paymentForm = null;
				nulls.add("paymentForm ");
			}
			else paymentForm = PaymentForm.PayPal;
			
			for(int j= 0; j<2; j++) {
				if(j ==1) {
					paymentDate = null;
					nulls.add("paymentDate ");
				}
				else paymentDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
				
				for(int k = 0; k<2; k++) {
					if(k ==1) {
						cardNumber = null;
						nulls.add("cardNumber ");
					}
					else cardNumber = "987654321";
					
					for(int l=0; l<2; l++) {
						if(l ==1) {
							expirationDate = null;
							nulls.add("expirationDate ");
						}
						else expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
						
						for(int m = 0; m<2; m++) {
							if(m ==1) {
								paymentTime = null;	
								nulls.add("paymentTime ");
							}
							else paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
							
							try {
								payment = service.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
							} catch (IllegalArgumentException e) {
								error = e.getMessage();
								for(String s:nulls) {
									theoreticalError += s;
								}
								theoreticalError += "must not be null";
								assertEquals(error, theoreticalError);
							}
							theoreticalError = "";
							nulls.remove("paymentTime ");
						}
						nulls.remove("expirationDate ");
					}
					nulls.remove("cardNumber ");
				}
				nulls.remove("paymentDate ");
			}
			nulls.remove("paymentForm ");
		}
	}


	@Test
	public void testCreatePaymentEmpty() {
		Payment payment = null;
		String error = null;
		String theoreticalError="cardNumber must not be empty";
		
		String cardNumber = " ";

		
		int paymentId = 123;
		
		try {
			payment = service.createPayment(PAYMENT_FORM, PAYMENT_DATE, cardNumber, EXPIRATION_DATE, CVV, PAYMENT_TIME);
		
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(theoreticalError, error);
	}
	
	@Test
	public void testCreatePaymentExpiredCard() {
		Payment payment = null;
		String error = null;
		String theoreticalError="card has expired";
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2022, Month.NOVEMBER, 19));;
		int cvv = 000;
		
		try {

			payment = service.createPayment(PAYMENT_FORM, paymentDate, CARD_NUMBER, expirationDate, CVV, PAYMENT_TIME);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(theoreticalError, error);
	}
	
	@Test
	public void testCreatePaymentInvalidCvv() {
		Payment payment = null;
		String error = null;
		String theoreticalError="Your cvv must be 3 digits as most";
		
		int cvv = 1000;
		
		try {
			payment = service.createPayment(PAYMENT_FORM, PAYMENT_DATE, CARD_NUMBER, EXPIRATION_DATE, cvv, PAYMENT_TIME);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(theoreticalError, error);
	}
	
	@Test
	public void testGetPayment() {
		Payment payment = null;

	    try {
	        payment = service.getPayment(PAYMENT_ID);
	    } catch (IllegalArgumentException e) {
	        fail();
	    }

	    assertNotNull(payment);
	    assertEquals(PAYMENT_ID, payment.getPaymentId());
	}
	
	@Test
	public void testGetPaymentNonExistingId() {
		Payment payment = null;
		String error = "";
		try {
	        payment = service.getPayment(NONEXISTING_ID);
	    } catch (IllegalArgumentException e) {
	        error = e.getMessage();
	    }

	    assertNull(payment);
	    assertEquals(error, "No payment with this ID");
	}
	
	@Test
    public void testGetAllPayments(){
		List<Payment> payments = service.getAllPayments();
		Payment payment = payments.get(0);

        assertEquals(1, payments.size());
        assertNotNull(payment);
        
        assertEquals(PAYMENT_FORM, payment.getPaymentForm());
		assertEquals(PAYMENT_DATE, payment.getPaymentDate());
		assertEquals(CARD_NUMBER, payment.getCardNumber());
		assertEquals(EXPIRATION_DATE, payment.getExpirationDate());
		assertEquals(CVV, payment.getCvv());
		assertEquals(PAYMENT_ID, payment.getPaymentId());
		assertEquals(PAYMENT_TIME, payment.getPaymentTime());
    }
	
	@Test
	public void testGetPaymentByCardNumber() {
		List<Payment> allPayments = service.getAllPaymentsByCardNumber(CARD_NUMBER);
		assertEquals(1, allPayments.size());
		assertEquals(CARD_NUMBER, allPayments.get(0).getCardNumber());
	}

	@Test
	public void testGetPaymentByCardNumberNull() {
		String cardNumber = null;
		String error = null;
		try{
			List<Payment> allPayments = service.getAllPaymentsByCardNumber(cardNumber);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "The card number must not be empty");
	}
	
	@Test
	public void testGetPaymentByCardNumberEmpty() {
		String cardNumber = " ";
		String error = null;
		try{
			List<Payment> allPayments = service.getAllPaymentsByCardNumber(cardNumber);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "The card number must not be empty");
	}
	
	@Test
	public void testGetPaymentByPaymentTime() {
		List<Payment> allPayments = service.getAllPaymentsByPaymentTime(PAYMENT_TIME);
		assertEquals(1, allPayments.size());
		assertEquals(PAYMENT_TIME, allPayments.get(0).getPaymentTime());
	}
	
	@Test
	public void testGetPaymentByPaymentTimeNull() {
		Time paymentTime = null;
		String error = null;
		try{
			List<Payment> allPayments = service.getAllPaymentsByPaymentTime(paymentTime);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "The time must not be empty");
	}
	
	@Test
	public void testGetPaymentByPaymentDate() {
		List<Payment> allPayments = service.getAllPaymentsByPaymentDate(PAYMENT_DATE);
		assertEquals(1, allPayments.size());
		assertEquals(PAYMENT_DATE, allPayments.get(0).getPaymentDate());
	}
	
	@Test
	public void testGetPaymentByPaymentDateNull() {
		Date paymentDate = null;
		String error = null;
		try{
			List<Payment> allPayments = service.getAllPaymentsByPaymentDate(paymentDate);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "The date must not be empty");
	}
	
	 @Test
	 public void testUpdatePayment() {
		 Payment payment = null;
	        
		 PaymentForm paymentForm = PaymentForm.PayPal;
		 Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.MAY, 19));
		 String cardNumber = "8888888";
		 Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 21));
		 int cvv = 321;
		 Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 05));
	
			
	     try {
	         payment = service.updatePayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, PAYMENT_ID, paymentTime);
	     } catch (IllegalArgumentException e) {
	         fail();
	     }
	     assertNotNull(payment);
	     assertEquals(paymentForm, payment.getPaymentForm());
	     assertEquals(paymentDate, payment.getPaymentDate());
	     assertEquals(cardNumber, payment.getCardNumber());
	     assertEquals(expirationDate, payment.getExpirationDate());
	     assertEquals(cvv, payment.getCvv());
	     assertEquals(paymentTime, payment.getPaymentTime());
	 }
	
	 @Test
	 public void testUpdateShipmentNonExistingId() {
	     String error = "";
	     Payment payment = null;
	        
		 PaymentForm paymentForm = PaymentForm.PayPal;
		 Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.MAY, 19));
		 String cardNumber = "8888888";
		 Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 21));
		 int cvv = 321;
		 Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 05));

	     try {
	         payment = service.updatePayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, NONEXISTING_ID, paymentTime);
	     } catch (IllegalArgumentException e) {
	         error = e.getMessage();
	     }
	     assertNull(payment);
	     assertEquals(error, "must enter a payment id that is in the table");
	 }
}	

