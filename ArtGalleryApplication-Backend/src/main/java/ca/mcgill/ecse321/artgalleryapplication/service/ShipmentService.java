package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private GalleryEventRepository galleryEventRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArtGalleryApplicationRepository artGalleryApplicationRepository;



    @Transactional
    public Shipment createShipment(Boolean toGallery, Time estimatedArrivalTime, int shipmentId, Date estimatedArrivalDate, Address returnAddress, Address destinationAddress) {
    	List<String> nulls = new ArrayList<String>();
    	if(toGallery == null) {
    		nulls.add("toGallery ");
    	}
    	if(estimatedArrivalTime == null) {
    		nulls.add("estimatedArrivalTime ");
    	}
    	if(estimatedArrivalDate == null) {
    		nulls.add("estimatedArrivalDate ");
    	}
    	if(returnAddress == null) {
    		nulls.add("returnAddress ");
    	}
    	if(destinationAddress == null) {
    		nulls.add("destinationAddress ");
    	}
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)!= null) {
    		throw new IllegalArgumentException("A shipment with this shipment ID already exists");
    	}
    	
    	if(nulls.size()>0) {
    		String errors = "";
    		for (String e:nulls){
    			errors += e;
    		}
    		errors += "must not be null";
    		throw new IllegalArgumentException(errors);
    	}
    	
    	if(equalAddresses(destinationAddress, returnAddress)) {
    		throw new IllegalArgumentException("must have different destination and return addresses");
    	}
    	Shipment s = new Shipment();
    	s.setDestination(destinationAddress);
    	s.setEstimatedArrivalDate(estimatedArrivalDate);
    	s.setEstimatedArrivalTime(estimatedArrivalTime);
    	s.setReturnAddress(returnAddress);
    	s.setShipmentId(shipmentId);
    	s.setToGallery(toGallery);
    	
    	shipmentRepository.save(s);
    	return s;
    }
    
    @Transactional
    public Shipment getShipment(int shipmentId) {
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)==null) {
    		throw new IllegalArgumentException("No shipment with this ID");
    	}
    	Shipment s = shipmentRepository.findShipmentByShipmentId(shipmentId);
    	return s;
    }

    @Transactional
    public List<Shipment> getAllShipments(){
    	return toList(shipmentRepository.findAll());
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByEstimatedArrivalDate(Date arrivalDate){
    	if(arrivalDate == null) {
    		throw new IllegalArgumentException("Must enter a Date variable");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByEstimatedArrivalDate(arrivalDate);
    	if(s.isEmpty()) {
    		throw new IllegalArgumentException("No shipments made with this estimated arrival date");
    	}
    	return s;
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByReturnAddress(Address r){
    	if(r == null) {
    		throw new IllegalArgumentException("Must enter a return address");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByReturnAddress(r);
    	if(s.isEmpty()) {
    		throw new IllegalArgumentException("No shipments made with this return address");
    	}
    	return s;
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByDestinationAddress(Address d){
    	if(d == null) {
    		throw new IllegalArgumentException("Must enter a destination address");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByDestination(d);
    	if(s.isEmpty()) {
    		throw new IllegalArgumentException("No shipments made to this destination");
    	}
    	return s;
    }
    
    @Transactional
    public Shipment updateShipment(Boolean toGal, Time eta, int shipmentId, Date estimatedArrival, Address r, Address d) {
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)==null) {
    		throw new IllegalArgumentException("must enter a shipment id that is in the table");
    	}
    	
    	Shipment s = shipmentRepository.findShipmentByShipmentId(shipmentId);
    	if(toGal!= null) {
    		s.setToGallery(toGal);
    	} if (eta != null) {
    		s.setEstimatedArrivalTime(eta);
    	} if (estimatedArrival != null) {
    		s.setEstimatedArrivalDate(estimatedArrival);
    	} if (r != null) {
    		s.setReturnAddress(r);
    	} if(d != null){
    		s.setDestination(d);
    	}				
    	shipmentRepository.save(s);
    	return s;
    }

    @Transactional
    public void deleteShipment(int shipmentId) {
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId) == null) {
    		throw new IllegalArgumentException("No shipment with this ID exists");
    	}
    	Shipment s = shipmentRepository.findShipmentByShipmentId(shipmentId);
    	shipmentRepository.delete(s);
    }
    
    //helper methods

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
    
    private static Boolean equalAddresses(Address a1, Address a2) {
    	if(!a1.getStreetAddress().equals(a2.getStreetAddress())) {
    		return false;
    	} if(!a1.getStreetAddress2().equals(a2.getStreetAddress2())) {
    		return false;
    	} if(!a1.getPostalCode().equals(a2.getPostalCode())) {
    		return false;
    	} if(!a1.getCity().equals(a2.getCity())) {
    		return false;
    	} if(!a1.getProvince().equals(a2.getProvince())) {
    		return false;
    	} if(!a1.getCountry().equals(a2.getCountry())) {
    		return false;
    	}
    	
    	return true;
    	
    }
    
}
