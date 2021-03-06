package com.example.schilling.smsweb.sms.sms;

import android.arch.persistence.room.*;

import java.util.List;

/**
 * SMS data access object
 * Created by schilling on 24.08.18.
 */
@Dao
public interface SmsDAO {

    @Query("Select * from Sms")
    List<Sms> getAllSms();

    @Query("Select * from Sms where _alreadySendToEmail is 0")
    List<Sms> getNotSynchronizedSms();

    @Delete
    void deleteSmss(List<Sms> smss);

    @Insert
    List<Long> insert(List<Sms> smss);

    @Query("Delete from Sms")
    void deleteAll();

    @Update
    void update(Sms sms);
}
