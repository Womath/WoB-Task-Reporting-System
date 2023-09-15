package tvarfalvi.reportapplication.service;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tvarfalvi.reportapplication.common.ListingDataValidator;
import tvarfalvi.reportapplication.dto.ListingDto;
import tvarfalvi.reportapplication.model.Listing;
import tvarfalvi.reportapplication.repository.ListingRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final ListingDataValidator listingDataValidator;
    private final String apiUrl = "https://my.api.mockaroo.com/listing?key=63304c70";

    /**
     * Loads the Listings from an external source and tries to save them to the database. It also calls the method
     * to write the invalid datas into a .csv file
     * @throws ParseException - in normal circumstances this should not happen as the data was validated beforehand
     */
    public void getAndSaveListings() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();

        ListingDto[] listings = restTemplate.getForObject(apiUrl, ListingDto[].class);
        if (listings != null) {
            saveListingsToDatabase(listings);
        }

        writeIntoImportCSVFile("importLog.csv", listingDataValidator.getInvalidDataList());
    }

    /**
     * Deletes all data from the listings database
     */
    public void clearListingTable() {
        listingRepository.deleteAll();
    }

    /**
     * Validates all data from the listing dto and if everything is correct, saves it to the database
     * @param listingDtos - an array of Listing Data Transfer Objects
     * @throws ParseException - in normal circumstances this should not happen as the data was validated beforehand
     */
    void saveListingsToDatabase(ListingDto[] listingDtos) throws ParseException {
        for (ListingDto listingDto : listingDtos) {
            if (listingDataValidator.validateListing(listingDto)) {
                Listing listing = new Listing();
                listing.setId(UUID.fromString(listingDto.getId()));
                listing.setTitle(listingDto.getTitle());
                listing.setDescription(listingDto.getDescription());
                listing.setLocationId(UUID.fromString(listingDto.getLocationId()));
                listing.setListingPrice(Double.parseDouble(listingDto.getListingPrice()));
                listing.setCurrency(listingDto.getCurrency());
                listing.setQuantity(Integer.parseInt(listingDto.getQuantity()));
                listing.setListingStatus(Integer.parseInt(listingDto.getListingStatus()));
                listing.setMarketplace(Integer.parseInt(listingDto.getMarketplace()));
                listing.setUploadTime(new SimpleDateFormat("M/d/yyyy").parse(listingDto.getUploadTime()));
                listing.setOwnerEmailAddress(listingDto.getOwnerEmailAddress());

                listingRepository.save(listing);
            } else {
                deleteFromDatabase(UUID.fromString(listingDto.getId()));
            }
        }
    }

    /**
     * Deletes a listing from database by its ID
     * @param listingId - ID of the listing
     */
    void deleteFromDatabase(UUID listingId) {
        if (listingRepository.existsById(listingId)) {
            listingRepository.deleteById(listingId);
        }
    }

    /**
     * Writes the data into a .csv file
     * @param filePath - path of the .csv file
     * @param invalidDataList - a list of invalid datas
     */
    void writeIntoImportCSVFile(String filePath, List<String[]> invalidDataList) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            csvWriter.writeAll(invalidDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
