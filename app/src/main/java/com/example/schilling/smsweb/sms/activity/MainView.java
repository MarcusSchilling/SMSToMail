package com.example.schilling.smsweb.sms.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schilling.smsweb.R;
import com.example.schilling.smsweb.sms.mail.MailUserData;

public class MainView extends Activity {

    private EditText username;
    private EditText password;
    private EditText smtpHost;
    private EditText port;
    private Presenter presenter;
    private static final int requestCode = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        smtpHost = findViewById(R.id.host);
        port = findViewById(R.id.port);
        requestReadAndSendSmsPermission();
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

    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isSmsPermissionGranted()) {
                    ActivityCompat.requestPermissions(MainView.this, new String[]{Manifest.permission.RECEIVE_SMS}, requestCode);
                }
            }
        }).run();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MainView.requestCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    new Toast(this)
                            .setText("This App makes no sense without this permission. It cannot pass your SMSs to your E-Mail Address.");

                }
            }
        }
    }



}
