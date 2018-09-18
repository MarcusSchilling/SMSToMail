package com.example.schilling.smsweb.sms.sms;

import java.util.List;

public interface SMSDBService {

    void insertNew(List<Sms> smss);

    List<Sms> getUnsent();

    void updateToSend(Sms sms);

}
