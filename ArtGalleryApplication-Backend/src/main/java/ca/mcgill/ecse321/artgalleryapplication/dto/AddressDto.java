package ca.mcgill.ecse321.artgalleryapplication.dto;

public class AddressDto {
    private Integer addressId;
    private String streetAddress;
    private String streetAddress2;
    private String postalCode;
    private String city;
    private String province;
    private String country;

    public AddressDto() {
    }

    public AddressDto(Integer addressId, String streetAddress, String streetAddress2, String postalCode, String city, String province, String country){
        this.addressId = addressId;
        this.streetAddress = streetAddress;
        this.streetAddress2 = streetAddress2;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
