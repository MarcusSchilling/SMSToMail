package com.example.schilling.smsweb.sms.activity;

import android.content.Context;

import com.example.schilling.smsweb.sms.mail.BackgroundMail;
import com.example.schilling.smsweb.sms.mail.MailDataNotFoundException;
import com.example.schilling.smsweb.sms.mail.MailUserData;
import com.example.schilling.smsweb.sms.mail.MailUserDataService;
import com.example.schilling.smsweb.sms.mail.MailUserDataServiceImpl;
import com.example.schilling.smsweb.sms.sms.SMSDBService;
import com.example.schilling.smsweb.sms.sms.SMSDBServiceImpl;
import com.example.schilling.smsweb.sms.sms.Sms;

import java.util.List;

import javax.mail.MessagingException;

public class Model {

    private final MailUserDataService mailUserDataService;

    private final BackgroundMail backgroundMail;

    private final SMSDBService smsdbService;

    private MailUserData mailUserData;

    private Runnable tryToSyncSmss = new Runnable() {
        @Override
        public void run() {
            List<Sms> unsent = smsdbService.getUnsent();
            for (Sms sms : unsent) {
                try {
                    backgroundMail.sendEmail(sms);
                    smsdbService.updateToSend(sms);
                } catch (MessagingException | MailDataNotFoundException e) {
                    //is already saved as not send so do nothing on error.
                }
            }
        }
    };

    public Model(Context applicationContext, final Presenter presenter) {
        this.mailUserDataService = MailUserDataServiceImpl.getInstance(applicationContext);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mailUserData = mailUserDataService.getMailUserData();
                if (mailUserData != null) {
                    presenter.updateMailUserData(mailUserData);
                }
            }
        });
        thread.start();
        this.backgroundMail = new BackgroundMail(applicationContext);
        this.smsdbService = SMSDBServiceImpl.getSingleton(applicationContext, "sms_db");
    }

    public void updateMailUserData(final MailUserData mailUserData) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                saveMailUserData(mailUserData);
                Thread tryToSyncThread = new Thread(tryToSyncSmss);
                tryToSyncThread.start();
            }
        });
        thread.start();
    }

    private void saveMailUserData(MailUserData mailUserData) {
        this.mailUserData = mailUserData;
        if (mailUserDataService.getMailUserData() == null) {
            mailUserDataService.saveMailUserData(mailUserData);
        } else {
            this.mailUserDataService.changeMailUserData(mailUserData);
        }
    }

}
