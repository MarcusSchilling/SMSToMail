package com.example.schilling.smsweb.sms;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class SMSDBServiceImpl implements SMSDBService {

    private final Context applicationContext;

    public SMSDBServiceImpl(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void insertNew(List<Sms> smss) {
        SmsDatabase db = Room.databaseBuilder(applicationContext,
                SmsDatabase.class, "database-name").build();

        List<Sms> filtered = new ArrayList<>();
        for (Sms sms : smss) {
            if (!sms.wasRead()) {
                filtered.add(sms);
            }
        }
        db.smsDAO().insert(filtered);
    }
}
