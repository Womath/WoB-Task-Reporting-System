package tvarfalvi.reportapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MarketplaceDto {
    private Integer id;
    @JsonProperty("marketplace_name")
    private String marketplaceName;
}
