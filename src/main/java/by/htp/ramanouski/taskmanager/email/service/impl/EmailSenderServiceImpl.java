package by.htp.ramanouski.taskmanager.email.service.impl;

import by.htp.ramanouski.taskmanager.email.exception.MessageServiceException;
import by.htp.ramanouski.taskmanager.email.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessages(List<String> emails, String title, String body){
        if(emails == null){
            throw new MessageServiceException("email cannot be null");
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);

        emails.forEach( (e) -> {
            simpleMailMessage.setTo(e);
            javaMailSender.send(simpleMailMessage);
        });
    }
}
