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
import ca.mcgill.ecse321.artgalleryapplication.dao.ShipmentRepository;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;

@ExtendWith(MockitoExtension.class)
public class TestShipmentService {
	@Mock
	private ShipmentRepository shipmentDao;
	
	@InjectMocks
	private ShipmentService service;
	
	private static final int SHIPMENT_ID = 123456789;
	private static final String NONEXISTING_KEY = "NotAPerson";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(shipmentDao.findShipmentByShipmentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SHIPMENT_ID)) {
				Shipment shipment = new Shipment();
				shipment.setShipmentId(SHIPMENT_ID);
				return shipment;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(shipmentDao.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateShipment() {
		assertEquals(0, service.getAllShipments().size());

		
		//make attributes
		Boolean toGallery = false;
		Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		int shipmentId = 999999999;
		Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
		Address returnAddress = createReturnAddress();
		Address destinationAddress = createDestinationAddress();
		
		Shipment shipment = null;
		try {
			shipment = service.createShipment(toGallery, estimatedArrivalTime, shipmentId, estimatedArrivalDate, returnAddress, destinationAddress);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(shipment);
		assertEquals(toGallery, shipment.getToGallery());
		assertEquals(estimatedArrivalTime, shipment.getEstimatedArrivalTime());
		assertEquals(shipmentId, shipment.getShipmentId());
		assertEquals(estimatedArrivalDate, shipment.getEstimatedArrivalDate());
		assertEquals(returnAddress, shipment.getReturnAddress());
		assertEquals(destinationAddress, shipment.getDestination());
	}
	
	//helper methods
	public static Address createReturnAddress() {
		String returnStreetAddress = "1001 Test Rd";
		String returnStreetAddress2 = "101";
		String returnPostalCode = "S7N3T5";
		String returnCity = "Test";
		String returnProvince = "TS";
		String returnCountry = "Canada";
		Address returnAddress = new Address();
		returnAddress.setStreetAddress(returnStreetAddress);
		returnAddress.setStreetAddress2(returnStreetAddress2);
		returnAddress.setPostalCode(returnPostalCode);
		returnAddress.setCity(returnCity);
		returnAddress.setProvince(returnProvince);
		returnAddress.setCountry(returnCountry);
		return returnAddress;
	}
	
	public static Address createDestinationAddress() {
		String deliveryStreetAddress = "100 Test Blv";
		String deliveryStreetAddress2 = "100";
		String deliveryPostalCode = "S7N5H5";
		String deliveryCity = "Test1";
		String deliveryProvince = "TE";
		String deliveryCountry = "Canada";
		Address deliveryAddress = new Address();
		deliveryAddress.setStreetAddress(deliveryStreetAddress);
		deliveryAddress.setStreetAddress2(deliveryStreetAddress2);
		deliveryAddress.setPostalCode(deliveryPostalCode);
		deliveryAddress.setCity(deliveryCity);
		deliveryAddress.setProvince(deliveryProvince);
		deliveryAddress.setCountry(deliveryCountry);
		return deliveryAddress;
	}
}
