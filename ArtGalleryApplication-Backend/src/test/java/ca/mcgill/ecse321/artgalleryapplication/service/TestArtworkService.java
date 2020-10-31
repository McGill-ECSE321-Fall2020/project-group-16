package ca.mcgill.ecse321.artgalleryapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.hibernate.mapping.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.ArtworkRepository;
import ca.mcgill.ecse321.artgalleryapplication.dao.UserRepository;
import ca.mcgill.ecse321.artgalleryapplication.dto.ArtworkDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

@ExtendWith(MockitoExtension.class)
public class TestArtworkService {

	@Mock
	private ArtworkRepository artworkDao;
	
	@Mock
	private UserRepository userDao;
	
	@InjectMocks
	private ArtworkService service;
	
	private static final int ARTWORK_ID = 123;
	private static final int NONEXISTING_ID = -1;
	private static final String TITLE = "Mona Lisa";
	private static final String DESCRIPTION = "very famous portrait!";
	private static final Date CREATION_DATE = Date.valueOf("2000-04-03");
	private static final String MEDIUM = "oil on canvas";
	private static final String IMAGE_URL = "/home/monalisa.jpg";
	private static final Double PRICE = 100.00;
	private static final ArtworkStatus STATUS = ArtworkStatus.ForSale;
	private static final String DIMENSIONS = "4' x 5'";
	private static final String COLLECTION = "classics";
	
