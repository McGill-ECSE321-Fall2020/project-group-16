package ca.mcgill.ecse321.artgalleryapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.PaymentRepository;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;

@ExtendWith(MockitoExtension.class)
public class TestPaymentService {
	@Mock
	private PaymentRepository paymentDao;
	
	@InjectMocks
	private PaymentService service;
	
	private static final int PAYMENT_ID = 123456789;
	private static final String NONEXISTING_KEY = "NotAPerson";
	
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
		//lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreatePayment() {
		assertEquals(0, service.getAllPayments().size());

		//make attributes
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
		String cardNumber = "987654321";
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
		int cvv = 123;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Payment payment = null;
		try {
			payment = service.createPayment(PaymentForm.CreditCard, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(payment);
		assertEquals(PaymentForm.CreditCard, payment.getPaymentForm());
		assertEquals(cardNumber, payment.getCardNumber());
		assertEquals(expirationDate, payment.getExpirationDate());
		assertEquals(cvv, payment.getCvv());
		assertEquals(paymentTime, payment.getPaymentTime());
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
		PaymentForm paymentForm = PaymentForm.CreditCard;
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		String cardNumber = " ";
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));;
		int cvv = 000;
		
		try {
			payment = service.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
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
		PaymentForm paymentForm = PaymentForm.CreditCard;
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		String cardNumber = "2222222";
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2022, Month.NOVEMBER, 19));;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));;
		int cvv = 000;
		
		try {
			payment = service.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
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
		PaymentForm paymentForm = PaymentForm.CreditCard;
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		String cardNumber = "2222222";
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));;
		int cvv = 1000;
		
		try {
			payment = service.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(theoreticalError, error);
	}
	
}	
