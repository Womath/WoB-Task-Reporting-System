package tvarfalvi.reportapplication.service;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tvarfalvi.reportapplication.model.MonthlyReport;
import tvarfalvi.reportapplication.model.Report;
import tvarfalvi.reportapplication.repository.MonthlyReportRepository;
import tvarfalvi.reportapplication.repository.ReportRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private MonthlyReportRepository monthlyReportRepository;
    @Mock
    private FTPClient ftpClient;

//    @Test
//    public void testCreateReport() throws IOException {
//        List<Report> reports = createSampleReports();
//        List<MonthlyReport> monthlyReports = createSampleMonthlyReports();
//
//        when(reportRepository.findAll()).thenReturn(reports);
//        when(monthlyReportRepository.findAll()).thenReturn(monthlyReports);
//
//    }

    private List<Report> createSampleReports() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report(
                1L,
                100L,
                40L,
                BigDecimal.valueOf(35.6),
                BigDecimal.valueOf(2.70),
                60L,
                BigDecimal.valueOf(48.80),
                BigDecimal.valueOf(3.11),
                "johnsnow@winterfell.got"
        ));
        return reports;
    }

    private List<MonthlyReport> createSampleMonthlyReports() {
        List<MonthlyReport> monthlyReports = new ArrayList<>();
        monthlyReports.add(new MonthlyReport(
                1L,
                "2017-01",
                40L,
                BigDecimal.valueOf(35.6),
                BigDecimal.valueOf(2.70),
                60L,
                BigDecimal.valueOf(48.80),
                BigDecimal.valueOf(3.11),
                "johnsnow@winterfell.got"
        ));

        monthlyReports.add(new MonthlyReport(
                1L,
                "2017-02",
                45L,
                BigDecimal.valueOf(25.6),
                BigDecimal.valueOf(1.70),
                65L,
                BigDecimal.valueOf(58.80),
                BigDecimal.valueOf(4.11),
                "ashketchup@wannabetheverybest.pkmn"
        ));
        return monthlyReports;
    }

}
