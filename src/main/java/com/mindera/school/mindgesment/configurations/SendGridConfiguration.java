package com.mindera.school.mindgesment.configurations;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SendGridConfiguration class exists to configure SendGrid class.
 * <p>
 * This project uses an email sending system to inform the user.
 */
@Configuration
public class SendGridConfiguration {

    @Value(value = "${sendgrid.key}")
    private String sendgridKey;

    @Bean
    public SendGrid configureSendGrid() {
        return new SendGrid(sendgridKey);
    }
}
