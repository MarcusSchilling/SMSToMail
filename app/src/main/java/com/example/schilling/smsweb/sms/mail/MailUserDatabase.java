package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MailUserData.class}, version = 1)
public abstract class MailUserDatabase extends RoomDatabase {

    public abstract MailUserDataDAO mailUserDataDAO();

}
