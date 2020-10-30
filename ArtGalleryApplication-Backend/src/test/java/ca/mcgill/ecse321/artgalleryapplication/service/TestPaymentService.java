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
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

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
		int paymentId = 111111111;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Payment payment = null;
		try {
			payment = service.createPayment(PaymentForm.CreditCard, paymentDate, cardNumber, expirationDate, cvv, paymentId, paymentTime);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(payment);
		assertEquals(PaymentForm.CreditCard, payment.getPaymentForm());
		assertEquals(cardNumber, payment.getCardNumber());
		assertEquals(expirationDate, payment.getExpirationDate());
		assertEquals(cvv, payment.getCvv());
		assertEquals(paymentId, payment.getPaymentId());
		assertEquals(paymentTime, payment.getPaymentTime());
	}

}

	

/*	@Test
	public void testCreatePaymentNull() {
		String name = null;
		String error = null;
		Person person = null;
		try {
			person = service.createPayment(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(person);
		// check error
		assertEquals("Person name cannot be empty!", error);
	}

	@Test
	public void testCreatePersonEmpty() {
		String name = "";
		String error = null;
		Person person = null;
		try {
			person = service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(person);
		// check error
		assertEquals("Person name cannot be empty!", error);
	}

	@Test
	public void testCreatePersonSpaces() {
		String name = " ";
		String error = null;
		Person person = null;
		try {
			person = service.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(person);
		// check error
		assertEquals("Person name cannot be empty!", error);
	}

	@Test
	public void testCreateEvent() {
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		LocalTime endTime = LocalTime.parse("10:30");
		Event event = null;
		try {
			event = service.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(event);
		checkResultEvent(event, name, eventDate, startTime, endTime);
	}

	private void checkResultEvent(Event event, String name, Date eventDate, LocalTime startTime, LocalTime endTime) {
		assertNotNull(event);
		assertEquals(name, event.getName());
		assertEquals(eventDate.toString(), event.getDate().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		assertEquals(startTime.format(formatter).toString(), event.getStartTime().toString());
		assertEquals(endTime.format(formatter).toString(), event.getEndTime().toString());
	}

	@Test
	public void testRegister() {
		String nameP = "Oscar2";
		String nameE = "Soccer Game2";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		Time endTime = new Time(c.getTimeInMillis());
		Person person = null;
		person = service.createPerson(nameP);
		Event event = null;
		event = service.createEvent(nameE, eventDate, startTime, endTime);
		lenient().when(personDao.existsById(anyString())).thenReturn(true);
		lenient().when(eventDao.existsById(anyString())).thenReturn(true);
		Registration registration = null;
		try {
			registration = service.register(person, event);
		} catch (IllegalArgumentException e) {
			fail();
		}

		checkResultRegister(registration, nameP, nameE, eventDate, startTime, endTime);
	}

	private void checkResultRegister(Registration registration, String nameP, String nameE, Date eventDate,
			Time startTime, Time endTime) {
		assertNotNull(registration);
		assertEquals(nameP, registration.getPerson().getName());
		assertEquals(nameE, registration.getEvent().getName());
		assertEquals(eventDate.toString(), registration.getEvent().getDate().toString());
		assertEquals(startTime.toString(), registration.getEvent().getStartTime().toString());
		assertEquals(endTime.toString(), registration.getEvent().getEndTime().toString());
	}

	@Test
	public void testCreateEventNull() {
		String name = null;
		Date eventDate = null;
		Time startTime = null;
		Time endTime = null;

		String error = null;
		Event event = null;
		try {
			event = service.createEvent(name, eventDate, startTime, endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(event);
		// check error
		assertEquals(
				"Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!",
				error);
	}

	@Test
	public void testCreateEventEmpty() {
		String name = "";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.FEBRUARY, 16, 10, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("10:00");
		c.set(2017, Calendar.FEBRUARY, 16, 11, 30, 0);
		LocalTime endTime = LocalTime.parse("11:30");

		String error = null;
		Event event = null;
		try {
			event = service.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(event);
		// check error
		assertEquals("Event name cannot be empty!", error);
	}

	@Test
	public void testCreateEventSpaces() {
		String name = " ";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
		LocalTime endTime = LocalTime.parse("10:30");

		String error = null;
		Event event = null;
		try {
			event = service.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(event);
		// check error
		assertEquals("Event name cannot be empty!", error);
	}

	@Test
	public void testCreateEventEndTimeBeforeStartTime() {
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2016, Calendar.OCTOBER, 16, 8, 59, 59);
		LocalTime endTime = LocalTime.parse("08:59");

		String error = null;
		Event event = null;
		try {
			event = service.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(event);
		// check error
		assertEquals("Event end time cannot be before event start time!", error);
	}

	@Test
	public void testRegisterNull() {
		Person person = null;
		assertEquals(0, service.getAllPersons().size());

		Event event = null;
		assertEquals(0, service.getAllEvents().size());

		String error = null;
		Registration registration = null;
		try {
			registration = service.register(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(registration);
		// check error
		assertEquals("Person needs to be selected for registration! Event needs to be selected for registration!",
				error);
	}

	@Test
	public void testRegisterPersonAndEventDoNotExist() {
		String nameP = "Oscar";
		Person person = new Person();
		person.setName(nameP);
		assertEquals(0, service.getAllPersons().size());

		String nameE = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
		Time endTime = new Time(c.getTimeInMillis());
		Event event = new Event();
		event.setName(nameE);
		event.setDate(eventDate);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		assertEquals(0, service.getAllEvents().size());

		String error = null;
		Registration registration = null;
		try {
			registration = service.register(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(registration);
		// check error
		assertEquals("Person does not exist! Event does not exist!", error);
	}

	@Test
	public void testGetExistingPerson() {
		assertEquals(PERSON_KEY, service.getPerson(PERSON_KEY).getName());
	}

	@Test
	public void testGetNonExistingPerson() {
		assertNull(service.getPerson(NONEXISTING_KEY));
	}

}*/