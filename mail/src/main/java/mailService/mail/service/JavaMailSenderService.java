package mailService.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mailService.mail.dto.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderService {

    @Value("${spring.mail.username}")
    private String fromEmail;
    JavaMailSender javaMailSender;

    public JavaMailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Mail email) throws MessagingException {

        try {
            String fileLocation = "static/java_quiz_logo.png";
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email.getToEmail());
            helper.setFrom(fromEmail);
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent(), true);
            helper.addAttachment("java_quiz_logo.png", new ClassPathResource(fileLocation));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exp) {
            System.out.println("Exception msg " + exp.getLocalizedMessage());
            exp.printStackTrace();
        }
    }
}
