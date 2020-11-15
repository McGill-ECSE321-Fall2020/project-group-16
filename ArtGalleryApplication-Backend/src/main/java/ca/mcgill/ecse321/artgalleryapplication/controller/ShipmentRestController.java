package ca.mcgill.ecse321.artgalleryapplication.controller;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.artgalleryapplication.dto.AddressDto;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import ca.mcgill.ecse321.artgalleryapplication.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.PaymentDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.ShipmentDto;

import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.service.ShipmentService;


@CrossOrigin(origins = "*")
@RestController
public class ShipmentRestController {

	@Autowired
	private ShipmentService shipmentService;
	@Autowired
	private AddressService addressService;



	@PostMapping(value = {"/shipments", "/shipments/"})
	public ShipmentDto createShipment (@RequestParam("toGallery") Boolean toGallery,
									 @RequestParam("estimatedArrivalTime") Time estimatedArrivalTime,
									 @RequestParam("estimatedArrivalDate") Date estimatedArrivalDate,
									 @RequestParam("returnAddressId") int returnAddressId,
									 @RequestParam("destinationAddressId") int destinationAddressId)
									 throws ApiRequestException {
		
		Shipment shipment = shipmentService.createShipment(toGallery, estimatedArrivalTime, estimatedArrivalDate, returnAddressId, destinationAddressId);
		
		return ConvertToDto.convertToDto(shipment);
	}

	@PostMapping(value = {"/shipments/full", "/shipments/full/"})
	public ShipmentDto createShipment (@RequestParam("toGallery") Boolean toGallery,
									   @RequestParam("estimatedArrivalTime") Time estimatedArrivalTime,
									   @RequestParam("estimatedArrivalDate") Date estimatedArrivalDate,
									   @RequestParam("destStreetAddress") String destStreetAddress,
									   @RequestParam("destStreetAddress2") String destStreetAddress2,
									   @RequestParam("destPostalCode") String destPostalCode,
									   @RequestParam("destCity") String destCity,
									   @RequestParam("destProvince") String destProvince,
									   @RequestParam("destCountry") String destCountry
									   )
			throws ApiRequestException{

		AddressDto destAddressDto = convertToDto(addressService.createAddress(destStreetAddress, destStreetAddress2, destPostalCode, destCity, destProvince, destCountry));

		return convertToDto(shipmentService.createShipment(toGallery, estimatedArrivalTime, estimatedArrivalDate, 0, destAddressDto.getAddressId()));
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
	
	
	@GetMapping(value = {"/shipments/byEstimatedArrivalDate", "/shipments/byEstimatedArrivalDate/"})
	public List<ShipmentDto> getAllShipmentsByEstimatedArrivalDate(@RequestParam("estimatedArrivalDate") Date estimatedArrivalDate) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByEstimatedArrivalDate(estimatedArrivalDate)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byReturnAddress", "/shipments/byReturnAddress/"})
	public List<ShipmentDto> getAllShipmentsByReturnAddress(@RequestParam("returnAddressId") int returnAddressId) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByReturnAddress(returnAddressId)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	@GetMapping(value = {"/shipments/byDestinationAddress", "/shipments/byDestinationAddress/"})
	public List<ShipmentDto> getAllShipmentsByDestinationAddress(@RequestParam("destinationAddressId") int destinationAddressId) {
		List<ShipmentDto> shipments = new ArrayList<>();
		for(Shipment s : shipmentService.getAllShipmentsByDestinationAddress(destinationAddressId)) {
			shipments.add(ConvertToDto.convertToDto(s));
		}
		return shipments;
	}
	
	 @DeleteMapping(value = { "/shipments/{id}", "/shipments/{id}/" })
	    public void deleteShipment(@PathVariable("id") Integer id) throws ApiRequestException {
	        shipmentService.deleteShipment(id);
	    }
	
}
