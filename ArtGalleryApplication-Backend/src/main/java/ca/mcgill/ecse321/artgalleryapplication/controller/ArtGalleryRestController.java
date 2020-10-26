package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.service.ArtGalleryApplicationService;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
public class ArtGalleryRestController {

    @Autowired
    private ArtGalleryApplicationService applicationService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private EventService eventService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private UserProfileService userProfileService;


    //Mappings + mappings methods





    //convertToDto methods


}
