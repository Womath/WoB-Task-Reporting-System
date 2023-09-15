package tvarfalvi.reportapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tvarfalvi.reportapplication.dto.MarketplaceDto;
import tvarfalvi.reportapplication.model.Marketplace;
import tvarfalvi.reportapplication.repository.MarketplaceRepository;

@Service
@RequiredArgsConstructor
public class MarketplaceService {
    @Autowired
    private MarketplaceRepository marketplaceRepository;
    private final String apiUrl = "https://my.api.mockaroo.com/marketplace?key=63304c70";

    /**
     * Loads the marketplaces from an external source and tries to save them to the database.
     */
    public void getAndSaveMarketplaces() {

        RestTemplate restTemplate = new RestTemplate();

        MarketplaceDto[] marketplaces = restTemplate.getForObject(apiUrl, MarketplaceDto[].class);

        if (marketplaces != null) {
            saveMarketplacesToDatabase(marketplaces);
        }
    }

    /**
     * Saves the marketplaces to the database
     * @param marketplaces - an array of marketplaces
     */
    private void saveMarketplacesToDatabase(MarketplaceDto[] marketplaces) {
        for (MarketplaceDto marketplaceDto : marketplaces) {
            Marketplace marketplace = new Marketplace();
            marketplace.setId(marketplaceDto.getId());
            marketplace.setMarketplaceName(marketplaceDto.getMarketplaceName());
            marketplaceRepository.save(marketplace);
        }
    }
}
