package com.example.schilling.smsweb.sms.mail;

import com.example.schilling.smsweb.sms.Sms;

public interface BackgroundMailService {

    void sendEmail(Sms sms, String mail);
}
