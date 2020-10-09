package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address{
private String streetAddress2;
   
   public void setStreetAddress2(String value) {
this.streetAddress2 = value;
    }
public String getStreetAddress2() {
return this.streetAddress2;
    }
private String postalCode;

public void setPostalCode(String value) {
this.postalCode = value;
    }
public String getPostalCode() {
return this.postalCode;
    }
private String city;

public void setCity(String value) {
this.city = value;
    }
public String getCity() {
return this.city;
    }
private String country;

public void setCountry(String value) {
this.country = value;
    }
public String getCountry() {
return this.country;
    }

private int addressId;

public void setAddressId(int value) {
this.addressId = value;
    }
@Id
public int getAddressId() {
return this.addressId;
    }
private String streetAddress;

public void setStreetAddress(String value) {
this.streetAddress = value;
    }
public String getStreetAddress() {
return this.streetAddress;
    }
private String province;

public void setProvince(String value) {
this.province = value;
    }
public String getProvince() {
return this.province;

       }
   }
