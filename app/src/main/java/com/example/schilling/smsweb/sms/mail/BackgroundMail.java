package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import com.example.schilling.smsweb.sms.Sms;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class BackgroundMail implements BackgroundMailService{

    private final MailUserDatabase db;


    public BackgroundMail(Context appContext) {
        this.db = Room.databaseBuilder(appContext,
                MailUserDatabase.class, "mail_user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private Message getMessage(Sms sms, MailUserData mailUserData, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailUserData.getUsername()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailUserData.getUsername()));
        String mailSubject = "SMS: " + sms.get_address();
        message.setSubject(mailSubject);
        String mailBody = "Message : "
                + "\n\n" + sms.get_msg();
        message.setText(mailBody);
        return message;
    }

    /**
     * Sends SMS to MAIL. Does nothing if there are no user data stored.
     * @param sms which should be send
     * @throws MessagingException false message or failed to send or if no user data were
     */
    @Override
    public void sendEmail(Sms sms) throws MessagingException, MailDataNotFoundException {
        final MailUserData mailUserData = db.mailUserDataDAO().getAllMailUserData();

        //do nothing if there is no profile of the user
        if (mailUserData == null) {
            Log.i("send Email: ", "No Userdata for Mail available");
            throw new MailDataNotFoundException("No UserData Available!");
        }

        Properties props = mailUserData.getProperties();
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUserData.getUsername(),
                        mailUserData.getPassword());
            }
        });

        Message message = getMessage(sms, mailUserData, session);

        Log.i("Mail: ", mailUserData.getUsername());
        Transport.send(message);
    }

}
