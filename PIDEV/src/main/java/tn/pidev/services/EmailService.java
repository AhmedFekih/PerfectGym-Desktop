package tn.pidev.services;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.*;

import java.util.Properties;

public class EmailService {

    // Configuration du serveur SMTP
    private static final String SMTP_HOST = "smtp.gmail.com"; // Par exemple, Gmail
    private static final String SMTP_PORT = "587"; // Port de communication pour Gmail
    private static final String SMTP_USER = System.getenv().getOrDefault("SMTP_USER", ""); // Ton email
    private static final String SMTP_PASSWORD = System.getenv().getOrDefault("SMTP_PASSWORD", ""); // Ton mot de passe ou application password

    public void sendConfirmationEmail(String toEmail, String subject, String body) {
        if (SMTP_USER.isBlank() || SMTP_PASSWORD.isBlank()) {
            throw new IllegalStateException("Set SMTP_USER and SMTP_PASSWORD before sending email.");
        }
        // Configurer les propriétés pour la connexion SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        // Créer une session avec l'authentification
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        try {
            // Créer le message à envoyer
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Envoyer le message
            Transport.send(message);
            System.out.println("Email envoyé avec succès.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'email.");
        }
    }
}

