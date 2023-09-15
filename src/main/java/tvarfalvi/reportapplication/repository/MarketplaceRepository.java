package tvarfalvi.reportapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tvarfalvi.reportapplication.model.Marketplace;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, Integer> {

}
