package com.esprit.tripagency.vol_management;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendFlightDetailsEmail(Flight flight) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Set email details
        helper.setFrom("oueslati.jihed@esprit.tn"); // Sender email
        helper.setTo("oueslati.jihed@esprit.tn"); // Recipient email
        helper.setSubject("Flight Update: " + flight.getNumVol() + " on " + flight.getDateVol());

        // Create the HTML email content
        String htmlContent = createEmailTemplate(flight);
        helper.setText(htmlContent, true); // Set HTML content

        // Send the email
        mailSender.send(message);
    }

    private String createEmailTemplate(Flight flight) {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>Flight Details</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 0; }" +
                "        .email-container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                "        h1 { color: #007BFF; text-align: center; }" +
                "        .flight-details { margin-top: 20px; }" +
                "        .flight-details p { margin: 10px 0; font-size: 16px; }" +
                "        .footer { margin-top: 20px; text-align: center; color: #777; font-size: 14px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='email-container'>" +
                "        <h1>Flight Details</h1>" +
                "        <div class='flight-details'>" +
                "            <p><strong>Flight Number:</strong> " + flight.getNumVol() + "</p>" +
                "            <p><strong>Departure Date:</strong> " + flight.getDateVol() + "</p>" +
                "            <p><strong>Departure Time:</strong> " + flight.getHeureDepart() + "</p>" +
                "            <p><strong>Arrival Time:</strong> " + flight.getHeureArrivee() + "</p>" +
                "            <p><strong>Status:</strong> " + flight.getEtatVol() + "</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>This is an automated email. Please do not reply.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
