package mailService.mail.service;

import java.util.Map;

import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import mailService.mail.convertor.JSONToObject;
import mailService.mail.dto.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static mailService.mail.constant.MailConstant.ACCOUNT_CREATE_DEFAULT_SUBJECT_EMAIL;
@Service
public class EmailService {
    JavaMailSenderService javaMailSenderService;
    ProcessTemplate processTemplate;
    Logger log = LoggerFactory.getLogger("");

    public EmailService(JavaMailSenderService javaMailSenderService) {
        this.javaMailSenderService = javaMailSenderService;
        this.processTemplate = new ProcessTemplate(new Configuration());
    }

    @KafkaListener(topics = "account-create-email", groupId = "sendMail")
    public void sendEmail(String msg) throws MessagingException {

        Map<String, Object> requestData = JSONToObject.convertToObject(msg, Map.class);

        log.info("Email Payload " + requestData);

        Mail mail = new Mail();
        mail.setToEmail(requestData.get("email").toString());
        mail.setSubject(ACCOUNT_CREATE_DEFAULT_SUBJECT_EMAIL);

        String template = processTemplate.setDataInTemplate("account_create.ftl", requestData);
        mail.setContent(template);
        javaMailSenderService.sendMail(mail);
        log.info("Response {}", requestData);
        System.out.println("Kafka Subscriber method called " + msg);
        throw new NullPointerException("Null value");
    }

    @KafkaListener(topics = "login_verification", groupId = "sendEmail")
    public void sendAdminLoginVerificationEmail(String requestData) throws MessagingException {
        Map<String, Object> data = JSONToObject.convertToObject(requestData, Map.class);

        Mail mail = new Mail();
        mail.setToEmail(data.get("toEmail").toString());
        mail.setSubject(ACCOUNT_CREATE_DEFAULT_SUBJECT_EMAIL);

        String attributes[] = processTemplate.getAllCustomAttributes();

        String template = processTemplate.setDataInTemplate("login_verification_template.ftl", data);
        mail.setContent(template);
        javaMailSenderService.sendMail(mail);
        log.info("Response {}", requestData);

    }
}
