package com.example.schilling.smsweb;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.schilling.smsweb.sms.mail.MailUserData;
import com.example.schilling.smsweb.sms.mail.MailUserDatabase;

public class MainActivity extends AppCompatActivity{

    private MailUserData mailUserData;


    private EditText username;
    private EditText password;
    private EditText smtpHost;
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        smtpHost = findViewById(R.id.host);
        port = findViewById(R.id.port);
        new Thread(load).start();
    }

    Runnable load = new Runnable() {
        @Override
        public void run() {
            MailUserDatabase db = Room.databaseBuilder(getApplicationContext(),
                    MailUserDatabase.class, "mail_user_db").build();
            MailUserData allMailUserData = db.mailUserDataDAO().getAllMailUserData();
            if (allMailUserData != null) {
                username.setText(allMailUserData.getUsername());
                port.setText(String.format("%s", allMailUserData.getPort()));
                smtpHost.setText(allMailUserData.getSmtpHost());
            }
        }
    };


    public void saveMailUserData(View view) {
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MailUserDatabase db = Room.databaseBuilder(getApplicationContext(),
                    MailUserDatabase.class, "mail_user_db").build();
            mailUserData = new MailUserData(smtpHost.getText().toString(),
                    Short.parseShort(port.getText().toString()),
                    username.getText().toString(),
                    password.getText().toString());
            db.mailUserDataDAO().insertMailUserData(mailUserData);
        }
    };
}
