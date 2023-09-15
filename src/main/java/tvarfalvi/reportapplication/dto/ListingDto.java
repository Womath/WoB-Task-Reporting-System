package tvarfalvi.reportapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingDto {

    private String id;
    private String title;
    private String description;
    @JsonProperty("location_id")
    private String locationId;
    @JsonProperty("listing_price")
    private String listingPrice;
    private String currency;
    private String quantity;
    @JsonProperty("listing_status")
    private String listingStatus;
    private String marketplace;
    @JsonProperty("upload_time")
    private String uploadTime;
    @JsonProperty("owner_email_address")
    private String ownerEmailAddress;
}
