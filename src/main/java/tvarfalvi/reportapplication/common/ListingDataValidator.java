package tvarfalvi.reportapplication.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tvarfalvi.reportapplication.dto.ListingDto;
import tvarfalvi.reportapplication.repository.ListingStatusRepository;
import tvarfalvi.reportapplication.repository.LocationRepository;
import tvarfalvi.reportapplication.repository.MarketplaceRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ListingDataValidator {
    private final ListingStatusRepository listingStatusRepository;
    private final LocationRepository locationRepository;
    private final MarketplaceRepository marketplaceRepository;

    @Getter
    private List<String[]> invalidDataList;

    @Autowired
    public ListingDataValidator(ListingStatusRepository listingStatusRepository, LocationRepository locationRepository, MarketplaceRepository marketplaceRepository) {
        this.listingStatusRepository = listingStatusRepository;
        this.locationRepository = locationRepository;
        this.marketplaceRepository = marketplaceRepository;
        this.invalidDataList = new ArrayList<>();
        this.invalidDataList.add(new String[]{"ListingId", "MarketplaceName", "InvalidField"});
    }


    /**
     * Validates the data of a Listing got from an external JSON source. It also saves the id, marketplace and the
     * invalid field and data to a .csv file whenever a listing is invalid.
     * @param listingDto - A Data Transfer Object that carries the fields of Listing as Strings
     * @return true if all fields contain valid data, false if at least one field is invalid
     */
    public boolean validateListing(ListingDto listingDto) {
        if (!validateId(listingDto.getId())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "id:" + listingDto.getId()});
            return false;
        }
        if (!validateTitle(listingDto.getTitle())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "title:" + listingDto.getTitle()});
            return false;
        }
        if (!validateDescription(listingDto.getDescription())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "description:" + listingDto.getDescription()});;
            return false;
        }
        if (!validateLocationId(listingDto.getLocationId())) {
            invalidDataList.add(new String[]{listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "locationId:" + listingDto.getLocationId()});;
            return false;
        }
        if (!validateListingPrice(listingDto.getListingPrice())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "listingPrice:" + listingDto.getListingPrice()});
            return false;
        }
        if (!validateCurrency(listingDto.getCurrency())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "currency:" + listingDto.getCurrency()});
            return false;
        }
        if (!validateQuantity(listingDto.getQuantity())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "quantity:" + listingDto.getQuantity()});
            return false;
        }
        if (!validateListingStatus(listingDto.getListingStatus())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "listingStatus:" + listingDto.getListingStatus()});
            return false;
        }
        if (!validateMarketplace(listingDto.getMarketplace())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "marketplace:" + listingDto.getMarketplace()});
            return false;
        }
        if (!validateUploadTime(listingDto.getUploadTime())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "uploadTime:" + listingDto.getUploadTime()});
            return false;
        }
        if (!validateOwnerEmailAddress(listingDto.getOwnerEmailAddress())) {
            invalidDataList.add(new String[]{
                    listingDto.getId(),
                    marketplaceRepository.findById(Integer.parseInt(listingDto.getMarketplace())).get().getMarketplaceName(),
                    "ownerEmailAddress:" + listingDto.getOwnerEmailAddress()});
            return false;
        }
        return true;
    }

    /**
     * Checks if the id is a valid UUID
     * @param id - id of the Listing
     * @return true if id is a UUID, false otherwise
     */
    boolean validateId(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if title is present
     * @param title - a text, title of a listing
     * @return true if a text is present, false otherwise
     */
    boolean validateTitle(String title) {
        return title != null;
    }

    /**
     * Checks if description is present
     * @param description - a text, description of a listing
     * @return true if a text is present, false otherwise
     */
    boolean validateDescription(String description) {
        return description != null;
    }

    /**
     * Checks if locationId is a UUID and if it can be found in the database
     * @param locationId - UUID of a location
     * @return true if an UUID and found, false otherwise
     */
    boolean validateLocationId(String locationId) {
        try {
            UUID locationUUID = UUID.fromString(locationId);
            return locationRepository.existsById(locationUUID);
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks if listing price is number more than 0 and has maximum 2 tenths
     * @param listingPrice - price of the product in a format of #.00
     * @return true if present, more than 0 and has the correct format, false otherwise
     */
    boolean validateListingPrice(String listingPrice) {
        try {
            Double aDouble = Double.parseDouble(listingPrice);
            return aDouble > 0 && listingPrice.matches("^\\d+\\.?\\d{0,2}$");
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks if currency is present and contains no more or less than 3 characters
     * @param currency - currency of the price
     * @return true if present and 3 characters long, false otherwise
     */
    boolean validateCurrency(String currency) {
        return currency != null && currency.length() == 3;
    }

    /**
     * Checks if quantity is present, more than 0 and an integer
     * @param quantity - qunatity of the product in the listing
     * @return
     */
    boolean validateQuantity(String quantity) {
        try {
            Integer anInteger = Integer.parseInt(quantity);
            return anInteger > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if listing status is present in a database
     * @param listingStatus - id of the listing status, an integer
     * @return true if present and found, false otherwise
     */
    boolean validateListingStatus(String listingStatus) {
        try {
            Integer statusId = Integer.parseInt(listingStatus);
            return listingStatusRepository.existsById(statusId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if marketplace is present in a database
     * @param marketplace - id of the marketplace, an integer
     * @return true if present and found, false otherwise
     */
    boolean validateMarketplace(String marketplace) {
        try {
            Integer marketplaceId = Integer.parseInt(marketplace);
            return marketplaceRepository.existsById(marketplaceId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if date is a valid date in a month/day/year format
     * @param uploadTime listing's upload date
     * @return true if present and valid, false otherwise
     */
    boolean validateUploadTime(String uploadTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
        simpleDateFormat.setLenient(false);
        if (uploadTime == null) {
            return false;
        }
        try {
            simpleDateFormat.parse(uploadTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Checks if the text has a valid email format
     * @param ownerEmailAddress - email address of the lister
     * @return true if present and in the right format, false otherwise
     */
    boolean validateOwnerEmailAddress(String ownerEmailAddress) {
        try {
            return ownerEmailAddress.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        } catch (NullPointerException e) {
            return false;
        }
    }


}
