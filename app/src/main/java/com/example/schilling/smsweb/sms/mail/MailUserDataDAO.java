package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.*;

@Dao
public interface MailUserDataDAO {

    @Query("Select * from MailUserData Limit 1")
    MailUserData getAllMailUserData();

    @Update
    void updateMailUserData(MailUserData mailUserData);

}
