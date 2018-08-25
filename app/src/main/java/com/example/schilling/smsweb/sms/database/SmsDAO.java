package com.example.schilling.smsweb.sms.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.schilling.smsweb.sms.Sms;

import java.util.List;

/**
 * SMS data access object
 * Created by schilling on 24.08.18.
 */
@Dao
public interface SmsDAO {

    @Query("Select * from Sms")
    List<Sms> getAllSms();

    @Query("Select * from Sms where _sendToEmail is 0")
    List<Sms> getNotSyncronizedSms();

    @Insert
    void insert(List<Sms> smss);

    @Delete
    void deleteAll(Sms sms);
}
