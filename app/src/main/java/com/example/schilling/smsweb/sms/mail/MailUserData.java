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

    private final String password;

    public MailUserData(String smtpHost, short port, String username, String password) {
        this.smtpHost = smtpHost;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable" , "true");
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.auth", "true"); // true
        props.setProperty("mail.smtp.port", port + "");
        props.setProperty("mail.smtp.user", username);
        props.setProperty("mail.smtp.password", password);
        return props;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public short getPort() {
        return port;
    }


    @Override
    public String toString() {
        return "MailUserData{" +
                "username='" + username + '\'' +
                ", smtpHost='" + smtpHost + '\'' +
                ", port=" + port +
                ", passwort='" + password + '\'' +
                '}';
    }
}
