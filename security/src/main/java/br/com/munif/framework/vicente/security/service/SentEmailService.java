package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.core.ZipFiles;
import br.com.munif.framework.vicente.security.domain.dto.SentEmailDto;
import br.com.munif.framework.vicente.security.service.properties.SentEmailProperties;
import br.com.munif.framework.vicente.security.service.interfaces.IEmailService;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

@Service
public class SentEmailService implements IEmailService {

    private final SentEmailProperties emailProperties;

    public SentEmailService(SentEmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    public void send(SentEmailDto emailDto) {
        final String username = emailProperties.getUser();
        final String password = emailProperties.getPassword();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailProperties.getUser()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailDto.getTo())
            );
            message.setSubject(emailDto.getSubject());

            String template = getTemplate(emailDto);
            for (Map.Entry<String, String> entry : emailDto.getBody().entrySet()) {
                template = template.replaceAll("\\$\\{" + entry.getKey() + "}", entry.getValue());
            }
            message.setContent(template, "text/html");

            if (emailDto.getFile() != null) {
                Multipart multipart = new MimeMultipart();

                ZipFiles zipFiles = new ZipFiles();
                File file = zipFiles.zipDirectory(emailProperties.getFileDir() + emailDto.getFile());

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(emailDto.getFileName() + "zip");
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
            }

            Transport.send(message);

            System.out.println("E-mail sent from " + emailProperties.getUser() + " to " + emailDto.getTo());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPasswordRecover(String email, String password) {
        SentEmailDto emailDto = new SentEmailDto(email, this.emailProperties.getRecoverPasswordSubject());
        send(emailDto);
    }

    public String getTemplate(SentEmailDto emailDto) {
        String template = null;
        try {
            template = new String(Files.readAllBytes(Paths.get(emailDto.getTemplate())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }


}