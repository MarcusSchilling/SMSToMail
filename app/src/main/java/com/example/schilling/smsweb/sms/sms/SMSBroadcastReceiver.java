package com.example.schilling.smsweb.sms.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import com.example.schilling.smsweb.sms.mail.BackgroundMail;
import com.example.schilling.smsweb.sms.mail.MailDataNotFoundException;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.schilling.smsweb.sms.Constants.SMS_INTENT_EXTRA_FLAG;

public class SMSBroadcastReceiver extends BroadcastReceiver implements Runnable {

    private List<Sms> messages = new ArrayList<>();
    private Context context;

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

        for (SmsMessage msg : msgs) {
            Log.i("SMS: ", msg.getMessageBody());
            messages.add(new Sms.Builder(msg).built());
        }
        this.context = context;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        BackgroundMail backgroundMail = new BackgroundMail(context);
        List<Sms> smsWhichCouldntBeSendToMail = new ArrayList<>();
        for (Sms message : messages) {
            try {
                backgroundMail.sendEmail(message);
                message.set_alreadySendToEmail(true);
            } catch (MessagingException | MailDataNotFoundException e) {
                smsWhichCouldntBeSendToMail.add(message);
                message.set_alreadySendToEmail(false);
            }
        }

        SMSDBServiceImpl smsDBService = SMSDBServiceImpl.getSingleton(context, "sms_db");
        smsDBService.insertNew(smsWhichCouldntBeSendToMail);
        messages.clear();
    }

}
