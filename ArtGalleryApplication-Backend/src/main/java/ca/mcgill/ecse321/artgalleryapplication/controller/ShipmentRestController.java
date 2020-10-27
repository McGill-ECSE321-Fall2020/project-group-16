package ca.mcgill.ecse321.artgalleryapplication.controller;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.PaymentDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.ShipmentDto;

import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.service.ShipmentService;

public class ShipmentRestController {

	@Autowired
	private ShipmentService shipmentService;
	
	
	@PostMapping(value = {"/payments", "/payments/"}) 
	public ShipmentDto createShipment (@RequestParam("toGallery") Boolean toGallery,
									 @RequestParam("estimatedArrivalTime") Time estimatedArrivalTime,
									 @RequestParam("shipmentId") int shipmentId,
									 @RequestParam("estimatedArrivalDate") Date estimatedArrivalDate,
									 @RequestParam("returnAddress") Address returnAddress,
									 @RequestParam("destinationAddress") Address destinationAddress)
									 throws IllegalArgumentException{
		
		Shipment shipment = shipmentService.createShipment(toGallery, estimatedArrivalTime, shipmentId, estimatedArrivalDate, returnAddress, destinationAddress);
		
		return ConvertToDto.convertToDto(shipment);
	}
	
	@GetMapping(value = {"/shipments/{id}", "/shipments/{id}/"})	
	public ShipmentDto getShipmentById(@PathVariable("id") int id) {
		Shipment s = shipmentService.getShipment(id);
		return ConvertToDto.convertToDto(s);
	}
	
	@GetMapping(value = {"/shipments", "/shipments/"})
	public List<ShipmentDto> getAllShipments() {
		List<ShipmentDto> shipments = new ArrayList<>();
	
		for(Shipment s : shipmentService.getAllShipments()) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byToGallery", "/shipments/byToGallery/"})
	public List<ShipmentDto> getAllShipmentsByToGallery(@RequestParam("toGallery") Boolean toGallery) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByToGallery(toGallery)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byEstimatedArrivalTime", "/shipments/byEstimatedArrivalTime/"})
	public List<ShipmentDto> getAllShipmentsByEstimatedArrivalTime(@RequestParam("estimatedArrivalTime") Time estimatedArrivalTime) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByEstimatedArrivalTime(estimatedArrivalTime)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byEstimatedArrivalDate", "/shipments/byEstimatedArrivalDate/"})
	public List<ShipmentDto> getAllShipmentsByEstimatedArrivalDate(@RequestParam("estimatedArrivalDate") Date estimatedArrivalDate) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByEstimatedArrivalDate(estimatedArrivalDate)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byReturnAddress", "/shipments/byReturnAddress/"})
	public List<ShipmentDto> getAllShipmentsByReturnAddress(@RequestParam("returnAddress") Address returnAddress) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByReturnAddress(returnAddress)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byDestinationAddress", "/shipments/byDestinationAddress/"})
	public List<ShipmentDto> getAllShipmentsByDestinationAddress(@RequestParam("destinationAddress") Address destinationAddress) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByDestinationAddress(destinationAddress)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	 @DeleteMapping(value = { "/shipments/{id}", "/shipments/{id}/" })
	    public void deleteShipment(@PathVariable("id") Integer id) throws IllegalArgumentException {
	        shipmentService.deleteShipment(id);
	    }
	
}
