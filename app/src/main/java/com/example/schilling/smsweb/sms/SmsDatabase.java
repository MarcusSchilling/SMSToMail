package com.example.schilling.smsweb.sms;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by schilling on 24.08.18.
 */
@Database(entities = {Sms.class}, version = 1)
public abstract class SmsDatabase extends RoomDatabase{

    public abstract SmsDAO smsDAO();

}
