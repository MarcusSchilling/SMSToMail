package com.example.schilling.smsweb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.example.schilling.smsweb.sms.sms.Sms;
import com.example.schilling.smsweb.sms.sms.Sms.Builder;
import com.example.schilling.smsweb.sms.sms.SMSDBServiceImpl;
import com.example.schilling.smsweb.sms.sms.SmsDatabase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SMSDatabaseTest {

    Context appContext;
    private SMSDBServiceImpl smsdbService;
    private SmsDatabase sms_db;

    private List<Sms> savedSms = new ArrayList<>();

    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.schilling.smsweb", appContext.getPackageName());
        String sms_database_test = "sms_test";
        sms_db = Room.databaseBuilder(appContext,
                SmsDatabase.class, sms_database_test)
                .fallbackToDestructiveMigration().build();

        //set up sms database ...
        smsdbService = SMSDBServiceImpl.getSingleton(appContext, sms_database_test);
        after();
        savedSms.clear();
    }

    @Test
    public void saveSmsTest() {
        Builder smsBuilder = new Builder("Hallo 1");
        Sms first = smsBuilder.built();
        savedSms.add(first);

        smsdbService.insertNew(Arrays.asList(first));
        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());
        sms_db.smsDAO().deleteAll();
         Assert.assertEquals(0, sms_db.smsDAO().getAllSms().size());
    }

    @Test
    public void getAllUnsent() {
        Builder smsBuilder = new Builder("Hallos ");
        Sms unsent = smsBuilder.sendToEmail(false).built();
        Sms sent = smsBuilder.sendToEmail(true).built();
        savedSms.add(unsent);
        savedSms.add(sent);


        smsdbService.insertNew(Arrays.asList(sent, unsent));
        Assert.assertEquals(1, smsdbService.getUnsent().size());

        sms_db.smsDAO().deleteSmss(Arrays.asList(unsent));
        Assert.assertEquals(0, sms_db.smsDAO().getNotSynchronizedSms().size());
        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());

        sms_db.smsDAO().deleteSmss(Arrays.asList(sent));
        Assert.assertEquals(0, sms_db.smsDAO().getNotSynchronizedSms().size());
        Assert.assertEquals(0, sms_db.smsDAO().getAllSms().size());
    }

    @Test
    public void testSetToSent() {
        Builder smsBuilder = new Builder("Hallos ");
        Sms unsent = smsBuilder.sendToEmail(false).built();
        savedSms.add(unsent);
        List<Long> ids = sms_db.smsDAO().insert(savedSms);
        savedSms.get(0).set_id(ids.get(0));
        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());
        Assert.assertEquals(1, sms_db.smsDAO().getNotSynchronizedSms().size());
        unsent.set_alreadySendToEmail(true);
        sms_db.smsDAO().update(unsent);
        Assert.assertEquals(1, sms_db.smsDAO().getAllSms().size());
        Assert.assertEquals(0, sms_db.smsDAO().getNotSynchronizedSms().size());
    }

    /**
     * sorry for the white box testing
     */
    @Test
    public void testIdGenerator() {
        Sms first = new Builder("as").built();
        Sms second = new Builder("aas").built();
        savedSms.add(first);
        savedSms.add(second);
        sms_db.smsDAO().insert(savedSms);
        sms_db.smsDAO().deleteSmss(savedSms);
    }


    @After
    public void after() {
        sms_db.smsDAO().deleteAll();
        savedSms.clear();
    }

}
