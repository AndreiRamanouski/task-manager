package by.htp.ramanouski.taskmanager.service;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class ServiceUtils {

    private final Random RANDOM = new SecureRandom();
    private final String LETTERS_AND_NUMBERS = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    private final int LENGTH_OF_RANDOM_STRING = 30;

    public String generatePublicUserId(){
        return generateRandomString();
    }
    public String generatePublicOrganizationId(){
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

}
