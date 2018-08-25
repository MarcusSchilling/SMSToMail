package com.example.schilling.smsweb.sms.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;

import java.util.List;

public class SMSDBServiceImpl implements SMSDBService {

    private final Context applicationContext;
    private final SmsDatabase db;

    public SMSDBServiceImpl(Context applicationContext) {
        this.applicationContext = applicationContext;
        db = Room.databaseBuilder(applicationContext,
                SmsDatabase.class, "sms_db").build();
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
