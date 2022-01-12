package by.htp.ramanouski.taskmanager.service.utils;


import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.email.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ServiceUtils {

    private final Random RANDOM = new SecureRandom();
    private final String LETTERS_AND_NUMBERS = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    private final int LENGTH_OF_RANDOM_STRING = 30;


    private final EmailSenderService emailSenderService;

    @Autowired
    public ServiceUtils(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public String generatePublicUserId(){
        return generateRandomString();
    }
    public String generatePublicOrganizationId(){
        return generateRandomString();
    }
    public String generatePublicTaskId(){
        return generateRandomString();
    }
    public String generatePublicAddressId(){
        return generateRandomString();
    }

    private String generateRandomString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < LENGTH_OF_RANDOM_STRING; i++) {
            builder.append
                    (LETTERS_AND_NUMBERS
                            .charAt(RANDOM.nextInt(LETTERS_AND_NUMBERS.length())));
        }
        return new String(builder);
    }

    public void sendMessages(TaskDto taskDto) {
        String messageTitle = "You have a new task" + taskDto.getTitle();
        String massageBody = "You have a new task" + taskDto.getTitle()
                + ". There is a task assigned to you. Due date is " + taskDto.getTargetDate();
        List<String> emails = new ArrayList<>();
        taskDto.getUsers().forEach((user) -> {
            emails.add(user.getEmail());
        });
        emailSenderService.sendMessages(emails, messageTitle, massageBody);
    }

}
