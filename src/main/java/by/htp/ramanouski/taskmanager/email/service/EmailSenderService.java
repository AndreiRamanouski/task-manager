package by.htp.ramanouski.taskmanager.email.service;


import java.util.List;

public interface EmailSenderService {
    void sendMessages(List<String> emails, String title, String body);
}
