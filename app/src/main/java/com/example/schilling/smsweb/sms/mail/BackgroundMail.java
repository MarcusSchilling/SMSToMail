package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.schilling.smsweb.sms.Sms;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class BackgroundMail implements BackgroundMailService{

    private final MailUserDatabase db;


    public BackgroundMail(Context appContext) {
        this.db = Room.databaseBuilder(appContext,
                MailUserDatabase.class, "sms_db").build();
    }

    @Override
    public void sendEmail(Sms sms) throws MessagingException {

        final MailUserData mailUserData = db.mailUserDataDAO().getAllMailUserData();
        Properties props = mailUserData.getProperties();
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUserData.getUsername(),
                        mailUserData.getPassword());
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailUserData.getUsername()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailUserData.getUsername()));
        message.setSubject("SMS: " + sms.get_address());
        message.setText("Message : "
                + "\n\n"+ sms.get_msg());

        Transport.send(message);


    }

}
