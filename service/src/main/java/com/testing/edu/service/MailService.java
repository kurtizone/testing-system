package com.testing.edu.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendRegistrationMail(String to, String firstname, String lastname, String middlename, String username, String password);
    void sendNewPassword(String to, String firstname, String username, String password);
    void sendStudentChanges(String to, String firstname, String lastname, String middlename, String group, String number, String phone, String username);
    void sendLecturerChanges(String to, String firstname, String lastname, String middlename, String academic, String degree, String phone, String username);

}