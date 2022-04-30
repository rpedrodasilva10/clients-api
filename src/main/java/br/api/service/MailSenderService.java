package br.api.service;

import br.api.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@Slf4j
public class MailSenderService {

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.user}")
    private String mailUser;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.from}")
    private String mailFrom;


    public void sendMail(String clientEmail) throws ApiException {
        log.info("Enviando email para: {}", clientEmail);

        try {
            Message message = new MimeMessage(this.getMailSession());

            message.setFrom(new InternetAddress(mailFrom));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(clientEmail));

            message.setSubject("Cadastro realizado");


            Multipart multipart = new MimeMultipart("related");

            MimeBodyPart htmlPart = new MimeBodyPart();

            String mailHtmlContent = this.createHtmlText();
            htmlPart.setText(mailHtmlContent, "utf-8", "html");
            multipart.addBodyPart(htmlPart);

            message.setContent(multipart);


            // Send message
            Transport.send(message);

            log.info("Email enviado com sucesso!");


        } catch (MessagingException e) {
            String defaultFailureMessage = "Não foi possível enviar o e-mail de confirmação";
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), defaultFailureMessage,
                    "Não foi possível processar o JSON de entrada", e);
        }

    }

    private String createHtmlText() {
        String bodyDefaultMessage = "<p>This is a body</p>";
        String footer = "<br />\n" +
                "<strong>Equipe Clients</strong>";
        return "<div style=\"font-family: Arial, Helvetica, sans-serif; font-size: 16px; line-height: 1.6; color: #222; max-width: 600px\"> " +
                bodyDefaultMessage + footer +
                "</div>";
    }

    private Session getMailSession() {
        return Session.getInstance(this.createMailProperties(),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUser, mailPassword);
                    }
                });

    }

    private Properties createMailProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);

        return props;
    }
}


