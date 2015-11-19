package com.testing.edu.service.utils;

import com.testing.edu.service.MailService;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString;

@Service
@PropertySource("classpath:properties/mail.properties")
public class MailServiceImpl implements MailService {

    @Autowired
    Environment env;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${mail.credentials.username}")
    private String userName;

    @Value("${site.protocol}")
    private String protocol;

    Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Async
    public void sendRegistrationMail(String to, String firstname, String lastname, String middlename, String username, String password) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws UnsupportedEncodingException, MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setFrom(new InternetAddress("testingyousystem@gmail.com", "Testing You"));
                Map<String, Object> templateVariables = new HashMap<>();
                templateVariables.put("lastname", lastname);
                templateVariables.put("firstname", firstname);
                templateVariables.put("middlename", middlename);
                templateVariables.put("username", username);
                templateVariables.put("password", password);
                String body = mergeTemplateIntoString(velocityEngine, "/templates" + "/registration.vm", "UTF-8", templateVariables);
                message.setText(body, true);
                message.setSubject("Registration notification");
            }
        };
        mailSender.send(preparator);
    }

    @Async
    public void sendNewPassword(String to, String firstname, String username, String password) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws UnsupportedEncodingException, MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setFrom(new InternetAddress("testingyousystem@gmail.com", "Testing You"));
                Map<String, Object> templateVariables = new HashMap<>();
                templateVariables.put("firstname", firstname);
                templateVariables.put("username", username);
                templateVariables.put("password", password);
                String body = mergeTemplateIntoString(velocityEngine, "/templates" + "/newPassword.vm", "UTF-8", templateVariables);
                message.setText(body, true);
                message.setSubject("Password was changed");
            }
        };
        mailSender.send(preparator);
    }

    @Override
    public void sendStudentChanges(String to, String firstname, String lastname, String middlename, String group, String number, String phone, String username) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws UnsupportedEncodingException, MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setFrom(new InternetAddress("testingyousystem@gmail.com", "Testing You"));
                Map<String, Object> templateVariables = new HashMap<>();
                templateVariables.put("firstname", firstname);
                templateVariables.put("lastname", lastname);
                templateVariables.put("middlename", middlename);
                templateVariables.put("group", group);
                templateVariables.put("number", number);
                templateVariables.put("phone", phone);
                templateVariables.put("email", to);
                templateVariables.put("username", username);
                String body = mergeTemplateIntoString(velocityEngine, "/templates" + "/studentChanges.vm", "UTF-8", templateVariables);
                message.setText(body, true);
                message.setSubject("Your account was changed");
            }
        };
        mailSender.send(preparator);
    }

    @Override
    public void sendLecturerChanges(String to, String firstname, String lastname, String middlename, String academic, String degree, String phone, String username) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws UnsupportedEncodingException, MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setFrom(new InternetAddress("testingyousystem@gmail.com", "Testing You"));
                Map<String, Object> templateVariables = new HashMap<>();
                templateVariables.put("firstname", firstname);
                templateVariables.put("lastname", lastname);
                templateVariables.put("middlename", middlename);
                templateVariables.put("academic", academic);
                templateVariables.put("degree", degree);
                templateVariables.put("phone", phone);
                templateVariables.put("email", to);
                templateVariables.put("username", username);
                String body = mergeTemplateIntoString(velocityEngine, "/templates" + "/lecturerChanges.vm", "UTF-8", templateVariables);
                message.setText(body, true);
                message.setSubject("Your account was changed");
            }
        };
        mailSender.send(preparator);
    }
}
