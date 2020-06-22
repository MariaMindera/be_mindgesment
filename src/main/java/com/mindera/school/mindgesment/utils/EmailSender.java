package com.mindera.school.mindgesment.utils;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final Email FROM_EMAIL = new Email("maria.pereira@school.mindera.com", "Mindgesment");

    private static final String BASE_URL = "http://127.0.0.1:3000";

    private final String forgotPasswordTemplateId;

    private final String welcomeTemplateId;

    private final String changeEmailTemplateId;

    private final String emailChangedTemplateId;

    private final String changePasswordTemplateId;

    private final SendGrid sg;


    @Autowired
    public EmailSender(SendGrid sg,
                       @Value("${email.forgotPassword.key}") String forgotPasswordTemplateId,
                       @Value("${email.welcome.key}") String welcomeTemplateId,
                       @Value("${email.changeEmail.key}") String changeEmailTemplateId,
                       @Value("${email.emailChanged.key}") String emailChangedTemplateId,
                       @Value("${email.changePassword.key}") String changePasswordTemplateId
    ) {
        this.sg = sg;
        this.forgotPasswordTemplateId = forgotPasswordTemplateId;
        this.welcomeTemplateId = welcomeTemplateId;
        this.changeEmailTemplateId = changeEmailTemplateId;
        this.emailChangedTemplateId = emailChangedTemplateId;
        this.changePasswordTemplateId = changePasswordTemplateId;
    }

    public void sendWelcomeEmail(final String username, final String email) {
        var toEmail = new Email(email, username);

        var mail = new Mail();
        mail.setFrom(FROM_EMAIL);
        mail.setTemplateId(welcomeTemplateId);

        var personalization = new Personalization();
        personalization.addDynamicTemplateData("date", LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        personalization.addDynamicTemplateData("username", username);
        personalization.addTo(toEmail);
        mail.addPersonalization(personalization);

        sendEmail(mail);
    }

    public void sendEmailForgotPassword(final String username, final String email, final String token) {
        sendMailWithUrl(username, email, String.format("%s/change-password?token=%s", BASE_URL, token), forgotPasswordTemplateId);
    }

    public void sendChangeEmail(final String username, final String email, final String token) {
        sendMailWithUrl(username, email, String.format("%s/change-email?token=%s", BASE_URL, token), changeEmailTemplateId);
    }

    public void sendConfirmNewEmail(final String username, final String email, final String token) {
        sendMailWithUrl(username, email, String.format("http://127.0.0.1:8080/confirm-email?token=%s", token), emailChangedTemplateId);
    }

    public void sendChangePassword(final String username, final String email, final String token) {
        sendMailWithUrl(username, email, String.format("%s/change-password?token=%s", BASE_URL, token), changePasswordTemplateId);
    }

    private void sendMailWithUrl(final String username, final String email, final String url, final String templateId) {
        var toEmail = new Email(email, username);

        var mail = new Mail();
        mail.setFrom(FROM_EMAIL);
        mail.setTemplateId(templateId);

        var personalization = new Personalization();
        personalization.addDynamicTemplateData("date", LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        personalization.addDynamicTemplateData("username", username);
        personalization.addDynamicTemplateData("url", url);
        personalization.addTo(toEmail);
        mail.addPersonalization(personalization);

        sendEmail(mail);
    }

    private void sendEmail(final Mail mail) {
        var request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            LOGGER.info("Email sent");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
