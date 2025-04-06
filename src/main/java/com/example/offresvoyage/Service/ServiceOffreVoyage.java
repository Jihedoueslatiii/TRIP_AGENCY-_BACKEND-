package com.example.offresvoyage.Service;

import com.example.offresvoyage.Repository.OffreVoyageRepository;
import com.example.offresvoyage.entities.OffreVoyage;
import com.example.offresvoyage.entities.StatisticsDto;
import com.itextpdf.text.pdf.PdfPTable;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itextpdf.text.*;


import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ServiceOffreVoyage implements IServiceOffreVoyage {

OffreVoyageRepository offreVoyageRepository;


    @Override
    public OffreVoyage addOffreVoyage(OffreVoyage offreVoyage) {
        offreVoyage.setIdOffreVoyage(null); // THIS IS THE LINE YOU NEED TO ADD!
        return offreVoyageRepository.save(offreVoyage);
    }


    @Override
    public List<OffreVoyage> getAllOffreVoyages() {
        return offreVoyageRepository.findAll();
    }

    @Override
    public OffreVoyage getOffreVoyage(Long idOffreVoyage) {
        return offreVoyageRepository.findById(idOffreVoyage).orElse(null); // Or throw an exception
    }

    @Override
    public OffreVoyage updateOffreVoyage(OffreVoyage offreVoyage) {
        if (offreVoyageRepository.existsById(offreVoyage.getIdOffreVoyage())) {
            return offreVoyageRepository.save(offreVoyage);
        }
        return null;
    }

    @Override
    public void deleteOffreVoyage(Long idOffreVoyage) {
        offreVoyageRepository.deleteById(idOffreVoyage);
    }

    private final JavaMailSender mailSender;

    public void sendOffreVoyageEmail(String to, OffreVoyage offre) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("ziadinadine5@gmail.com", "TourNest Offres"));

        helper.setTo(to);
        helper.setSubject("🌍 Offre de Voyage: " + offre.getNom());

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>")
                .append("<style>")
                .append("body { font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; padding: 20px; }")
                .append(".container { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }")
                .append("h2 { color: #2E86C1; }")
                .append("p { color: #333; line-height: 1.6; }")
                .append(".logo a, .logo a:hover, .logo a:focus { color: #8596a3; text-decoration: none; font-size: 24px; font-weight: bold; }")
                .append(".logo a span { color: #00d8ff; text-transform: capitalize; }")
                .append("</style>")
                .append("</head><body>")
                .append("<div class='container'>");

        // Add the logo/link
        sb.append("<div class='logo' style='text-align:center; margin-bottom:20px;'>")
                .append("<a href='index.html'>tour<span>Nest</span></a>")
                .append("</div>");

        sb.append("<h2>").append(offre.getNom()).append("</h2>");
        sb.append("<p><strong>Destination:</strong> ").append(offre.getDestination()).append("</p>");
        sb.append("<p><strong>Prix:</strong> ").append(offre.getPrix()).append(" TND</p>");
        sb.append("<p><strong>Dates:</strong> ").append(offre.getDateDepart()).append(" → ").append(offre.getDateFin()).append("</p>");
        sb.append("<p><strong>Transport:</strong> ").append(offre.getTransport()).append("</p>");
        sb.append("<p><strong>Hébergement:</strong> ").append(offre.getHebergement()).append("</p>");
        sb.append("<p><strong>Description:</strong><br>").append(offre.getDescription()).append("</p>");

        sb.append("</div></body></html>");

        helper.setText(sb.toString(), true);
        mailSender.send(message);
    }


    public byte[] generatePDF(OffreVoyage offre) throws DocumentException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();

        // Define fonts for title and content
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);

        // Add the title
        document.add(new Paragraph("Offre de Voyage: " + offre.getNom(), titleFont));
        document.add(Chunk.NEWLINE); // Add a newline for spacing

        // Create a table to display the offer details
        PdfPTable table = new PdfPTable(2); // 2 columns
        table.setWidthPercentage(100); // Set table width to 100%

        // Add table headers
        table.addCell(new Phrase("Destination:", headerFont));
        table.addCell(new Phrase(offre.getDestination(), normalFont));
        table.addCell(new Phrase("Prix:", headerFont));
        table.addCell(new Phrase(offre.getPrix() + " TND", normalFont));
        table.addCell(new Phrase("Dates:", headerFont));
        table.addCell(new Phrase(offre.getDateDepart() + " → " + offre.getDateFin(), normalFont));
        table.addCell(new Phrase("Transport:", headerFont));
        table.addCell(new Phrase(String.valueOf(offre.getTransport()), normalFont));
        table.addCell(new Phrase("Hébergement:", headerFont));
        table.addCell(new Phrase(String.valueOf(offre.getHebergement()), normalFont));
        table.addCell(new Phrase("Description:", headerFont));
        table.addCell(new Phrase(offre.getDescription(), normalFont));

        // Add the table to the document
        document.add(table);
        document.add(Chunk.NEWLINE); // Add a newline for spacing

        document.close();
        return out.toByteArray();
    }


    public Page<OffreVoyage> searchOffers(String destination, LocalDate startDate, LocalDate endDate,
                                          Double minPrice, Double maxPrice, String transport,
                                          String hebergement, LocalDate reservationDeadline, Pageable pageable) {

        return offreVoyageRepository.findAllByFilters(destination, startDate, endDate, minPrice, maxPrice,
                transport, hebergement, reservationDeadline, pageable);
    }


    public byte[] generatePdfForSearchResults(String destination, LocalDate startDate, LocalDate endDate,
                                              Double minPrice, Double maxPrice, String transport,
                                              String hebergement, LocalDate reservationDeadline, Pageable pageable) throws DocumentException, IOException {
        // Perform the search based on the criteria provided
        Page<OffreVoyage> offresPage = searchOffers(destination, startDate, endDate, minPrice, maxPrice, transport, hebergement, reservationDeadline, pageable);
        List<OffreVoyage> offres = offresPage.getContent(); // Get the content of the search results

        // Generate a PDF document for the list of offers
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();

        // Define fonts for title and content
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);

        // Add the title
        document.add(new Paragraph("Liste des Offres de Voyage", titleFont));
        document.add(Chunk.NEWLINE); // Add a newline for spacing

        // Loop through the offers and add them to the PDF
        for (OffreVoyage offre : offres) {
            document.add(new Paragraph("Offre de Voyage: " + offre.getNom(), headerFont));
            document.add(new Paragraph("Destination: " + offre.getDestination(), normalFont));
            document.add(new Paragraph("Prix: " + offre.getPrix() + " TND", normalFont));
            document.add(new Paragraph("Dates: " + offre.getDateDepart() + " → " + offre.getDateFin(), normalFont));
            document.add(new Paragraph("Transport: " + offre.getTransport(), normalFont));
            document.add(new Paragraph("Hébergement: " + offre.getHebergement(), normalFont));
            document.add(new Paragraph("Description: " + offre.getDescription(), normalFont));
            document.add(Chunk.NEWLINE); // Add space between offers
        }

        document.close();
        return out.toByteArray();
    }

    public StatisticsDto getAdvancedStatistics() {
        List<OffreVoyage> allOffers = offreVoyageRepository.findAll();

        // Calculate revenue for the last 5 years
        Map<Integer, Double> yearlyRevenue = calculateYearlyRevenueForLastFiveYears(allOffers);

        // Create a DTO to hold the statistics
        StatisticsDto statistics = new StatisticsDto();
        statistics.setYearlyRevenue(yearlyRevenue);

        return statistics;
    }

    // Method to calculate revenue for each year, but only for the last 5 years
    public Map<Integer, Double> calculateYearlyRevenueForLastFiveYears(List<OffreVoyage> allOffers) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = currentYear - 5;

        // Filter the offers to include only those from the last 5 years
        List<OffreVoyage> recentOffers = allOffers.stream()
                .filter(offer -> offer.getDateDepart().getYear() >= startYear)
                .collect(Collectors.toList());

        // Group by year and calculate revenue
        return recentOffers.stream()
                .collect(Collectors.groupingBy(
                        offer -> offer.getDateDepart().getYear(), // Assuming `getDateCreation()` returns the year of the offer
                        Collectors.summingDouble(offer -> offer.getPrix() * offer.getCapacite()) // Calculate revenue per offer
                ));
    }

    // Save the revenue trend chart to a file on the local machine
    public void saveRevenueTrendChart(Map<Integer, Double> yearlyRevenue) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add yearly revenue data to the dataset
        for (Map.Entry<Integer, Double> entry : yearlyRevenue.entrySet()) {
            dataset.addValue(entry.getValue(), "Revenue", entry.getKey().toString());
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Yearly Revenue Trend (Last 5 Years)", "Year", "Revenue", dataset, PlotOrientation.VERTICAL, true, true, false);

        // Convert chart to image (PNG)
        BufferedImage chartImage = chart.createBufferedImage(800, 600);

        // Save the chart image to a file (e.g., on your local PC)
        File outputFile = new File("yearly_revenue_trend_last_5_years.png"); // Change path as needed
        ImageIO.write(chartImage, "PNG", outputFile);
    }
}