	private static final String ARTIST = "da vinky";
	
	
	//MOCK OUTPUTS
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(artworkDao.findArtworkByArtworkId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ARTWORK_ID)) {
				Artwork a = new Artwork();
				setMockArtworkAttributes(a);
				return a;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(artworkDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Artwork a = new Artwork();
			setMockArtworkAttributes(a);
			List<Artwork> allArtwork = new ArrayList<>();
			allArtwork.add(a);
			return allArtwork;
		});
		
		lenient().when(artworkDao.findAllArtworkByArtist(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			Artwork a = new Artwork();
			if(invocation.getArgument(0).equals(ARTIST)) {
				setMockArtworkAttributes(a);
				List<Artwork> allArtwork = new ArrayList<>();
				allArtwork.add(a);
				return allArtwork;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(artworkDao.findAllArtworkByArtworkStatus(any())).thenAnswer((InvocationOnMock invocation) -> {
			Artwork a = new Artwork();
			if(invocation.getArgument(0).equals(STATUS)) {
				setMockArtworkAttributes(a);
				List<Artwork> allArtwork = new ArrayList<>();
				allArtwork.add(a);
				return allArtwork;
			} 
			else {
				return null;
			}
		});
		
		lenient().when(userDao.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTIST)) {
                UserProfile p = new UserProfile();
            	p.setUsername(ARTIST);
            	return p;
            } else {
                return null;
            }
        });
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(artworkDao.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	
	//helper method for setting mock outputs since there are so many
	public void setMockArtworkAttributes(Artwork a) {
		a.setArtworkId(ARTWORK_ID);
		a.setTitle(TITLE);
		a.setDescription(DESCRIPTION);
		a.setCreationDate(CREATION_DATE);
		a.setMedium(MEDIUM);
		a.setImageUrl(IMAGE_URL);
		a.setPrice(PRICE);
		a.setArtworkStatus(STATUS);
		a.setDimensions(DIMENSIONS);
		a.setCollection(COLLECTION);
		
		UserProfile artist = new UserProfile();
		artist.setUsername(ARTIST);
		HashSet<UserProfile> artists = new HashSet<>();
		artists.add(artist);
		
		a.setArtist(artists);
	}
	
	
	//TESTS
	
	@Test
	public void testCreateArtwork() {
		String title = TITLE;
		String description = DESCRIPTION;
		Date creationDate = CREATION_DATE;
		String medium = MEDIUM;
		String imageUrl = IMAGE_URL;
		Double price = PRICE;
		ArtworkStatus status = STATUS;
		String dimensions = DIMENSIONS;
		String collection = COLLECTION;
		
		Artwork a = null;
		
		try {
			a = service.createArtwork(title, description, creationDate,
									  medium, imageUrl, price, status, 
									  dimensions, collection);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(a);
		
		assertEquals(title, a.getTitle());
		assertEquals(description, a.getDescription());
		assertEquals(creationDate, a.getCreationDate());
		assertEquals(medium, a.getMedium());
		assertEquals(imageUrl, a.getImageUrl());
		assertEquals(price, a.getPrice());
		assertEquals(status, a.getArtworkStatus());
		assertEquals(dimensions, a.getDimensions());
		assertEquals(collection, a.getCollection());
	}
	

	
	@Test
	public void testCreateArtworkNull() {
		String title = null;
		String description = null;
		Date creationDate = null;
		String medium = null;
		String imageUrl = null;
		Double price = null;
		ArtworkStatus status = null;
		String dimensions = null;
		String collection = null;
		
		Artwork a = null;
		
		String error = null; 
		
		try {
			a = service.createArtwork(title, description, creationDate,
									  medium, imageUrl, price, status, 
								      dimensions, collection);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(a);
		// check error
		assertTrue(error.equals("title is null or length 0. Please enter valid title.") ||
				   error.equals("Entered price is null. Please enter a valid price.") || 
				   error.equals("Entered artwork status is null. Please enter a valid status."));
	}
	
	@Test
	public void testCreateArtworkEmpty() {
		String title = "";
		String description = null;
		Date creationDate = null;
		String medium = null;
		String imageUrl = null;
		Double price = null;
		ArtworkStatus status = null;
		String dimensions = null;
		String collection = null;
		
		Artwork a = null;
		
		String error = null; 
		
		try {
			a = service.createArtwork(title, description, creationDate,
									  medium, imageUrl, price, status, 
								      dimensions, collection);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(error.equals("title is null or length 0. Please enter valid title."));
	}
	
	@Test	
	public void testUpdateArtworkFields() {
		//updating attributes
		String newTitle = "Water Lilies";
		String newDescription = "wow amazing painting";
		String newImageUrl = "lilies.png";
		Double newPrice = 1000.00;
		ArtworkStatus newStatus = ArtworkStatus.NotForSale;
		String newDimensions = "2m x 2m";
		String newCollection = "landscapes";
		
		Artwork a = null;
		
		try {
			a = service.updateArtworkFields(ARTWORK_ID, newTitle, newDescription, 
											newImageUrl, newPrice, newStatus, 
											newDimensions, newCollection);
		}
		catch(IllegalArgumentException e ) {
			fail();
		}
													
		assertEquals(newTitle, a.getTitle());
		assertEquals(newDescription, a.getDescription());
		assertEquals(newImageUrl, a.getImageUrl());
		assertEquals(newPrice, a.getPrice());
		assertEquals(newStatus, a.getArtworkStatus());
		assertEquals(newDimensions, a.getDimensions());
		assertEquals(newCollection, a.getCollection());
	}
	
	@Test
	public void testGetExistingArtwork() {
		assertEquals(ARTWORK_ID, service.getArtwork(ARTWORK_ID).getArtworkId());
	}
	
	@Test
	public void testGetNonExistingArtwork() {
		String error = "";
		Artwork a;
		try {
			a = service.getArtwork(NONEXISTING_ID);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(error.equals("No artwork with ID " + NONEXISTING_ID + " in the system."));
	}
	
	@Test
	public void testGetAllArtworks() {
		String title = TITLE;
		String description = DESCRIPTION;
		Date creationDate = CREATION_DATE;
		String medium = MEDIUM;
		String imageUrl = IMAGE_URL;
		Double price = PRICE;
		ArtworkStatus status = STATUS;
		String dimensions = DIMENSIONS;
		String collection = COLLECTION;
			
		Artwork a = null;
		
		try {
			a = service.createArtwork(title, description, creationDate,
									  medium, imageUrl, price, status, 
								      dimensions, collection);
		}
		catch (IllegalArgumentException e) {
			fail();
		} 
		
		List<Artwork> allArtworks = service.getAllArtworks();
		assertEquals(1, allArtworks.size());
		assertEquals(title, allArtworks.get(0).getTitle());
		assertEquals(description, allArtworks.get(0).getDescription());
		assertEquals(creationDate, allArtworks.get(0).getCreationDate());
		assertEquals(medium, allArtworks.get(0).getMedium());
		assertEquals(imageUrl, allArtworks.get(0).getImageUrl());
		assertEquals(price, allArtworks.get(0).getPrice());
		assertEquals(status, allArtworks.get(0).getArtworkStatus());
		assertEquals(dimensions, allArtworks.get(0).getDimensions());
		assertEquals(collection, allArtworks.get(0).getCollection());
	}
	
	@Test
	public void testGetFirstNArtworks() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Artwork> allArtworks = service.getFirstNArtworks(1);
		assertEquals(1, allArtworks.size());
	}
	
	@Test
	public void testGetFirstNArtworksInvalidN() {
		int n = 0;
		String error = "";	
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		
		try {
			allArtworks = service.getFirstNArtworks(n);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "n must be greater than 0");
	}
	
	@Test
	public void testGetArtworkByArtist() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Artwork> allArtworks = service.getArtworkByArtist(ARTIST);
		assertEquals(1, allArtworks.size());
		assertEquals(ARTIST, allArtworks.get(0).getArtist().iterator().next().getUsername());
	}
	
	@Test
	public void testGetArtworkByNullArtist() {
		String artist = null;
		String error = "";
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		
		try {
			allArtworks = service.getArtworkByArtist(artist);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Please input an artist");
	}
	
	@Test
	public void testGetArtworkByEmptyArtist() {
		String artist = "";
		String error = "";
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		
		try {
			allArtworks = service.getArtworkByArtist(artist);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Please input an artist");
	}
	
	@Test
	public void testGetArtworkByPrice() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Artwork> allArtworks = service.getArtworkByPrice(null, null);
		assertEquals(1, allArtworks.size());
		assertEquals(PRICE, allArtworks.get(0).getPrice());
	}
	
	@Test
	public void testGetArtworkByInvalidPrice() {
		Artwork a = null;
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		String error = "";
		
		try {
			allArtworks = service.getArtworkByPrice(101.00, 99.00);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Minimum price must be less than maximum price");
	}
	
	@Test
	public void testGetArtworkByCreationDate() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
		
		Date minDate = Date.valueOf("2000-04-02");
		Date maxDate = Date.valueOf("2000-04-04");
		
		List<Artwork> allArtworks = service.getArtworkByCreationDate(minDate, maxDate);
		
		assertEquals(1, allArtworks.size());
		assertEquals(CREATION_DATE, allArtworks.get(0).getCreationDate());
	}
	
	@Test
	public void testGetArtworkByNullCreationDate() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}

		List<Artwork> allArtworks = service.getArtworkByCreationDate(null, null);
		
		assertEquals(1, allArtworks.size());
		assertEquals(CREATION_DATE, allArtworks.get(0).getCreationDate());
	}
	
	@Test
	public void testGetArtworkByInvalidCreationDate() {
		Artwork a = null;
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		String error = "";
		
		Date minDate = Date.valueOf("2000-04-02");
		Date maxDate = Date.valueOf("2000-04-04");
		
		try {
			allArtworks = service.getArtworkByCreationDate(maxDate, minDate);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Maximum date must be after minimum date");
	}
	
	@Test
	public void testGetArtworkByStatus() {
		Artwork a = null;
		
		try {
			a = service.createArtwork(TITLE, DESCRIPTION, CREATION_DATE,
									  MEDIUM, IMAGE_URL, PRICE, STATUS, 
								      DIMENSIONS, COLLECTION);
		}
		catch (IllegalArgumentException e) {
			fail();
		}
	
		List<Artwork> allArtworks = service.getArtworkByStatus(STATUS);
		assertEquals(1, allArtworks.size());
		assertEquals(STATUS, allArtworks.get(0).getArtworkStatus());
	}
	
	@Test
	public void testGetArtworkByNullStatus() {
		ArtworkStatus status = null;
		String error = "";
		List<Artwork> allArtworks = new ArrayList<Artwork>();
		
		try {
			allArtworks = service.getArtworkByStatus(status);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Please enter a status");
	}
	
	@Test
	public void testAddArtistToArtwork() {
		Artwork a = service.getArtwork(ARTWORK_ID);
		
		try {
			service.addArtistToArtwork(a, ARTIST);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		assertEquals(a.getArtist().iterator().next().getUsername(), ARTIST);
	}
	
	@Test
	public void testAddNullArtistToArtwork() {
		Artwork a = service.getArtwork(ARTWORK_ID);
		String artist = null;
		String error = "";
		
		try {
			service.addArtistToArtwork(a, artist);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "No user in system associated to this username");
	}
	
	@Test
	public void testAddArtistToNullArtwork() {
		Artwork a = null;
		String error = "";
		
		try {
			service.addArtistToArtwork(a, ARTIST);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "No artwork with this id in the system.");
	}
	
	@Test
	public void testDeleteNonexistentArtwork() {
		String error = "";
		try {
			service.deleteArtwork(NONEXISTING_ID);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "No artwork with this id in the system.");
	}
	
}
