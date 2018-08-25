package com.example.schilling.smsweb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.example.schilling.smsweb.sms.SMSDBServiceImpl;
import com.example.schilling.smsweb.sms.Sms;
import com.example.schilling.smsweb.sms.SmsDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext;
    private SMSDBServiceImpl smsdbService;
    private SmsDatabase sms_db;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.schilling.smsweb", appContext.getPackageName());
        sms_db = Room.databaseBuilder(appContext,
                SmsDatabase.class, "sms_db").build();

        //set up sms database ...
        smsdbService = new SMSDBServiceImpl(appContext);
    }

    @Test
    public void saveSmsTest() {
        Sms.Builder smsBuilder = new Sms.Builder("Hallo 1");
        Sms first = smsBuilder.id("1").built();
        smsdbService.insertNew(Arrays.asList(first));

        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());
        sms_db.smsDAO().deleteAll(first);
        Assert.assertEquals(0, sms_db.smsDAO().getAllSms().size());
    }

    @Test
    public void getAllUnsent() {
        Sms.Builder smsBuilder = new Sms.Builder("Hallos ");
        Sms unsent = smsBuilder.id("1").built();
        Sms send = smsBuilder.id("2").built();

        smsdbService.insertNew(Arrays.asList(send, unsent));
        Assert.assertEquals(1, smsdbService.getUnsend().size());

        sms_db.smsDAO().deleteAll(unsent);
        Assert.assertEquals(0, sms_db.smsDAO().getAllNotSyncronizedSms().size());
        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());

        sms_db.smsDAO().deleteAll(send);
        Assert.assertEquals(0, sms_db.smsDAO().getAllNotSyncronizedSms().size());
        Assert.assertEquals(0, sms_db.smsDAO().getAllSms().size());

    }




}
