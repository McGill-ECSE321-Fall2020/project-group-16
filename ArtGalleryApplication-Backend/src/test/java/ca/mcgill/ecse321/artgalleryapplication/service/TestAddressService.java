package ca.mcgill.ecse321.artgalleryapplication.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import  ca.mcgill.ecse321.artgalleryapplication.model.*;

@ExtendWith(MockitoExtension.class)
public class TestAddressService {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private static final String ADDRESS_KEY = "TestCity";

    // Correct Address info
    private static final String CORRECTSTREETADDRESS = "Test Street Address";
    private static final String CORRECTSTREETADDRESS2 = "Test Street Address 2";
    private static final String CORRECTPOSTALCODE = "PostalCode";
    private static final String CORRECTCITY = "TestCity";
    private static final String CORRECTPROVINCE = "TestProvince";
    private static final String CORRECTCOUNTRY = "TestCountry";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(addressRepository.save(any(Address.class))).thenAnswer((InvocationOnMock invocation) -> {
            Address address = new Address();
            address.setCity(ADDRESS_KEY);
            return address;
        });
    }

    @Test
    public void testCreateCorrectAddress() {
        Address address = null;

        try {
            address = addressService.createAddress(CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(address);
        assertEquals(ADDRESS_KEY, address.getCity());
        assertEquals(CORRECTSTREETADDRESS, address.getStreetAddress());
        assertEquals(CORRECTSTREETADDRESS2, address.getStreetAddress2());
        assertEquals(CORRECTPOSTALCODE, address.getPostalCode());
        assertEquals(CORRECTPROVINCE, address.getProvince());
        assertEquals(CORRECTCOUNTRY, address.getCountry());
    }

    @Test
    public void testStreetAddressNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.createAddress(null, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "StreetAddress is null or length 0. Please enter a valid streetAddress");
    }

    @Test
    public void testPostalCodeNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.createAddress(CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, null, CORRECTCITY, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "postalCode is null or length 0. Please enter a valid postalCode");
    }

    @Test
    public void testCityNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.createAddress(CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, null, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "City is null or length 0. Please enter a valid city");
    }

    @Test
    public void testProvinceNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.createAddress(CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, null, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "Province is null or length 0. Please enter a valid province");
    }

    @Test
    public void testCountryNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.createAddress(CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, CORRECTPROVINCE, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "Country is null or length 0. Please enter a valid country");
    }

    @Test
    public void testDeleteCorrectAddress() {
        //Stub that creates an address and returns it when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(createAddress());
        try {
            // 1 is a fake id we are passing in
            addressService.deleteAddress(1);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testDeleteIdNull() {
        String error = "";

        try {
             addressService.deleteAddress(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "AddressID is null. Please enter a valid addressID");
    }

    @Test
    public void testDeleteInvalidId() {
        String error = "";
        //Stub that returns null when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(null);

        try {
            addressService.deleteAddress(1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "No address in system associated with addressID: 1");
    }

    @Test
    public void testGetCorrectAddress() {
        Address address = null;
        //Stub that creates an address and returns it when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(createAddress());
        try {
            // 1 is a fake id we are passing in
            address = addressService.getAddressById(1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(address);
        assertEquals(ADDRESS_KEY, address.getCity());
    }

    @Test
    public void testGetIdNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.getAddressById(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "AddressID is null. Please enter a valid addressID");
    }

    @Test
    public void testGetInvalidId() {
        Address address = null;
        String error = "";
        //Stub that returns null when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(null);

        try {
            address = addressService.getAddressById(1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "No address in system associated with addressID: 1");
    }

    @Test
    public void testUpdateCorrectAddress() {
        Address address = null;
        //Stub that creates an address and returns it when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(createAddress());

        String NEWSTREETADDRESS = "newSA";
        String NEWSTREETADDRESS2 = "newSA2";
        String NEWPOSTALCODE = "newPC";
        String NEWCITY = "newC";
        String NEWPROVINCE = "newP";
        String NEWCOUNTRY = "newCo";


        try {
            // 1 is a fake id we are passing in
            address = addressService.updateAddress(1, NEWSTREETADDRESS, NEWSTREETADDRESS2, NEWPOSTALCODE, NEWCITY, NEWPROVINCE, NEWCOUNTRY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(address);

        // verify all fields have changed
        assertEquals(NEWSTREETADDRESS, address.getStreetAddress());
        assertEquals(NEWSTREETADDRESS2, address.getStreetAddress2());
        assertEquals(NEWPOSTALCODE, address.getPostalCode());
        assertEquals(NEWCITY, address.getCity());
        assertEquals(NEWPROVINCE, address.getProvince());
        assertEquals(NEWCOUNTRY, address.getCountry());
    }

    @Test
    public void testUpdateIdNull() {
        Address address = null;
        String error = "";

        try {
            address = addressService.updateAddress(null, CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "AddressID is null. Please enter a valid addressID");
    }

    @Test
    public void testUpdateInvalidId() {
        Address address = null;
        String error = "";
        //Stub that returns null when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(null);

        try {
            address = addressService.updateAddress(1, CORRECTSTREETADDRESS, CORRECTSTREETADDRESS2, CORRECTPOSTALCODE, CORRECTCITY, CORRECTPROVINCE, CORRECTCOUNTRY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(address);
        assertEquals(error, "No address in system associated with addressID: 1");
    }

    @Test
    public void testUpdatePostalCode() {
        Address address = null;
        String NEWPOSTALCODE = "zeNewPostalCode";
        //Stub that creates an address and returns it when we try to find an address by id
        lenient().when(addressRepository.findAddressByAddressId(anyInt())).thenReturn(createAddress());

        try {
            // 1 is a fake id we are passing in
            address = addressService.updateAddress(1, "", "", NEWPOSTALCODE, "", "", "");
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(address);

        // all these fields have not changed
        assertEquals(ADDRESS_KEY, address.getCity());
        assertEquals(CORRECTSTREETADDRESS, address.getStreetAddress());
        assertEquals(CORRECTSTREETADDRESS2, address.getStreetAddress2());
        assertEquals(CORRECTPROVINCE, address.getProvince());
        assertEquals(CORRECTCOUNTRY, address.getCountry());

        // verifying unique field changed
        assertEquals(NEWPOSTALCODE, address.getPostalCode());
    }






    private Address createAddress() {
        Address address = new Address();

        // Setting the Address info
        address.setStreetAddress(CORRECTSTREETADDRESS);
        address.setStreetAddress2(CORRECTSTREETADDRESS2);
        address.setPostalCode(CORRECTPOSTALCODE);
        address.setCity(CORRECTCITY);
        address.setProvince(CORRECTPROVINCE);
        address.setCountry(CORRECTCOUNTRY);

        return address;
    }
}
