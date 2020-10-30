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
    	List<String> errors = new ArrayList<String>();
    	if(toGallery == null) {
    		errors.add("toGallery variable must not be null");
    	}
    	if(estimatedArrivalTime == null) {
    		errors.add("ETA must not be null");
    	}
    	if(estimatedArrivalDate == null) {
    		errors.add("Estimated arrival date must not be null");
    	}
    	if(returnAddress == null) {
    		errors.add("Return address must not be null");
    	}
    	if(destinationAddress == null) {
    		errors.add("Destination address must not be null");
    	}
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)!= null) {
    		throw new IllegalArgumentException("A shipment with this shipment ID already exists");
    	}
    	if(errors.size()>0) {
    		for (String e:errors){
    			throw new IllegalArgumentException(e);
    		}
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
    	Shipment s = shipmentRepository.findShipmentByShipmentId(shipmentId);
    	return s;
    }

    @Transactional
    public List<Shipment> getAllShipments(){
    	return toList(shipmentRepository.findAll());
    }

    @Transactional
    public List<Shipment> getAllShipmentsByToGallery(Boolean toGal){
    	if(toGal == null) {
    		throw new IllegalArgumentException("Must enter a boolean variable");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByToGallery(toGal);
    	if(s.isEmpty()) {
    		throw new IllegalArgumentException("No shipments made with this toGallery value");
    	}
    	return s;
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByEstimatedArrivalTime(Time eta){
    	if(eta == null) {
    		throw new IllegalArgumentException("Must enter a Time variable");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByEstimatedArrivalTime(eta);
    	if(s.isEmpty()) {
    		throw new IllegalArgumentException("No shipments made with this eta");
    	}
    	return s;
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
}
