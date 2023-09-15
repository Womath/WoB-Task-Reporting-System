package tvarfalvi.reportapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tvarfalvi.reportapplication.dto.ListingStatusDto;
import tvarfalvi.reportapplication.model.ListingStatus;
import tvarfalvi.reportapplication.repository.ListingStatusRepository;

@Service
@RequiredArgsConstructor
public class ListingStatusService {
    @Autowired
    private ListingStatusRepository listingStatusRepository;
    private final String apiUrl = "https://my.api.mockaroo.com/listingStatus?key=63304c70";

    /**
     * Loads the listing statuses from an external source and tries to save them to the database.
     */
    public void getAndSaveListingStatuses() {
        RestTemplate restTemplate = new RestTemplate();

        ListingStatusDto[] statuses = restTemplate.getForObject(apiUrl, ListingStatusDto[].class);

        if (statuses != null) {
            saveListingStatusesToDatabase(statuses);
        }
    }

    /**
     * Saves listing statuses to the database
     * @param statuses - an array of listing statuses
     */
    private void saveListingStatusesToDatabase(ListingStatusDto[] statuses) {
        for (ListingStatusDto status : statuses) {
            ListingStatus listingStatus = new ListingStatus();
            listingStatus.setId(status.getId());
            listingStatus.setStatusName(status.getStatusName());
            listingStatusRepository.save(listingStatus);
        }
    }
}
