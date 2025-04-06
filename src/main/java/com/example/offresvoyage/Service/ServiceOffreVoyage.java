package com.example.offresvoyage.Service;

import com.example.offresvoyage.Repository.OffreVoyageRepository;
import com.example.offresvoyage.entities.OffreVoyage;
import com.itextpdf.text.pdf.PdfPTable;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import com.itextpdf.text.*;


import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


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


}
