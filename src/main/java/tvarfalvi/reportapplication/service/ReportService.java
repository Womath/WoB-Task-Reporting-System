package tvarfalvi.reportapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import tvarfalvi.reportapplication.model.MonthlyReport;
import tvarfalvi.reportapplication.model.Report;
import tvarfalvi.reportapplication.repository.MonthlyReportRepository;
import tvarfalvi.reportapplication.repository.ReportRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final MonthlyReportRepository monthlyReportRepository;

    /**
     * Create a list of report and monthly report object by querying the data from the database. Also, calls the method
     * to write it into a JSON and upload it to an FTP server
     * @throws JsonProcessingException
     */
    public void createReport() throws JsonProcessingException {
        List<Report> reports = reportRepository.findAll();
        List<MonthlyReport> monthlyReports = monthlyReportRepository.findAll();
        uploadToFTPServer(writeReportIntoJson(reports, monthlyReports));
    }

    /**
     * Creates JSON format from the Lists
     * @param reports - List of Report objects
     * @param monthlyReports - List of Monthly Report objects
     * @return returns a JSON formatted string
     * @throws JsonProcessingException
     */
    private String writeReportIntoJson(List<Report> reports, List<MonthlyReport> monthlyReports) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonObject jsonObject = new JsonObject();
        String report = objectMapper.writeValueAsString(reports);
        String monthlyReport = objectMapper.writeValueAsString(monthlyReports);

        jsonObject.addProperty("Report", report);
        jsonObject.addProperty("Monthly Report", monthlyReport);

        return jsonObject.toString();
    }

    /**
     * Uploads and stores the JSON string in a .json file on an FTP server
     * @param json - the report as a json string
     */
    private void uploadToFTPServer(String json) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("127.0.0.1", 21);
            ftpClient.login("User", "user");
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
            ftpClient.storeFile("/report/report.json", inputStream);
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
