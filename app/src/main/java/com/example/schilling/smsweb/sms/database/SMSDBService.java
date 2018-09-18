package com.example.schilling.smsweb.sms.database;

import com.example.schilling.smsweb.sms.Sms;

import java.util.List;

public interface SMSDBService {

    void insertNew(List<Sms> smss);

    List<Sms> getUnsent();

    void updateToSend(Sms sms);

}
