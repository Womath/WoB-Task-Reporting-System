package tvarfalvi.reportapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tvarfalvi.reportapplication.model.MonthlyReport;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
}
