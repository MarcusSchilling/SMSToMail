package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface MailUserDataDAO {

    @Query("Select * from MailUserData Limit 1")
    MailUserData getAllMailUserData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMailUserData(MailUserData mailUserData);
}
