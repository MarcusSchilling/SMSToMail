package com.example.schilling.smsweb.sms.activity;

import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;
import com.example.schilling.smsweb.sms.database.SMSDBService;
import com.example.schilling.smsweb.sms.database.SMSDBServiceImpl;
import com.example.schilling.smsweb.sms.mail.*;

import javax.mail.MessagingException;
import java.util.List;

public class Model {

    private MailUserData mailUserData;
    private final MailUserDataService mailUserDataService;
    private final BackgroundMail backgroundMail;
    private final SMSDBService smsdbService;


    public Model(Context applicationContext) {
        this.mailUserDataService = MailUserDataServiceImpl.getInstance(applicationContext);
        this.mailUserData = mailUserDataService.getMailUserData();
        this.backgroundMail = new BackgroundMail(applicationContext);
        this.smsdbService = SMSDBServiceImpl.getSingleton(applicationContext, "sms_db");
    }

    public void updateMailUserData(MailUserData mailUserData) {
        saveMailUserData(mailUserData);
        Thread tryToSyncThread = new Thread(tryToSyncSmss);
        tryToSyncThread.start();
    }

    private void saveMailUserData(MailUserData mailUserData) {
        this.mailUserData = mailUserData;
        this.mailUserDataService.changeMailUserData(mailUserData);
    }

    public MailUserData getMailUserData() {
        return mailUserData;
    }

    private Runnable tryToSyncSmss = new Runnable() {
        @Override
        public void run() {
            List<Sms> unsent = smsdbService.getUnsent();
            for (Sms sms : unsent) {
                try{
                    backgroundMail.sendEmail(sms);
                    smsdbService.updateToSend(sms);
                }catch (MessagingException | MailDataNotFoundException e) {
                    //is already saved as not send so do nothing on error.
                }
            }
        }
    };
}
