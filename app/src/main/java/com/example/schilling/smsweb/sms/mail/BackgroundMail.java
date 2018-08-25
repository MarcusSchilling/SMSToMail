package com.example.schilling.smsweb.sms.mail;

import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;

public class BackgroundMail implements BackgroundMailService{


    private final Context app;

    public BackgroundMail(Context app) {
        this.app = app;
    }

    @Override
    public void sendEmail(Sms sms, String targetAddress) {

    }
}
