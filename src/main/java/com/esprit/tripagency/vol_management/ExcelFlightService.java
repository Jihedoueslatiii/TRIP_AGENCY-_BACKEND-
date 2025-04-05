package com.esprit.tripagency.vol_management;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelFlightService {

    public List<Flight> importFlightsFromExcel(MultipartFile file) throws IOException {
        List<Flight> flights = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip header
            if (rows.hasNext()) rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();
                Flight flight = new Flight();

                flight.setNumVol(getStringValue(row.getCell(0)));
                flight.setCompagnie(getStringValue(row.getCell(1)));
                flight.setAeroportDepart(getStringValue(row.getCell(2)));
                flight.setAeroportArrivee(getStringValue(row.getCell(3)));
                flight.setDateVol(getDateValue(row.getCell(4)));
                flight.setHeureDepart(getTimeValue(row.getCell(5)));
                flight.setHeureArrivee(getTimeValue(row.getCell(6)));
                flight.setEtatVol(FlightStatus.valueOf(getStringValue(row.getCell(7))));

                flights.add(flight);
            }
        }

        return flights;
    }

    public ByteArrayOutputStream exportFlightsToExcel(List<Flight> flights) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Flight Operations");

            // Style for header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Create header
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Flight No.", "Airline", "Departure Airport",
                    "Arrival Airport", "Date", "Departure Time",
                    "Arrival Time", "Status", "Duration"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Style for duration cells
            CellStyle durationStyle = workbook.createCellStyle();
            durationStyle.setDataFormat(workbook.createDataFormat().getFormat("[h]:mm"));

            // Fill data
            int rowNum = 1;
            for (Flight flight : flights) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(flight.getNumVol());
                row.createCell(1).setCellValue(flight.getCompagnie());
                row.createCell(2).setCellValue(flight.getAeroportDepart());
                row.createCell(3).setCellValue(flight.getAeroportArrivee());
                row.createCell(4).setCellValue(flight.getDateVol().toString());
                row.createCell(5).setCellValue(flight.getHeureDepart().toString());
                row.createCell(6).setCellValue(flight.getHeureArrivee().toString());
                row.createCell(7).setCellValue(flight.getEtatVol().toString());

                // Calculate duration in Excel format (fraction of day)
                long minutes = Duration.between(flight.getHeureDepart(), flight.getHeureArrivee()).toMinutes();
                double excelDuration = minutes / (24.0 * 60);
                Cell durationCell = row.createCell(8);
                durationCell.setCellValue(excelDuration);
                durationCell.setCellStyle(durationStyle);
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Freeze header row
            sheet.createFreezePane(0, 1);

            workbook.write(out);
            return out;
        }
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private LocalDate getDateValue(Cell cell) {
        if (cell == null) return null;
        try {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } catch (Exception e) {
            // Handle case when cell contains date as string
            return LocalDate.parse(cell.getStringCellValue());
        }
    }

    private LocalTime getTimeValue(Cell cell) {
        if (cell == null) return null;
        try {
            return cell.getLocalDateTimeCellValue().toLocalTime();
        } catch (Exception e) {
            // Handle case when cell contains time as string
            return LocalTime.parse(cell.getStringCellValue());
        }
    }
}