package com.example.schilling.smsweb.sms;

import android.provider.Telephony;

public interface SMSService {

    public Iterable<Telephony.Sms> getSMSs();
}
