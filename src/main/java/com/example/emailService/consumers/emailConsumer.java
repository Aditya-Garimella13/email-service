package com.example.emailService.consumers;

import com.example.emailService.Dto.CreateUserEmailDto;
import com.example.emailService.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class emailConsumer {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EmailService emailService;
    @KafkaListener(id = "emailListenerGroup"
    , topics = "emailMessage")
    public void sendEmail(String mailDetails){
        CreateUserEmailDto emailDto = new CreateUserEmailDto();

        try {
            emailDto = mapper.readValue(mailDetails,CreateUserEmailDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        CreateUserEmailDto finalEmailDto = emailDto;
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalEmailDto.getFrom(), "chitqamfwokkqvkb");
            }
        };
        Session session = Session.getInstance(props, auth);

        emailService.sendEmail(session, emailDto.getTo(),emailDto.getSubject(), emailDto.getSubject());







    }
}
