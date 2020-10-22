package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtGalleryApplicationService;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


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
    private UserService userService;


    //Mappings + mappings methods





    //convertToDto methods


}
