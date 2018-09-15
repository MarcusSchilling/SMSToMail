package com.example.schilling.smsweb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.example.schilling.smsweb.sms.Sms;
import com.example.schilling.smsweb.sms.mail.BackgroundMail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.MessagingException;

@RunWith(AndroidJUnit4.class)
public class BackgroundMailTest {

    BackgroundMail backgroundMail;

    Context appContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        backgroundMail = new BackgroundMail(appContext);
    }

    @Test
    public void test() {
        try {
            backgroundMail.sendEmail(new Sms.Builder("Hallo").built());
        } catch (MessagingException e) {
            throw new AssertionError("Couldn't send the mail");
        }
    }



}