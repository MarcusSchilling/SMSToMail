package com.example.schilling.smsweb.sms.sms;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import static com.example.schilling.smsweb.sms.sms.Sms.EMPTY_MIGRATION;
import static com.example.schilling.smsweb.sms.sms.Sms.EMPTY_MIGRATION_2;

public class SMSDBServiceImpl implements SMSDBService {

    private static SMSDBServiceImpl singleton;
    private final SmsDatabase db;

    private SMSDBServiceImpl(Context applicationContext, String db_name) {
        db = Room.databaseBuilder(applicationContext,
                SmsDatabase.class, db_name)
                .addMigrations(EMPTY_MIGRATION)
                .addMigrations(EMPTY_MIGRATION_2)
                .build();
    }

    public static SMSDBServiceImpl getSingleton(Context applicationContext, String db_name) {
        if (singleton == null) {
            singleton = new SMSDBServiceImpl(applicationContext, db_name);
            return singleton;
        } else {
            return singleton;
        }
    }

    /**
     * saving the Smss and adding the autogenerated ids to the objects
     *
     * @param smss which should be saved
     */
    @Override
    public void insertNew(List<Sms> smss) {
        List<Long> ids = db.smsDAO().insert(smss);
        for (int i = 0; i < ids.size(); i++) {
            smss.get(i).set_id(ids.get(i));
        }
    }

    @Override
    public void updateToSend(Sms sms) {
        sms.set_alreadySendToEmail(true);
        db.smsDAO().update(sms);
    }

    @Override
    public List<Sms> getUnsent() {
        return db.smsDAO().getNotSynchronizedSms();
    }


}