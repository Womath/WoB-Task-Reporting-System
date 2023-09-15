package tvarfalvi.reportapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ListingStatusDto {

    private Integer id;
    @JsonProperty("status_name")
    private String statusName;
}
