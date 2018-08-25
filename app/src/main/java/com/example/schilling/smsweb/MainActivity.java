package com.example.schilling.smsweb;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.schilling.smsweb.sms.mail.MailUserData;
import com.example.schilling.smsweb.sms.mail.MailUserDatabase;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void saveMailUserData() {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText smtpHost = findViewById(R.id.host);
        EditText port = findViewById(R.id.port);
        MailUserData mailUserData = new MailUserData(smtpHost.getText().toString(),
                Short.parseShort(port.getText().toString()),
                username.getText().toString(),
                password.getText().toString());

        MailUserDatabase db = Room.databaseBuilder(getApplicationContext(),
                MailUserDatabase.class, "sms_db").build();
        db.mailUserDataDAO().insertMailUserData(mailUserData);
    }
}
