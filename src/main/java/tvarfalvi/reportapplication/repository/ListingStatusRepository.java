package tvarfalvi.reportapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tvarfalvi.reportapplication.model.ListingStatus;

@Repository
public interface ListingStatusRepository extends JpaRepository<ListingStatus, Integer> {
}
