package tvarfalvi.reportapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tvarfalvi.reportapplication.dto.LocationDto;
import tvarfalvi.reportapplication.model.Location;
import tvarfalvi.reportapplication.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    private final String apiUrl = "https://my.api.mockaroo.com/location?key=63304c70";

    /**
     * Loads the locations data from an external source and tries to save them to the database.
     */
    public void getAndSaveLocations() {

        RestTemplate restTemplate = new RestTemplate();

        LocationDto[] locations = restTemplate.getForObject(apiUrl, LocationDto[].class);

        if (locations != null) {
            saveLocationsToDatabase(locations);
        }
    }

    /**
     * saves the locations to the database
     * @param locations - an array of locations
     */
    private void saveLocationsToDatabase(LocationDto[] locations) {
        for (LocationDto locationDto : locations) {
            Location location = new Location();
            location.setId(locationDto.getId());
            location.setManagerName(locationDto.getManagerName());
            location.setPhone(locationDto.getPhone());
            location.setAddressPrimary(locationDto.getAddressPrimary());
            location.setAddressSecondary(locationDto.getAddressSecondary());
            location.setCountry(locationDto.getCountry());
            location.setTown(locationDto.getTown());
            location.setPostalCode(locationDto.getPostalCode());
            locationRepository.save(location);
        }
    }
}
