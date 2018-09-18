package com.example.schilling.smsweb.sms.mail;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

public class MailUserDataServiceImpl implements MailUserDataService{

    private static MailUserDataServiceImpl singleton;


    public static MailUserDataServiceImpl getInstance(Context appContext) {
        if (singleton != null) {
            return singleton;
        }
        singleton = new MailUserDataServiceImpl(appContext);
        return singleton;
    }

    private final MailUserDatabase db;

    private MailUserDataServiceImpl(Context appContext) {
        this.db = Room.databaseBuilder(appContext,
                MailUserDatabase.class, "mail_user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    public void changeMailUserData(MailUserData mailUserData) {

    }

    @Override
    public MailUserData getMailUserData() {
        final LiveData<MailUserData> mailUserData = db.mailUserDataDAO().getAllMailUserData();
        return mailUserData.getValue();
    }
}
