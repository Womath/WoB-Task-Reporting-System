package tvarfalvi.reportapplication.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import tvarfalvi.reportapplication.service.*;

import java.text.ParseException;
import java.util.logging.Logger;

@ShellComponent
@NoArgsConstructor
@AllArgsConstructor
public class CustomCommand {
    @Autowired
    private ListingStatusService listingStatusService;
    @Autowired
    private ListingService listingService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private MarketplaceService marketplaceService;
    @Autowired
    private ReportService reportService;
    Logger log = Logger.getLogger(CustomCommand.class.getName());

    /**
     * typing the report word, it synchronizes data from an external source, creates a total and monthly reports
     * and uploads it to an FTP server in a JSON file
     * @throws ParseException
     * @throws JsonProcessingException
     */
    @ShellMethod(value = "creates listing report")
    public void report() throws ParseException, JsonProcessingException {
        log.info("Synchronizing locations...");
        locationService.getAndSaveLocations();
        log.info("Synchronizing statuses...");
        listingStatusService.getAndSaveListingStatuses();
        log.info("Synchronizing marketplaces...");
        marketplaceService.getAndSaveMarketplaces();
        log.info("Synchronizing listings...");
        listingService.clearListingTable();
        listingService.getAndSaveListings();
        log.info("Synchronization completed!");
        log.info("Creating report...");
        reportService.createReport();
        log.info("Report successfully created and uploaded!");
    }
}
