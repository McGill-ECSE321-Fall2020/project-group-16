package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    //create the Transactional methods

    /**
     * Service Method to create an address
     * @param streetAddress
     * @param streetAddress2
     * @param postalCode
     * @param city
     * @param province
     * @param country
     * @return
     */
    @Transactional
    public Address createAddress(String streetAddress, String streetAddress2, String postalCode, String city, String province, String country){
        if(streetAddress == null || streetAddress.trim().length() == 0) throw new IllegalArgumentException("StreetAddress is null or length 0. Please enter a valid streetAddress");
        if(postalCode == null || postalCode.trim().length() == 0) throw new IllegalArgumentException("postalCode is null or length 0. Please enter a valid postalCode");
        if(city == null || city.trim().length() == 0) throw new IllegalArgumentException("City is null or length 0. Please enter a valid city");
        if(province == null || province.trim().length() == 0) throw new IllegalArgumentException("Province is null or length 0. Please enter a valid province");
        if(country == null || country.trim().length() == 0) throw new IllegalArgumentException("Country is null or length 0. Please enter a valid country");

        Address address = new Address();
        address.setStreetAddress(streetAddress);
        address.setStreetAddress2(streetAddress2);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setProvince(province);
        address.setCountry(country);

        addressRepository.save(address);
        return address;
    }

    /**
     * Service Method to delete an address
     * @param addressId
     */
    @Transactional
    public void deleteAddress(Integer addressId) {
        if(addressId == null) throw new IllegalArgumentException("AddressID is null. Please enter a valid addressID");
        Address address = addressRepository.findAddressByAddressId(addressId);
        if(address == null) throw new IllegalReceiveException("No address in system associated with addressID: " + addressId);
        addressRepository.deleteAddressByAddressId(addressId);
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
