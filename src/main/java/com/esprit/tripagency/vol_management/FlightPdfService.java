package com.esprit.tripagency.vol_management;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;

@Service
public class FlightPdfService {

    private static final String FONT = "src/main/resources/static/fonts/arial.ttf";
    private static final String LOGO_PATH = "src/main/resources/static/images/airline-logo.png";

    public byte[] generateFlightSummary(Flight flight) throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        // Add airline logo
        Image logo = Image.getInstance(LOGO_PATH);
        logo.scaleToFit(100, 100);
        logo.setAbsolutePosition(50, 750);
        document.add(logo);

        // Title
        Font titleFont = FontFactory.getFont(FONT, 18, Font.BOLD);
        Paragraph title = new Paragraph("FLIGHT OPERATION SUMMARY", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Flight information table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setSpacingBefore(20);
        table.setSpacingAfter(30);

        addTableHeader(table, "FLIGHT DETAILS");
        addTableRow(table, "Flight Number:", flight.getNumVol());
        addTableRow(table, "Airline:", flight.getCompagnie());
        addTableRow(table, "Date:", flight.getDateVol().toString());
        addTableRow(table, "Departure Airport:", flight.getAeroportDepart());
        addTableRow(table, "Departure Time:", flight.getHeureDepart().toString());
        addTableRow(table, "Arrival Airport:", flight.getAeroportArrivee());
        addTableRow(table, "Arrival Time:", flight.getHeureArrivee().toString());
        addTableRow(table, "Status:", flight.getEtatVol().toString());

        // Flight duration calculation
        Duration duration = Duration.between(flight.getHeureDepart(), flight.getHeureArrivee());
        String durationStr = String.format("%dh %02dm", duration.toHours(), duration.toMinutesPart());
        addTableRow(table, "Scheduled Duration:", durationStr);

        document.add(table);

        // Add operational notes section
        Font sectionFont = FontFactory.getFont(FONT, 12, Font.BOLD);
        Paragraph notesTitle = new Paragraph("OPERATIONAL NOTES", sectionFont);
        notesTitle.setSpacingBefore(20);
        document.add(notesTitle);

        Font notesFont = FontFactory.getFont(FONT, 10);
        Paragraph notes = new Paragraph(
                "• This document serves as official flight operation record\n" +
                        "• All times are local to departure/arrival airports\n" +
                        "• Status updates must be reported to operations center", notesFont);
        notes.setSpacingBefore(10);
        document.add(notes);

        // Add QR code for flight
        BarcodeQRCode qrCode = new BarcodeQRCode(flight.getNumVol(), 100, 100, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.setAbsolutePosition(400, 100);
        document.add(qrCodeImage);

        document.close();
        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table, String header) {
        Font font = FontFactory.getFont(FONT, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setColspan(2);
        cell.setBackgroundColor(new BaseColor(0, 102, 204)); // Dark blue
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);
        table.addCell(cell);
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        Font labelFont = FontFactory.getFont(FONT, 10, Font.BOLD);
        Font valueFont = FontFactory.getFont(FONT, 10);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(5);
        labelCell.setBackgroundColor(new BaseColor(240, 240, 240)); // Light gray

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(5);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}