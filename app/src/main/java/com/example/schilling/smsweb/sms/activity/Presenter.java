package com.example.schilling.smsweb.sms.activity;

import android.content.Context;
import android.text.Editable;
import com.example.schilling.smsweb.sms.mail.MailUserData;

public class Presenter {

    private final Model model;

    private final MainView view;


    /**
     * updates the ui with the saved UserData if available
     *
     * @param context app context
     * @param view    of the model view presenter design pattern
     */
    public Presenter(Context context, MainView view) {
        this.model = new Model(context, this);
        this.view = view;
    }

    public void updateMailUserData(MailUserData mailUserData) {
        view.updateUI(mailUserData);
    }

    public void changeMailUserData(Editable username, Editable password, Editable smtpHost, Editable port) {
        MailUserData mailUserData
                = new MailUserData(smtpHost.toString(),
                Short.decode(port.toString()),
                username.toString(),
                password.toString());
        model.updateMailUserData(mailUserData);
    }
}
