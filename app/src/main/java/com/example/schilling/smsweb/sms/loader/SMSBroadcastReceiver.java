package com.example.schilling.smsweb.sms.loader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import com.example.schilling.smsweb.sms.Sms;

import java.util.ArrayList;
import java.util.List;

import static com.example.schilling.smsweb.sms.Constants.SMS_INTENT_EXTRA_FLAG;

public class SMSBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] msgs;
        Bundle bundle = intent.getExtras();
        if (Build.VERSION.SDK_INT >= 19) { //KITKAT
            msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        } else {
            Object pdus[] = (Object[]) bundle.get(SMS_INTENT_EXTRA_FLAG);
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
        }
        List<Sms> messages = new ArrayList<>();
        for (SmsMessage msg : msgs) {
            messages.add(new Sms.Builder(msg).built());
        }
        Log.d("MESSAGE: ",messages.get(0).get_msg());
    }

}
