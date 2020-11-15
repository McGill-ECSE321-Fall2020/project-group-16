package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
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
    public Shipment createShipment(Boolean toGallery, Time estimatedArrivalTime, Date estimatedArrivalDate, int returnAddressId, int destinationAddressId) {
    	List<String> nulls = new ArrayList<>();
    	if(toGallery == null) {
    		nulls.add("toGallery ");
    	}
    	if(estimatedArrivalTime == null) {
    		nulls.add("estimatedArrivalTime ");
    	}
    	if(estimatedArrivalDate == null) {
    		nulls.add("estimatedArrivalDate ");
    	}
		Address destinationAddress = addressRepository.findAddressByAddressId(destinationAddressId);
    	if(destinationAddress == null) {
    		nulls.add("destinationAddress ");
    	}
    	
    	if(nulls.size()>0) {
    		String errors = "";
    		for (String e:nulls){
    			errors += e;
    		}
    		errors += "must not be null";
    		throw new ApiRequestException(errors);
    	}

    	Shipment s = new Shipment();
    	s.setDestination(destinationAddress);
    	s.setEstimatedArrivalDate(estimatedArrivalDate);
    	s.setEstimatedArrivalTime(estimatedArrivalTime);
    	s.setToGallery(toGallery);
    	
    	shipmentRepository.save(s);
    	return s;
    }
    
    @Transactional
    public Shipment getShipment(int shipmentId) {
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)==null) {
    		throw new ApiRequestException("No shipment with this ID");
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
    		throw new ApiRequestException("Must enter a Date variable");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByEstimatedArrivalDate(arrivalDate);
    	if(s.isEmpty()) {
    		throw new ApiRequestException("No shipments made with this estimated arrival date");
    	}
    	return s;
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByReturnAddress(int id){
		Address returnAddress = addressRepository.findAddressByAddressId(id);
    	if( returnAddress == null) {
    		throw new ApiRequestException("Must enter a return address");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByReturnAddress(returnAddress);
    	if(s.isEmpty()) {
    		throw new ApiRequestException("No shipments made with this return address");
    	}
    	return s;
    }
    
    @Transactional
    public List<Shipment> getAllShipmentsByDestinationAddress(int id){
		Address destinationAddress = addressRepository.findAddressByAddressId(id);
    	if(destinationAddress == null) {
    		throw new ApiRequestException("Must enter a destination address");
    	}
    	
    	List<Shipment> s = shipmentRepository.findShipmentByDestination(destinationAddress);
    	if(s.isEmpty()) {
    		throw new ApiRequestException("No shipments made to this destination");
    	}
    	return s;
    }
    
    @Transactional
    public Shipment updateShipment(Boolean toGal, Time eta, int shipmentId, Date estimatedArrival, Address r, Address d) {
    	if(shipmentRepository.findShipmentByShipmentId(shipmentId)==null) {
    		throw new ApiRequestException("must enter a shipment id that is in the table");
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
    		throw new ApiRequestException("No shipment with this ID exists");
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
