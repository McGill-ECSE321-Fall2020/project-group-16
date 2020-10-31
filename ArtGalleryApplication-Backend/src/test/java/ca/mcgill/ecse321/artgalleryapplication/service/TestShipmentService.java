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
	private static final Address DESTINATION = createDestinationAddress();
	private static final Address RETURN_ADDRESS	 = createReturnAddress();
	private static final Boolean TO_GALLERY = false;
	private static final Time ESTIMATED_ARRIVAL_TIME = java.sql.Time.valueOf(LocalTime.of(11, 35));
	private static final Date ESTIMATED_ARRIVAL_DATE = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
	
	private static final int SHIPMENT_ID2 = 88888888;
	private static final Address DESTINATION2 = createDestinationAddress();
	private static final Address RETURN_ADDRESS2 = createReturnAddress();
	private static final Boolean TO_GALLERY2 = false;
	private static final Time ESTIMATED_ARRIVAL_TIME2 = java.sql.Time.valueOf(LocalTime.of(9, 05));
	private static final Date ESTIMATED_ARRIVAL_DATE2 = java.sql.Date.valueOf(LocalDate.of(2020, Month.DECEMBER, 19));
	
	private static final int NONEXISTING_ID = 111111111;
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(shipmentDao.findShipmentByShipmentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SHIPMENT_ID)) {
				Shipment shipment = new Shipment();
				shipment.setShipmentId(SHIPMENT_ID);
				shipment.setDestination(DESTINATION);
				shipment.setToGallery(TO_GALLERY);
				shipment.setReturnAddress(RETURN_ADDRESS);
				shipment.setEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
				shipment.setEstimatedArrivalTime(ESTIMATED_ARRIVAL_TIME);
				return shipment;
			} else {
				return null;
			}
		});
		
		lenient().when(shipmentDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
	           Shipment shipment = new Shipment();
	           shipment.setShipmentId(SHIPMENT_ID);
	           shipment.setDestination(DESTINATION);
	           shipment.setToGallery(TO_GALLERY);
	           shipment.setReturnAddress(RETURN_ADDRESS);
	           shipment.setEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
	           shipment.setEstimatedArrivalTime(ESTIMATED_ARRIVAL_TIME);

	           List<Shipment> list = new ArrayList<Shipment>();
	           list.add(shipment);
	           return list;
		});
		
		lenient().when(shipmentDao.findShipmentByEstimatedArrivalDate(any())).thenAnswer((InvocationOnMock invocation) -> {
			Shipment shipment = new Shipment();
			if(invocation.getArgument(0).equals(ESTIMATED_ARRIVAL_DATE)) {
				shipment.setShipmentId(SHIPMENT_ID);
		        shipment.setDestination(DESTINATION);
		        shipment.setToGallery(TO_GALLERY);
		        shipment.setReturnAddress(RETURN_ADDRESS);
		        shipment.setEstimatedArrivalTime(ESTIMATED_ARRIVAL_TIME);
		        shipment.setEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
				List<Shipment> allShipments = new ArrayList<>();
				allShipments.add(shipment);
				return allShipments;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(shipmentDao.findShipmentByReturnAddress(any())).thenAnswer((InvocationOnMock invocation) -> {
			Shipment shipment = new Shipment();
			if(invocation.getArgument(0).equals(RETURN_ADDRESS)) {
				shipment.setShipmentId(SHIPMENT_ID);
		        shipment.setDestination(DESTINATION);
		        shipment.setToGallery(TO_GALLERY);
		        shipment.setEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
		        shipment.setEstimatedArrivalTime(ESTIMATED_ARRIVAL_TIME);
		        shipment.setReturnAddress(RETURN_ADDRESS);
				List<Shipment> allShipments = new ArrayList<>();
				allShipments.add(shipment);
				return allShipments;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(shipmentDao.findShipmentByDestination(any())).thenAnswer((InvocationOnMock invocation) -> {
			Shipment shipment = new Shipment();
			if(invocation.getArgument(0).equals(DESTINATION)) {
				shipment.setShipmentId(SHIPMENT_ID);
		        shipment.setReturnAddress(RETURN_ADDRESS);
		        shipment.setToGallery(TO_GALLERY);
		        shipment.setEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
		        shipment.setEstimatedArrivalTime(ESTIMATED_ARRIVAL_TIME);
		        shipment.setDestination(DESTINATION);
				List<Shipment> allShipments = new ArrayList<>();
				allShipments.add(shipment);
				return allShipments;
			} 
			else {
				return null;
			}
		});
		
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(shipmentDao.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
		
	}
	
	@Test
	public void testCreateShipment() {
		
		Shipment shipment = null;
		try {
			shipment = service.createShipment(TO_GALLERY2, ESTIMATED_ARRIVAL_TIME2, SHIPMENT_ID2, ESTIMATED_ARRIVAL_DATE2, RETURN_ADDRESS2, DESTINATION2);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(shipment);
		assertEquals(TO_GALLERY2, shipment.getToGallery());
		assertEquals(ESTIMATED_ARRIVAL_TIME2, shipment.getEstimatedArrivalTime());
		assertEquals(SHIPMENT_ID2, shipment.getShipmentId());
		assertEquals(ESTIMATED_ARRIVAL_DATE2, shipment.getEstimatedArrivalDate());
		assertEquals(RETURN_ADDRESS2, shipment.getReturnAddress());
		assertEquals(DESTINATION2, shipment.getDestination());
	}
	
	@Test
	public void testCreateShipmentNull() {
		Shipment shipment = null;
		String error = null;
		String theoreticalError="";
		ArrayList<String> nulls = new ArrayList<String>();
		Boolean toGallery;
		Time estimatedArrivalTime;
		Date estimatedArrivalDate;
		Address returnAddress;
		Address destinationAddress;
		int shipmentId = 555555555;
		
		for(int i = 0; i<2; i++) {
			if(i ==1) {
				toGallery = null;
				nulls.add("toGallery ");
			}
			else toGallery = false;
			
			for(int j= 0; j<2; j++) {
				if(j ==1) {
					estimatedArrivalTime = null;
					nulls.add("estimatedArrivalTime ");
				}
				else estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
				
				for(int k = 0; k<2; k++) {
					if(k ==1) {
						estimatedArrivalDate = null;
						nulls.add("estimatedArrivalDate ");
					}
					else estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
					
					for(int l=0; l<2; l++) {
						if(l ==1) {
							returnAddress = null;
							nulls.add("returnAddress ");
						}
						else returnAddress = createReturnAddress();
						
						for(int m = 0; m<2; m++) {
							if(m ==1) {
								destinationAddress = null;	
								nulls.add("destinationAddress ");
							}
							else destinationAddress = createDestinationAddress();
							
							try {
								shipment = service.createShipment(toGallery, estimatedArrivalTime, shipmentId, estimatedArrivalDate, returnAddress, destinationAddress);
							} catch (IllegalArgumentException e) {
								error = e.getMessage();
								for(String s:nulls) {
									theoreticalError += s;
								}
								theoreticalError += "must not be null";
								assertEquals(error, theoreticalError);
							}
							theoreticalError = "";
							nulls.remove("destinationAddress ");
						}
						nulls.remove("returnAddress ");
					}
					nulls.remove("estimatedArrivalDate ");
				}
				nulls.remove("estimatedArrivalTime ");
			}
			nulls.remove("toGallery ");
		}
	}
	
	@Test
	public void testCreateShipmentInvalidAddresses() {
		Shipment shipment = null;
		String error = null;
		String theoreticalError = "must have different destination and return addresses";
		Boolean toGallery = false;
		Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		int shipmentId = 999999999;
		Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
		Address returnAddress = createReturnAddress();
		Address destinationAddress = createReturnAddress();
		try {
			shipment = service.createShipment(toGallery, estimatedArrivalTime, shipmentId, estimatedArrivalDate, returnAddress, destinationAddress);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(theoreticalError, error);
	}
	
	@Test
	public void testDeleteShipment() {
		try {
			service.deleteShipment(SHIPMENT_ID);
		} catch(IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteShipmentNonExistingId() {
		String error="";
		try {
			service.deleteShipment(NONEXISTING_ID);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "No shipment with this ID exists");
	}
	
	@Test
	public void testGetShipment() {
		Shipment shipment = null;

	    try {
	        shipment = service.getShipment(SHIPMENT_ID);
	    } catch (IllegalArgumentException e) {
	        fail();
	    }

	    assertNotNull(shipment);
	    assertEquals(SHIPMENT_ID, shipment.getShipmentId());
	}
	  
	@Test
	public void testGetShipmentNonExistingId() {
		Shipment shipment = null;
		String error = "";
		try {
	        shipment = service.getShipment(NONEXISTING_ID);
	    } catch (IllegalArgumentException e) {
	        error = e.getMessage();
	    }

	    assertNull(shipment);
	    assertEquals(error, "No shipment with this ID");
	}
	  
	//get all shipments
	@Test
    public void testGetAllShipments(){
		List<Shipment> shipments = service.getAllShipments();
		Shipment shipment = shipments.get(0);

        assertEquals(1, shipments.size());
        assertNotNull(shipment);
        assertEquals(SHIPMENT_ID, shipment.getShipmentId());
        assertEquals(DESTINATION, shipment.getDestination());
        assertEquals(RETURN_ADDRESS, shipment.getReturnAddress());
        assertEquals(ESTIMATED_ARRIVAL_TIME, shipment.getEstimatedArrivalTime());
        assertEquals(ESTIMATED_ARRIVAL_DATE, shipment.getEstimatedArrivalDate());
        assertEquals(TO_GALLERY, shipment.getToGallery());
    }
	
	
	//get all by estimated arrival date\
	@Test
	public void testGetShipmentByEstimatedArrivalDate() {
		List<Shipment> allShipments = service.getAllShipmentsByEstimatedArrivalDate(ESTIMATED_ARRIVAL_DATE);
		assertEquals(1, allShipments.size());
		assertEquals(ESTIMATED_ARRIVAL_DATE, allShipments.get(0).getEstimatedArrivalDate());
	}
	
	@Test
	public void testGetShipmentByEstimatedArrivalDateNull() {
		Date estimatedArrival = null;
		String error = null;
		try {
			List<Shipment> allShipments = service.getAllShipmentsByEstimatedArrivalDate(estimatedArrival);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Must enter a Date variable");
	}
	
	//get all by return address
	@Test
	public void testGetShipmentByReturnAddress() {
		List<Shipment> allShipments = service.getAllShipmentsByReturnAddress(RETURN_ADDRESS);
		assertEquals(1, allShipments.size());
		assertEquals(RETURN_ADDRESS, allShipments.get(0).getReturnAddress());
	}
	
	@Test
	public void testGetShipmentByReturnAddressNull() {
		Address returnAddress = null;
		String error = null;
		try {
			List<Shipment> allShipments = service.getAllShipmentsByReturnAddress(returnAddress);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Must enter a return address");
	}
	
	
	//get all by destination address
	
	@Test
	public void testGetShipmentByDestination() {
		List<Shipment> allShipments = service.getAllShipmentsByDestinationAddress(DESTINATION);
		assertEquals(1, allShipments.size());
		assertEquals(DESTINATION, allShipments.get(0).getDestination());
	}
	
	@Test
	public void testGetShipmentByDestinationNull() {
		Address destination = null;
		String error = null;
		try {
			List<Shipment> allShipments = service.getAllShipmentsByDestinationAddress(destination);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Must enter a destination address");
	}
	
	//update
	 @Test
	 public void testUpdateShipment() {
		 Shipment shipment = null;
	        
		 Boolean toGallery = true;
		 Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(9, 05));
		 Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.DECEMBER, 19));
		 Address returnAddress = createReturnAddress();
		 returnAddress.setStreetAddress("123 Test Lane");
		 Address destinationAddress = createDestinationAddress();
		 destinationAddress.setStreetAddress("22 new destination blvd");

	     try {
	         shipment = service.updateShipment(toGallery, estimatedArrivalTime, SHIPMENT_ID, estimatedArrivalDate, returnAddress, destinationAddress);
	     } catch (IllegalArgumentException e) {
	         fail();
	     }
	     assertNotNull(shipment);
	     assertEquals(toGallery, shipment.getToGallery());
	     assertEquals(estimatedArrivalTime, shipment.getEstimatedArrivalTime());
	     assertEquals(estimatedArrivalDate, shipment.getEstimatedArrivalDate());
	     assertEquals(returnAddress, shipment.getReturnAddress());
	     assertEquals(destinationAddress, shipment.getDestination());
	 }
	
	 @Test
	 public void testUpdateShipmentNonExistingId() {
		 Shipment shipment = null;
	     String error = "";
		 Boolean toGallery = true;
		 Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(9, 05));
		 Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.DECEMBER, 19));
		 Address returnAddress = createReturnAddress();
		 returnAddress.setStreetAddress("123 Test Lane");
		 Address destinationAddress = createDestinationAddress();
		 destinationAddress.setStreetAddress("22 new destination blvd");

	     try {
	         shipment = service.updateShipment(toGallery, estimatedArrivalTime, NONEXISTING_ID, estimatedArrivalDate, returnAddress, destinationAddress);
	     } catch (IllegalArgumentException e) {
	         error = e.getMessage();
	     }
	     assertNull(shipment);
	     assertEquals(error, "must enter a shipment id that is in the table");
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
