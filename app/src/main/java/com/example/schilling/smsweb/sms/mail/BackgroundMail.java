package com.example.schilling.smsweb.sms.mail;

import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;

public class BackgroundMail implements BackgroundMailService{


    private final Context app;

    public BackgroundMail(Context app) {
        this.app = app;
    }

    //TODO send email at marcus@5-schillinge.de + Send from SMS Broadcast Receiver + save SMS
    @Override
    public void sendEmail(Sms sms, String targetAddress) {

    }
}
