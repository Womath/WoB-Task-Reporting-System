package tvarfalvi.reportapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Listing {
    @Id
    private @NotNull UUID id;
    private @NotNull String title;
    private @NotNull String description;
    @Column(name = "location_id")
    private @NotNull UUID locationId;
    @Digits(integer = 10, fraction = 2)
    @Positive
    @Column(name = "listing_price")
    @NumberFormat(pattern = "#,###,###,###.##", style = NumberFormat.Style.NUMBER)
    private @NotNull Double listingPrice;
    @Length(min = 3, max = 3)
    private @NotNull String currency;
    private @NotNull Integer quantity;
    @Column(name = "listing_status")
    private @NotNull Integer listingStatus;
    private @NotNull Integer marketplace;
    @Column(name = "upload_time")
    @JsonFormat(pattern = "M/d/yyyy")
    private Date uploadTime;
    @Email
    @Column(name = "owner_email_address")
    private @NotNull String ownerEmailAddress;

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", locationId=" + locationId +
                ", listingPrice=" + listingPrice +
                ", currency='" + currency + '\'' +
                ", quantity=" + quantity +
                ", listingStatus=" + listingStatus +
                ", marketplace=" + marketplace +
                ", uploadTime=" + uploadTime +
                ", ownerEmailAddress='" + ownerEmailAddress + '\'' +
                '}';
    }
}
