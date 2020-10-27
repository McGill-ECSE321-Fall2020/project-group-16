package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.AddressDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;

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

    @DeleteMapping(value = { "/address/{id}", "/address/{id}/" })
    public void deleteAddress(@PathVariable("id") int id) throws IllegalArgumentException {
        addressService.deleteAddress(id);
    }
}
