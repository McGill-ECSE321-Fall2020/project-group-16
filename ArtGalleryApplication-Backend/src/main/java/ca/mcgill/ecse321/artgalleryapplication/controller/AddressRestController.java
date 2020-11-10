package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.AddressDto;
//import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    /**
     * Get address by ID
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/address/{id}", "/address/{id}/" })
    public AddressDto getAddress(@PathVariable("id") int id) throws IllegalArgumentException {
        Address address = addressService.getAddressById(id);
        return ConvertToDto.convertToDto(address);
    }

    @GetMapping(value = { "/address", "/address/" })
    public List<AddressDto> getAddress() throws IllegalArgumentException {
        return addressService.getAllAddresses().stream().map(ConvertToDto::convertToDto).collect(Collectors.toList());
    }

    /**
     * Create an address
     * @param streetAddress
     * @param streetAddress2
     * @param postalCode
     * @param city
     * @param province
     * @param country
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/address", "/address/" })
    public AddressDto createAddress(
            @RequestParam String streetAddress,
            @RequestParam String streetAddress2,
            @RequestParam String postalCode,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String country)
            throws IllegalArgumentException {

        Address address = addressService.createAddress(streetAddress, streetAddress2, postalCode, city, province, country);
        return ConvertToDto.convertToDto(address);
    }

    /**
     * Delete an address
     * @param id
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = { "/address/{id}", "/address/{id}/" })
    public void deleteAddress(@PathVariable("id") int id) throws IllegalArgumentException {
        addressService.deleteAddress(id);
    }

    /**
     * Update the fields of an address
     * @param id
     * @param streetAddress
     * @param streetAddress2
     * @param postalCode
     * @param city
     * @param province
     * @param country
     * @return
     * @throws IllegalArgumentException
     */
    @PutMapping(value = { "/address/{id}", "/address/{id}/" })
    public AddressDto updateAddress(
            @PathVariable("id") int id,
            @RequestParam String streetAddress,
            @RequestParam String streetAddress2,
            @RequestParam String postalCode,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String country)
            throws IllegalArgumentException {

        Address address = addressService.updateAddress(id, streetAddress, streetAddress2, postalCode, city, province, country);
        return ConvertToDto.convertToDto(address);
    }
}
