package com.example.schilling.smsweb.sms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.example.schilling.smsweb.R;
import com.example.schilling.smsweb.sms.mail.MailUserData;

public class MainView extends Activity {

    private EditText username;
    private EditText password;
    private EditText smtpHost;
    private EditText port;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        smtpHost = findViewById(R.id.host);
        port = findViewById(R.id.port);
        presenter = new Presenter(getApplicationContext(), this);
    }

    public void saveMailUserData(android.view.View view) {
        presenter.changeMailUserData(username.getText(), password.getText(), smtpHost.getText(), port.getText());
    }

    public void updateUI(MailUserData mailUserData) {
        username.setText(mailUserData.getUsername());
        port.setText(String.format("%s", mailUserData.getPort()));
        smtpHost.setText(mailUserData.getSmtpHost());
        password.setText(mailUserData.getPassword());
    }


}
