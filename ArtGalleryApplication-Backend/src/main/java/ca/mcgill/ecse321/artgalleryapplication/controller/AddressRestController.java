package ca.mcgill.ecse321.artgalleryapplication.controller;
import ca.mcgill.ecse321.artgalleryapplication.dto.AddressDto;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
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
     * @throws ApiRequestException
     */
    @GetMapping(value = { "/address/{id}", "/address/{id}/" })
    public AddressDto getAddress(@PathVariable("id") int id) throws ApiRequestException {
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
     * @throws ApiRequestException
     */
    @PostMapping(value = { "/address", "/address/" })
    public AddressDto createAddress(
            @RequestParam String streetAddress,
            @RequestParam String streetAddress2,
            @RequestParam String postalCode,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String country)
            throws ApiRequestException {

        Address address = addressService.createAddress(streetAddress, streetAddress2, postalCode, city, province, country);
        return ConvertToDto.convertToDto(address);
    }

    /**
     * Delete an address
     * @param id
     * @throws ApiRequestException
     */
    @DeleteMapping(value = { "/address/{id}", "/address/{id}/" })
    public void deleteAddress(@PathVariable("id") int id) throws ApiRequestException {
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
     * @throws ApiRequestException
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
            throws ApiRequestException {

        Address address = addressService.updateAddress(id, streetAddress, streetAddress2, postalCode, city, province, country);
        return ConvertToDto.convertToDto(address);
    }
}
