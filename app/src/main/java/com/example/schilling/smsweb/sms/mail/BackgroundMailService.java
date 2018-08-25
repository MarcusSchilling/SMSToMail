package com.example.schilling.smsweb.sms.mail;

import com.example.schilling.smsweb.sms.Sms;

import javax.mail.MessagingException;

public interface BackgroundMailService {

    void sendEmail(Sms sms) throws MessagingException;
}
