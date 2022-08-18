package cm.resources.irokotechservice.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendConfirmationTokentoNewCustomer(String name, String email, String token);
    void welcomeAfterActivationAccount(String email);
    boolean sendEmailToIrokotech();
    boolean sendEmailForForgotedPassword(String email, String url);
    void sendConfirmationUpdateInformation(String email);
}

