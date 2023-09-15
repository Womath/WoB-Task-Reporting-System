package tvarfalvi.reportapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LocationDto {
    private UUID id;
    @JsonProperty("manager_name")
    private String managerName;
    private String phone;
    @JsonProperty("address_primary")
    private String addressPrimary;
    @JsonProperty("address_secondary")
    private String addressSecondary;
    private String country;
    private String town;
    @JsonProperty("postal_code")
    private String postalCode;
}
