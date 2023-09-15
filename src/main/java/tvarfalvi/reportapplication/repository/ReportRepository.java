package tvarfalvi.reportapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tvarfalvi.reportapplication.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
