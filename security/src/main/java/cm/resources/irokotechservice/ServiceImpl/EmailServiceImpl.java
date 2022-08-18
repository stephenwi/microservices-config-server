package cm.resources.irokotechservice.ServiceImpl;

import cm.resources.irokotechservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final String EMAIL = "no-reply@irokotech.africa";

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @SneakyThrows
    @Override
    @Async
    public void sendConfirmationTokentoNewCustomer(String name, String email, String token)  {
     logger.info("Sending activation account link mail to user:"+ name);

     MimeMessage message = javaMailSender.createMimeMessage();
     MimeMessageHelper helper  = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
     helper.addAttachment("iroko.png", new ClassPathResource("iroko.png"));
    // Map<String, Object> model = new HashMap<>();
    // Template template = c
        helper.setSubject("Welcome " + name);
        helper.setFrom(EMAIL);

        String template = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>Sending Email with Thymeleaf HTML Template Example</title>\n" +
                "\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "\n" +
                "    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>\n" +
                "\n" +
                "    <!-- use the font -->\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Roboto', sans-serif;\n" +
                "            font-size: 48px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\n" +
                "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
                "        <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#78ab46\" style=\"padding: 40px 0 30px 0;\">\n" +
                "                <img src=\"cid:logo.png\" alt=\"https://irokotech.africa\" style=\"display: block;\" />\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#eaeaea\" style=\"padding: 40px 30px 40px 30px;\">\n" +
                "                <p>Dear" + name +",</p>\n" +
                "                <p>Thank you for registering. Please click on the below link to activate your account</b></p>\n" +
                "                <p>"+ token +"</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#777777\" style=\"padding: 30px 30px 30px 30px;\">\n" +
                "                <p>irokotech.africa</p>\n" +
                "                <p>Yaoundé-CAMEROON</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        helper.setText(template, true);
        helper.setTo(email);
        javaMailSender.send(message);

    }

    @Override
    public void welcomeAfterActivationAccount(String email) {
        logger.info("Sending welcome message to user with username: "+ email);

        
    }

    @Override
    public boolean sendEmailToIrokotech() {
        return false;
    }

    @Override
    public boolean sendEmailForForgotedPassword(String email, String url) {
        return false;
    }

    @Override
    public void sendConfirmationUpdateInformation(String email) {

    }


}
