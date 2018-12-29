package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MailUserDataServiceImpl implements MailUserDataService {

    private static MailUserDataServiceImpl singleton;
    private final MailUserDatabase db;

    private MailUserDataServiceImpl(Context appContext) {
        this.db = Room.databaseBuilder(appContext,
                MailUserDatabase.class, "mail_user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static MailUserDataServiceImpl getInstance(Context appContext) {
        if (singleton != null) {
            return singleton;
        }
        singleton = new MailUserDataServiceImpl(appContext);
        return singleton;
    }

    @Override
    public void changeMailUserData(MailUserData mailUserData) {
        db.mailUserDataDAO()
                .updateMailUserData(mailUserData);
    }

    @Override
    public MailUserData getMailUserData() {
        return db.mailUserDataDAO().getAllMailUserData();
    }

    @Override
    public void saveMailUserData(MailUserData mailUserData) {
        db.mailUserDataDAO().saveMailUserData(mailUserData);
    }
}
