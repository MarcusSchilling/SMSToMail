package com.example.schilling.smsweb.sms;

import java.util.List;

public interface SMSDBService {

    void insertNew(List<Sms> smss);

    List<Sms> getUnsend();

}
