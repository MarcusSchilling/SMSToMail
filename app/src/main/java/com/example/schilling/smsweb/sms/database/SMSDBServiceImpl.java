package com.example.schilling.smsweb.sms.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;

import java.util.List;

public class SMSDBServiceImpl implements SMSDBService {

    private final Context applicationContext;
    private final SmsDatabase db;

    private SMSDBServiceImpl(Context applicationContext) {
        this.applicationContext = applicationContext;
        db = Room.databaseBuilder(applicationContext,
                SmsDatabase.class, "sms_db").build();
    }

    private static SMSDBServiceImpl singleton;

    public static SMSDBServiceImpl getSingleton(Context applicationContext) {
        if (singleton == null) {
            singleton = new SMSDBServiceImpl(applicationContext);
            return singleton;
        }else {
            return singleton;
        }
    }

    @Override
    public void insertNew(List<Sms> smss) {
        db.smsDAO().insert(smss);
    }

    @Override
    public List<Sms> getUnsent() {
        return db.smsDAO().getNotSyncronizedSms();
    }


}
