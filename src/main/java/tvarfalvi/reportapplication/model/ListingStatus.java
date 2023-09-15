package tvarfalvi.reportapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "listing_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingStatus {
    @Id
    private Integer id;
    @Column(name = "status_name")
    private String statusName;

    @Override
    public String toString() {
        return "ListingStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
