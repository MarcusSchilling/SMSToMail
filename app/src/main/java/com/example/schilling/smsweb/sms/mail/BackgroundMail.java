package com.example.schilling.smsweb.sms.mail;

import android.content.Context;
import android.util.Log;
import com.example.schilling.smsweb.sms.sms.Sms;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Properties;

public class BackgroundMail implements BackgroundMailService{


    private final MailUserDataService mailUserDataService;

    public BackgroundMail(Context appContext) {
        this.mailUserDataService = MailUserDataServiceImpl.getInstance(appContext);
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
        final MailUserData mailUserData = mailUserDataService.getMailUserData();

        //does nothing if there is no profile of the user
        if (mailUserData == null) {
            Log.i("send Email: ", "No Userdata for Mail available");
            throw new MailDataNotFoundException("No UserData Available!");
        }

        Properties props = Objects.requireNonNull(mailUserData).getProperties();
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUserData.getUsername(),
                        mailUserData.getPassword());
            }
        });

        Message message = getMessage(sms, Objects.requireNonNull(mailUserData), session);

        Log.i("Mail: ", mailUserData.getUsername());
        Transport.send(message);
    }
}
