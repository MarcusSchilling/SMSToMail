package com.example.schilling.smsweb.sms.mail;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Properties;

@Entity
public class MailUserData {

    @PrimaryKey
    @NonNull
    private final String username;

    private final String smtpHost;
    private final short port;

    private final String passwort;

    public MailUserData(String smtpHost, short port, String username, String passwort) {
        this.smtpHost = smtpHost;
        this.port = port;
        this.username = username;
        this.passwort = passwort;
    }

    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable" , "true");
        props.setProperty("mail.smtp.host", "smtp.1und1.de"); // smtp.1und1.de
        props.setProperty("mail.smtp.auth", "true"); // true
        props.setProperty("mail.smtp.port", "587"); // 587
        props.setProperty("mail.smtp.user", username);
        props.setProperty("mail.smtp.password", passwort);
        return props;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return passwort;
    }
}
