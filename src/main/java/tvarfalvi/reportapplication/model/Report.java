package tvarfalvi.reportapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @JsonIgnore
    private Long id;
    @JsonProperty("total_listing_count")
    private Long totalListingCount;
    @JsonProperty("ebay_listing_count")
    private Long ebayListingCount;
    @JsonProperty("ebay_total_listing_price")
    private BigDecimal ebayTotalListingPrice;
    @JsonProperty("ebay_avg_listing_price")
    private BigDecimal ebayAvgListingPrice;
    @JsonProperty("amazon_listing_count")
    private Long amazonListingCount;
    @JsonProperty("amazon_total_listing_price")
    private BigDecimal amazonTotalListingPrice;
    @JsonProperty("amazon_avg_listing_price")
    private BigDecimal amazonAvgListingPrice;
    @JsonProperty("best_lister_email_address")
    private String bestListerEmailAddress;
}
