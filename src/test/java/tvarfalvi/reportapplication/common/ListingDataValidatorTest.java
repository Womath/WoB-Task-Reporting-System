package tvarfalvi.reportapplication.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tvarfalvi.reportapplication.dto.ListingDto;
import tvarfalvi.reportapplication.model.Marketplace;
import tvarfalvi.reportapplication.repository.ListingStatusRepository;
import tvarfalvi.reportapplication.repository.LocationRepository;
import tvarfalvi.reportapplication.repository.MarketplaceRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListingDataValidatorTest {

    @InjectMocks
    private ListingDataValidator listingDataValidator;
    @Mock
    private ListingStatusRepository listingStatusRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private MarketplaceRepository marketplaceRepository;

    @Test
    public void testValidateId_ValidId_ReturnsTrue() {
        assertTrue(listingDataValidator.validateId(String.valueOf(UUID.randomUUID())));
    }

    @Test
    public void testValidateId_InvalidId_ReturnsFalse() {
        assertFalse(listingDataValidator.validateId("InvalidID"));
    }

    @Test
    public void testValidateTitle_ValidTitle_ReturnsTrue() {
        assertTrue(listingDataValidator.validateTitle("Red Seas under Red Skies"));
    }

    @Test
    public void testValidateTitle_InvalidTitle_ReturnsFalse() {
        assertFalse(listingDataValidator.validateTitle(null));
    }

    @Test
    public void testValidateDescription_ValidDescription_ReturnsTrue() {
        assertTrue(listingDataValidator.validateDescription("This is the description of the book Red Seas under Red Skies from Scott Lynch"));
    }

    @Test
    public void testValidateDescription_InvalidDescription_ReturnsFalse() {
        assertFalse(listingDataValidator.validateDescription(null));
    }

    @Test
    public void testValidateLocationId_ValidId_ReturnsTrue() {
        UUID locationId = UUID.randomUUID();

        when(locationRepository.existsById(locationId)).thenReturn(true);

        assertTrue(listingDataValidator.validateLocationId(locationId.toString()));
    }

    @Test
    public void testValidateLocationId_InvalidId_ReturnsFalse() {
        String invalidId = "InvalidId";

        assertFalse(listingDataValidator.validateLocationId(invalidId));
    }

    @Test
    public void testValidateLocationId_NotFoundId_ReturnsFalse() {
        UUID invalidLocationId = UUID.randomUUID();

        when(locationRepository.existsById(invalidLocationId)).thenReturn(false);

        assertFalse(listingDataValidator.validateLocationId(invalidLocationId.toString()));
    }

    @Test
    public void testValidateLocationId_NullId_ReturnsFalse() {
        assertFalse(listingDataValidator.validateLocationId(null));
    }

    @Test
    public void testValidateListingPrice_ValidPrice_ReturnsTrue() {
        assertTrue(listingDataValidator.validateListingPrice("58.99"));
    }

    @Test
    public void testValidateListingPrice_ZeroPrice_ReturnsFalse() {
        assertFalse(listingDataValidator.validateListingPrice("0"));
    }

    @Test
    public void testValidateListingPrice_NullPrice_ReturnsFalse() {
        assertFalse(listingDataValidator.validateListingPrice(null));
    }

    @Test
    public void testValidateCurrency_ValidCurrency_ReturnTrue() {
        assertTrue(listingDataValidator.validateCurrency("HUF"));
    }

    @Test
    public void testValidateListingPrice_NullCurrency_ReturnsFalse() {
        assertFalse(listingDataValidator.validateCurrency(null));
    }

    @Test
    public void testValidateListingPrice_InvalidLength_ReturnsFalse() {
        assertFalse(listingDataValidator.validateCurrency("Ft"));
    }

    @Test
    public void testValidateQuantity_ValidQuantity_ReturnTrue() {
        assertTrue(listingDataValidator.validateCurrency("148"));
    }

    @Test
    public void testValidateQuantity_NullQuantity_ReturnsFalse() {
        assertFalse(listingDataValidator.validateCurrency(null));
    }

    @Test
    public void testValidateQuantity_NotInteger_ReturnsFalse() {
        assertFalse(listingDataValidator.validateCurrency("45.59"));
    }

    @Test
    public void testValidateQuantity_ZeroQuantity_ReturnsFalse() {
        assertFalse(listingDataValidator.validateCurrency("0"));
    }

    @Test
    public void testValidateListingStatus_ValidStatus_ReturnsTrue() {
        String status = "1";

        when(listingStatusRepository.existsById(Integer.parseInt(status))).thenReturn(true);

        assertTrue(listingDataValidator.validateListingStatus(status));
    }

    @Test
    public void testValidateListingStatus_InvalidStatus_ReturnsFalse() {
        String status = "41";

        when(listingStatusRepository.existsById(Integer.parseInt(status))).thenReturn(false);

        assertFalse(listingDataValidator.validateListingStatus(status));
    }

    @Test
    public void testValidateListingStatus_NullStatus_ReturnsFalse() {
        assertFalse(listingDataValidator.validateListingStatus(null));
    }

    @Test
    public void testValidateListingStatus_NotInteger_ReturnsFalse() {
          assertFalse(listingDataValidator.validateListingStatus("1.67"));
    }

    @Test
    public void testValidateMarketplace_ValidMarketplace_ReturnsTrue() {
        String status = "1";

        when(marketplaceRepository.existsById(Integer.parseInt(status))).thenReturn(true);

        assertTrue(listingDataValidator.validateMarketplace(status));
    }

    @Test
    public void testValidateMarketplace_InvalidMarketplace_ReturnsFalse() {
        String status = "41";

        when(marketplaceRepository.existsById(Integer.parseInt(status))).thenReturn(false);

        assertFalse(listingDataValidator.validateMarketplace(status));
    }

    @Test
    public void testValidateMarketplace_NullMarketplace_ReturnsFalse() {
        assertFalse(listingDataValidator.validateMarketplace(null));
    }

    @Test
    public void testValidateMarketplace_NotInteger_ReturnsFalse() {
        assertFalse(listingDataValidator.validateMarketplace("1.67"));
    }

    @Test
    public void testValidateUploadTime_ValidDate_ReturnsTrue() {
        String date = "9/17/2020";

        assertTrue(listingDataValidator.validateUploadTime(date));
    }

    @Test
    public void testValidateUploadTime_InvalidDate_ReturnsFalse() {
        String date = "17/9/2020";

        assertFalse(listingDataValidator.validateUploadTime(date));
    }

    @Test
    public void testValidateUploadTime_NullDate_ReturnsFalse() {
        assertFalse(listingDataValidator.validateUploadTime(null));
    }

    @Test
    public void testValidateOwnerEmailAddress_ValidEmail_ReturnsTrue() {
        String date = "palacsinta@nutellas.com";

        assertTrue(listingDataValidator.validateOwnerEmailAddress(date));
    }

    @Test
    public void testValidateOwnerEmailAddress_InvalidEmail_ReturnsFalse() {
        String date = "nutellasApalacsinta.com";

        assertFalse(listingDataValidator.validateOwnerEmailAddress(date));
    }

    @Test
    public void testValidateOwnerEmailAddress_NullEmail_ReturnsFalse() {
        assertFalse(listingDataValidator.validateOwnerEmailAddress(null));
    }

    @Test
    public void testValidateListing_ValidListing_ReturnTrue() {
        ListingDto listingDto = createValidListing();
        when(listingStatusRepository.existsById(Integer.parseInt(listingDto.getListingStatus()))).thenReturn(true);
        when(marketplaceRepository.existsById(Integer.parseInt(listingDto.getMarketplace()))).thenReturn(true);
        when(locationRepository.existsById(UUID.fromString(listingDto.getLocationId()))).thenReturn(true);
        assertTrue(listingDataValidator.validateListing(listingDto));
    }

    @Test
    public void testValidateListing_InvalidListing_ReturnFalse() {
        ListingDto listingDto = createInvalidListing();
        Marketplace marketplace = createMarketplace();
        when(listingStatusRepository.existsById(Integer.parseInt(listingDto.getListingStatus()))).thenReturn(true);
        when(marketplaceRepository.existsById(Integer.parseInt(listingDto.getMarketplace()))).thenReturn(true);
        when(locationRepository.existsById(UUID.fromString(listingDto.getLocationId()))).thenReturn(true);
        when(marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace()))).thenReturn(Optional.of(marketplace));
        assertFalse(listingDataValidator.validateListing(listingDto));
    }

    private ListingDto createValidListing() {
        ListingDto listingDto = new ListingDto();
        listingDto.setId(UUID.randomUUID().toString());
        listingDto.setTitle("Red Seas under Red Skies");
        listingDto.setDescription("Locke Lamore goes pirating ARRRR!");
        listingDto.setLocationId(UUID.randomUUID().toString());
        listingDto.setListingPrice("49.99");
        listingDto.setCurrency("GBP");
        listingDto.setQuantity("16");
        listingDto.setListingStatus("1");
        listingDto.setMarketplace("1");
        listingDto.setUploadTime("9/14/2023");
        listingDto.setOwnerEmailAddress("palacsinta@nutellas.com");

        return listingDto;
    }

    private ListingDto createInvalidListing() {
        ListingDto listingDto = new ListingDto();
        listingDto.setId(UUID.randomUUID().toString());
        listingDto.setTitle("Red Seas under Red Skies");
        listingDto.setDescription("Locke Lamore goes pirating ARRRR!");
        listingDto.setLocationId(UUID.randomUUID().toString());
        listingDto.setListingPrice("49.99");
        listingDto.setCurrency("GBP");
        listingDto.setQuantity("16");
        listingDto.setListingStatus("1");
        listingDto.setMarketplace("1");
        listingDto.setUploadTime("14/9/2023");
        listingDto.setOwnerEmailAddress("palacsinta@nutellas.com");

        return listingDto;
    }

    private Marketplace createMarketplace() {
        Marketplace marketplace = new Marketplace();
        marketplace.setId(1);
        marketplace.setMarketplaceName("EBAY");
        return marketplace;
    }






}
