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
@Table(name = "marketplaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marketplace {
    @Id
    private Integer id;
    @Column(name = "marketplace_name")
    private String marketplaceName;

    @Override
    public String toString() {
        return "Marketplace{" +
                "id=" + id +
                ", marketplaceName='" + marketplaceName + '\'' +
                '}';
    }
}
