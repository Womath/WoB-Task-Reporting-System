package tvarfalvi.reportapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    private UUID id;
    @Column(name = "manager_name")
    private String managerName;
    private String phone;
    @Column(name = "address_primary")
    private String addressPrimary;
    @Column(name = "address_secondary")
    private String addressSecondary;
    private String country;
    private String town;
    @Column(name = "postal_code")
    private String postalCode;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", managerName='" + managerName + '\'' +
                ", phone='" + phone + '\'' +
                ", addressPrimary='" + addressPrimary + '\'' +
                ", addressSecondary='" + addressSecondary + '\'' +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
