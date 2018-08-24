package com.example.schilling.smsweb.sms;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * SMS data access object
 * Created by schilling on 24.08.18.
 */
@Dao
public interface SmsDAO {

    @Query("Select * from Sms")
    List<Sms> getAllSms();

    @Query("Select * from Sms where _read is 1")
    List<Sms> getAllNotSyncronizedSms();

    @Insert
    void insert(List<Sms> smss);

}
