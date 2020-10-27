package ca.mcgill.ecse321.artgalleryapplication.dto;

public class ArtGalleryApplicationDto {

    private int id;

    public ArtGalleryApplicationDto() {
    }

    ArtGalleryApplicationDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
